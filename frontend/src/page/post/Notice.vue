<template>
    <div class="container">
        <v-btn v-show="this.info=='ROLE_ADMIN'" style="float:right;" x-large outlined text @click="create">글작성</v-btn>
        <!-- <b-table
            :data="tableData"            
            striped
            narrowed
            hoverable
            mobile-cards
            :loading="loading">
             <template slot-scope="props">
                <b-table-column :label="header" :field="index" v-for="(data, index) in data" :key="index" sortable numeric>
                    {{ props.row[index] }}
                </b-table-column>
            </template>            
        </b-table>     -->
        
    <!-- <b-table :items="data" :fields="fields" :per-page="perPage" :current-page="currentPage" @row-clicked="detail(index)" bordered responsive="sm">
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.noticeNo)" class="mr-1">
          dd
        </b-button>
      </template>
    </b-table> -->
    <b-table :items="data" :fields="fields" :per-page="perPage" :current-page="currentPage" responsive="sm">
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <div size="sm" @click="detail(row.item.noticeNo)" class="mr-1" style="background-color:white;">
          {{row.item.title}}
        </div>
      </template>
    </b-table>
    <b-pagination
        v-show="this.data.length>5"
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        align="center"
      ></b-pagination>
    </div>
</template>

<script>
import '../../assets/css/profile.scss'
import axios from 'axios'

  export default {
    data(){
      return {
        currentPage:1,
        perPage:5,
        data: null,
        info: '',
        fields: [
        {key:'noticeNo', label: '번호'},
        {key:'actions', label: '제목'},
        {key: 'createDate', label: '작성 일자'}
        ],
      }
    },
    created() {
      this.getlist()
    },
    computed:{
        rows(){
        return this.data.length;
        }
    },
    methods: {
      getlist() {
        axios.get(this.$store.state.base_url +'/notice',{
            headers: {
              'ACCESS-TOKEN' : this.$store.state.token
            }
        })
        .then((response) => {
            console.log(response)
            this.data = response.data.data.list;
            this.info = response.data.data.user.role;
            console.log(this.info)
          })
        .catch((error) => {
          console.log(error)
        })
      },
      detail(noticeNo) {
        console.log(noticeNo)
        this.$router.push('/noticedetail/'+noticeNo)
      },
      create(){
        this.$router.push('/noticecreate')
      },
      ck(a,b){
        console.log(a)
        console.log(b)
      }
    },

    beforeRouteUpdate (to, from, next){
        this.getlist();
        next();
    }
  }
</script>

<style scoped>
.mr-1:hover{
  color:rgb(51, 54, 185);
}
</style>