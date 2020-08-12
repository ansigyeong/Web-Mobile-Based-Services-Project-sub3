<template>
    <div class="container">
        <b-table :items="data" :fields="fields" :per-page="perPage" :current-page="currentPage" striped responsive="sm">
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.noticeNo)" class="mr-1">
          상세보기
        </b-button>
      </template>
    </b-table>
    <b-pagination
        v-show="this.data.length>10"
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
        fields: [
        {key:'noticeNo', label: '번호'},
        {key:'title', label: '제목'},
        {key: 'createDate', label: '작성 일자'}],
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
          })
        .catch((error) => {
          console.log(error)
        })
      },
      detail(noticeNo) {
        this.$router.push('/detail/'+noticeNo)
      }
    },

    beforeRouteUpdate (to, from, next){
        this.getlist();
        next();
    }
  }
</script>