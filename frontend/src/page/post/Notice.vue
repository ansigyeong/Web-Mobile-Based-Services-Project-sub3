<template>
    <div class="container">
        <h1 style="margin: 20px;">📢 공지 사항 📢</h1>
        <v-btn v-show="this.info=='ROLE_ADMIN'" style="float:right;" x-large outlined text @click="create">글작성</v-btn>
       
    <b-table :items="data" :fields="fields" :per-page="perPage" :current-page="currentPage" responsive="sm">
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <div size="sm" @click="detail(row.item.noticeNo)" class="mr-1" style="background-color:white;">
          {{row.item.title}}
        </div>
      </template>
    </b-table>
    <div class="outter">
        <a href="http://ncov.mohw.go.kr/baroView4.do?brdId=4&brdGubun=44/" target="_blank">
           <img src="../../assets/img/covid.png"> 
        </a>
      </div>
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
        data: [],
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
            this.data = response.data.data.list;
            this.info = response.data.data.user.role;
          })
        .catch((error) => {
        })
      },
      detail(noticeNo) {
        this.$router.push('/noticedetail/'+noticeNo)
      },
      create(){
        this.$router.push('/noticecreate')
      },
    },

    beforeRouteUpdate (to, from, next){
        this.getlist();
        next();
    }
  }
</script>

<style scoped>

            @font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

  * {
      font-family: 'CookieRun-Regular';
  }
            


.mr-1:hover{
  color:rgb(51, 54, 185);
}

.outter{
    position: fixed;
    right:50px;
    bottom:0;
    width: 190px;
    top: 200px
           /* padding: 150px 50px 0 0 */
    }
</style>