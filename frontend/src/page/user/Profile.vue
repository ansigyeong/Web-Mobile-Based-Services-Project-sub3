<template>
  <div class="container">
    <!-- 프로필 -->
    <h1 v-if="this.flag">내 정보</h1>
    <h1 v-else>{{username}}님 정보</h1>
    <div class="row">
      <div class="col-1"></div>
      <div class="col-3">
        <img class="userprofile" :src="link">
      </div>
      <div class="col-1"></div>
      <div class="col-6">
        <b-table stacked :items="useritems" class = "custom"></b-table>
      </div>
    </div>
    <span v-if="this.flag"> 
    <div>
        <b-button @click="deleteuser">회원 탈퇴</b-button>
        <b-button @click="updateuser">회원 정보 수정</b-button>
    </div>
    </span>
    <span v-else>    
        <b-button v-if = "this.followflag" @click="follow">팔로우</b-button>
        <b-button v-else @click="followdelete">팔로우 취소</b-button>
    </span>
    <hr>
    <!-- 팔로우-->
    <div>
      <h2 class="prtitle">나를 팔로우한 사람</h2>
      <!-- count함수 사용해서 총 팔로우/팔로워수 집계 -->
      <p>{{follower.length}}명</p>
      <!-- <a @click="openfollow">dd</a> -->
      
      <div>
        <b-button v-b-modal.modal-scrollable>팔로워 전체보기</b-button>

        <b-modal id="modal-scrollable" scrollable title="나를 팔로우하는 사람들">
          <div class="my-4" v-for="i in 10" :key="i">
            <div class="container row allfollowers">
              <div class="col-6 allfollower">
                <img class="allfollowerimg" src="../../assets/img/백상아리.png" alt="">
                <p class="allfollower">민디</p>
              </div>
              <div class="col-6 allfollower">
                <img class="allfollowerimg" src="../../assets/img/백상아리.png" alt="">
                <p>만디</p>
              </div>
            </div>
          </div>
          <div class="overflow-auto" style="float: right;">
            <div class="mt-3">
              <b-pagination v-model="currentPage" :total-rows="rows"></b-pagination>
            </div>
          </div>
        </b-modal>

      </div>


        <carousel :per-page="1" :navigate-to="someLocalProperty" :mouse-drag="false">
            <slide>
              <div v-for="(item,index) in follower.slice(0,4)"  :key="index">
                <div class="followpfs">
                  <a href=""><img class="followpf" src="../../assets/img/lv1.png" alt=""></a>
                  <a href=""><p>{{item.name}}</p></a>
                </div>
              </div>                       
            </slide>
            <slide v-if="follow.length > 3">
              <div v-for="(item,index) in follower.slice(4,8)"  :key="index">
                <div class="followpfs">
                  <a href=""><img class="followpf" src="../../assets/img/lv1.png" alt=""></a>
                  <a href=""><p>{{item.name}}</p></a>
                </div>
              </div>                             
            </slide>        
        </carousel>

  
    </div>
    <hr>
    <!-- 팔로잉 -->
    <div>
      <h2 class="prtitle">내가 팔로잉하는 사람</h2>
      <p>{{following.length}}명</p>

      <div>
        <b-button v-b-modal.modal-tall>팔로잉 전체보기</b-button>

        <b-modal id="modal-tall" scrollable title="내가 팔로우하는 사람들">
          <div class="my-4" v-for="i in 10" :key="i">
            <div class="container row allfollowers">
              <div class="col-6 allfollower">
                <img class="allfollowerimg" src="../../assets/img/백상아리.png" alt="">
                <p class="allfollower">민디</p>
              </div>
              <div class="col-6 allfollower">
                <img class="allfollowerimg" src="../../assets/img/백상아리.png" alt="">
                <p>만디</p>
              </div>
            </div>
          </div>
          <div class="overflow-auto" style="float: right;">
            <div class="mt-3">
              <b-pagination v-model="currentPage" :total-rows="rows"></b-pagination>
            </div>
          </div>
        </b-modal>
      </div>
 
        <carousel :per-page="1" :navigate-to="someLocalProperty" :mouse-drag="false">
            <slide>
              <div v-for="(item,index) in following.slice(0,4)"  :key="index">
                <div class="followpfs">
                  <a href=""><img class="followpf" src="../../assets/img/lv1.png" alt=""></a>
                  <a href=""><p>{{item.name}}</p></a>
                </div>
              </div>                       
            </slide>
            <slide v-if="following.length > 3">
              <div v-for="(item,index) in following.slice(4,8)"  :key="index">
                <div class="followpfs">
                  <a href=""><img class="followpf" src="../../assets/img/lv1.png" alt=""></a>
                  <a href=""><p>{{item.name}}</p></a>
                </div>
              </div>                             
            </slide>        
        </carousel>     
    </div>
    <hr>

    <h2>내가 한 질문</h2>
    <b-table :items="data" :fields="fields" striped responsive="sm">
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
          상세보기
        </b-button>
      </template>
    </b-table>
    <h2>내가 한 답변</h2>
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
import '../../assets/css/profile.scss'
import axios from 'axios'
import { Carousel, Slide } from 'vue-carousel'

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
        useritems:null,
        following: null,
        follower: null,
        link: null,
        username: null,
        flag: null,
        followflag :null,
        selectlist: null
      }
    },
    created() {
      this.getlist(this.$route.params.userNo)
      this.checkflag()
    },
    methods: {
      getlist(userNo) {
        axios.get(this.$store.state.base_url +'/info/profile',{     
            params: {
              'userNo': userNo
            },
            headers: {
              'ACCESS-TOKEN' : this.$store.state.token
            }
        })
        .then((response) => {
            console.log(response)
            this.flag = response.data.data.modify
            this.link =  require('../../assets/img/lv'+this.level(response.data.data.user.grade)+'.png')
            this.username = response.data.data.user.name;
            this.data = response.data.data.myque;
            this.replydata = response.data.data.myrp;
            this.useritems =[{
              '이름': response.data.data.user.name,
              '이메일' : response.data.data.user.email,
              '가입 일자' : response.data.data.user.createDate,
              '언어' : response.data.data.user.lang,
              '등급' : response.data.data.user.grade,
              '랭킹' : response.data.data.hof.rank
            }]
            this.following = response.data.data.followingList
            this.follower = response.data.data.followerList
            console.log(this.follower)
          })
        .catch((error) => {
          console.log(error)
        })
      },
      detail(queNo) {
        this.$router.push('/detail/'+queNo)
      },
      checkflag() {
          axios.get(this.$store.state.base_url +'/follow/following',{     
          params: null, 
          headers : {
              'ACCESS-TOKEN' : this.$store.state.token
          }   
      })
      .then((response) => {
          console.log(response)
          this.selectlist = response.data.data.followingList
          console.log(this.selectlist)
          var temp = true
          for (var i in this.selectlist){
              if (this.$route.params.userNo == this.selectlist[i].userNo){
                  temp = false
                  break
              }
          }
          this.followflag = temp
          })
      },
      deleteuser() {
        
      },
      updateuser() {

      },
      follow() {
          let config = {
              headers: {
                  "ACCESS-TOKEN": this.$store.state.token
              }
          }
          let body = {
              followingNo: this.$route.params.userNo
          }
          axios.post(this.$store.state.base_url +'/follow',body, config)
          .then((response) => {
            console.log(response)
            this.checkflag()
          })
        },
      followdelete() {
          axios.delete(this.$store.state.base_url +'/follow',{     
          params: {
            followingNo: this.$route.params.userNo
          }, 
          headers : {
              'ACCESS-TOKEN' : this.$store.state.token
          }   
        })
          .then((response) => {
            console.log(response)
            this.checkflag()
            console.log(this.followflag)
          })
        },
      level(grade){
        if (grade>= 0 && grade < 30){
          return 0
        }
        else if (grade>=30 && grade<50){
          return 1
        }
        else if (grade>=50 && grade<70){
          return 2
        }
        else if (grade>=70 && grade<90){
          return 3
        }
        else if (grade>=90 && grade<110){
          return 4
        }
        else if (grade>=110 && grade<130){
          return 5
        }
        else {
          return 6
        }
      }
      },
    beforeRouteUpdate (to, from, next){
        this.getlist(to.params.userNo);
        next();
    }
  }
</script>

<style>

  .allfollowers {
    margin: 0;
    padding-top: 0;
    padding-right: 26px;
  }
  .allfollower {
    margin: 0;
    padding: 0;
    display: block;

  }

    .allfollowerimg {
    margin: 0;
    padding: 0;

    border: black solid 1px;
    border-radius: 50%;

  }
</style>