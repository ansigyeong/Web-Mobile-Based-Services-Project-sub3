<template>
  <div class="container">
    <h1>내 정보</h1>
    <b-table stacked :items="useritems" class = "custom"></b-table>
    <h1>내가 한 질문</h1>
    <b-table :items="data" :fields="fields" striped responsive="sm">
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
          상세보기
        </b-button>
      </template>
    </b-table>
    <h1>내가 한 답변</h1>
    <b-table :items="replydata" :fields="replyfields" striped responsive="sm">
      <slot></slot>
      <template v-slot:cell(replyactions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
          상세보기
        </b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
import axios from 'axios'
  export default {
    data(){
      return {
        data: null,
        fields: [
        {key:'lang', label: '언어'},
        {key:'title', label: '제목'},
        {key: 'createDate', label: '작성 시간'},
        {key: 'actions', label: '상세 보기' }],
        replydata : null,
        replyfields: [
          {key:'rpLike', label: '좋아요'},
          {key:'contents', label: '내용'},
          {key:'createDate', label: '작성일'},
          {key:'replyactions', label: '질문 보기'}
        ],
        useritems: null,
        followinglist: null,
        followerlist: null
      }
    },
    methods: {
      getlist() {
        axios.get(this.$store.state.base_url +'/question/myQue',{     
            params: null, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
          console.log(response)
          this.data = response.data.data.list
        })
        .catch((error) => {
            alert('세션 만료.\n다시 로그인 해주세요.')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
      },
      detail(queNo) {
        this.$router.push('/detail/'+queNo)
      },
      getreply() {
          axios.get(this.$store.state.base_url +'/question/myRp',{     
              params: null, 
              headers : {
                  'ACCESS-TOKEN' : this.$store.state.token
              }   
          })
          .then((response) => {
            console.log(response)
            this.replydata = response.data.data.list
          })
          .catch((error) => {
              alert('세션 만료.\n다시 로그인 해주세요.')
              this.$cookies.remove('auth-token')
              this.$store.commit('checkToken',this.$cookies.get('auth-token'))
              this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
              this.$router.push('/login')
          })
      },
    getuser() {
        axios.get(this.$store.state.base_url +'/account/profile',{     
            params: null, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
          console.log(response)
        userfields: [
          {key:'name', label: '이름'},
          {key:'email', label: '이메일'},
          {key:'createDate', label: '가입 일자'},
          {key:'lang', label:'언어'},
          {key:'grade', label:'등급'}      
        ],
          this.useritems = [{
            '이름':response.data.data.user.name,
            '이메일':response.data.data.user.email,
            '가입 일자':response.data.data.user.createDate,
            '언어':response.data.data.user.lang,
            '등급':response.data.data.user.grade
          }];
          this.followinglist = response.data.data.followinglist;
          this.followerlist = response.data.data.followerlist;
          console.log(this.useritems)
        })
        .catch((error) => {
            alert('세션 만료.\n다시 로그인 해주세요.')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
      }  
    },
    created() {
      this.getlist()
      this.getreply()
      this.getuser()
    }
  }
</script>

<style>

</style>