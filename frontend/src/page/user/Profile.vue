<template>
  <div class="container">
    <!-- 프로필 -->
    <h1 v-if="this.flag">내 정보</h1>
    <h1 v-else>{{username}}님 정보</h1>
    <div class="row" style="border:black solid 1px; width:800px; height:330px; margin:0 100px 0 100px">
      <div class="col-1"></div>
      <div class="col-3">
        <img class="userprofile" :src="link">
      </div>
      <div class="col-1"></div>
      <div class="col-6">
        <b-table stacked :items="useritems" class = "custom" borderless="false"></b-table>
      </div>
    </div>
    <span v-if="this.flag"> 
    <div>
        <b-button @click="deleteuser" disabled  variant="primary" style="margin:7px 7px 0 0 ">회원 탈퇴</b-button>
        <b-button @click="updateuser"  disabled  variant="primary" style="margin:7px 0 0 7px">정보 수정</b-button>
    </div>
    </span>
    <span v-else>    
        <b-button v-if = "this.followflag" @click="follow"  disabled  variant="primary">팔로우</b-button>
        <b-button v-else @click="followdelete"  disabled  variant="primary">팔로우 취소</b-button>
    </span>
    <hr>
    <!-- 팔로우-->
    <div>
      <h2 class="prtitle">나를 팔로우한 사람</h2>
      <!-- count함수 사용해서 총 팔로우/팔로워수 집계 -->
      <p>{{follower.length}}명</p>
      <!-- <a @click="openfollow">dd</a> -->
      
      <div>
        <b-button v-b-modal.modal-scrollable  disabled  variant="primary">팔로워 전체보기</b-button>

        <b-modal id="modal-scrollable" scrollable title="나를 팔로우하는 사람들">
          <div class="my-4" v-for="(k,i) in following.slice(0,follower.length/2+1)" :key="i">
            <div class="container row allfollowers">
                <div class="col-6 allfollower" v-for="item in follower.slice(2*i,2*i+2)" :key="item">
                  <img class="allfollowerimg" width="120px" height="120px" :src="getimage(item.grade)" alt="">
                  <a href="" @click="userdetail(item.userNo)"><p class="allfollower">{{item.name}}</p></a>
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
              <div class="followpfs">
                <div v-for="(item,index) in follower.slice(0,5)" :key="index">
                    <a href="" @click="userdetail(item.userNo)"><img class="followpf" :src="getimage(item.grade)" alt=""></a>
                    <a href="" @click="userdetail(item.userNo)"><p>{{item.name}}</p></a>
                </div>       
              </div>   
            </slide>
            <slide v-if="follow.length > 4">
              <div class="followpfs">
                <div v-for="(item,index) in follower.slice(5,10)" :key="index">
                    <a href="" @click="userdetail(item.userNo)"><img class="followpf" :src="getimage(item.grade)" alt=""></a>
                    <a href="" @click="userdetail(item.userNo)"><p>{{item.name}}</p></a>
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
        <b-button v-b-modal.modal-tall  disabled  variant="primary">팔로잉 전체보기</b-button>

        <b-modal id="modal-tall" scrollable title="내가 팔로우하는 사람들">
          <div class="my-4" v-for="(k,i) in following.slice(0,following.length/2+1)" :key="i">
            <div class="container row allfollowers">
                <div class="col-6 allfollower" v-for="item in following.slice(2*i,2*i+2)" :key="item">
                  <img class="allfollowerimg" width="120px" height="120px" :src="getimage(item.grade)" alt="">
                  <a href="" @click="userdetail(item.userNo)"><p class="allfollower">{{item.name}}</p></a>
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
              <div class="followpfs">
                <div v-for="(item,index) in following.slice(0,5)"  :key="index">
                  <a href="" @click="userdetail(item.userNo)"><img class="followpf" :src="getimage(item.grade)" alt=""></a>
                  <a href="" @click="userdetail(item.userNo)"><p>{{item.name}}</p></a>
                </div>
              </div>                       
            </slide>
            <slide v-if="following.length > 5">
              <div class="followpfs">
                <div v-for="(item,index) in following.slice(5,10)"  :key="index">
                  <a href="" @click="userdetail(item.userNo)"><img class="followpf" :src="getimage(item.grade)" alt=""></a>
                  <a href="" @click="userdetail(item.userNo)"><p>{{item.name}}</p></a>
                </div>
              </div>                             
            </slide>        
        </carousel>     
    </div>
    <hr> 

    <h2>내가 한 질문</h2>
    <b-table :items="data" :fields="fields" :per-page="perPage" :current-page="currentPage"  responsive="sm" >
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1"  disabled  variant="primary">
          상세보기
        </b-button>
      </template>
    </b-table>
    <b-pagination
        v-show="this.data.length>5"
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        align="right"
        
      ></b-pagination>
    <h2>내가 한 답변</h2>
    <b-table :items="replydata" :fields="replyfields" :per-page="perPage2" :current-page="currentPage2"  responsive="sm">
      <slot></slot>
      <template v-slot:cell(replyactions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1"  disabled  variant="primary">
          상세보기
        </b-button>
      </template>
    </b-table>
    <b-pagination
        v-show="this.replydata.length>5"
        v-model="currentPage2"
        :total-rows="rows2"
        :per-page="perPage2"
        align="right"
      ></b-pagination>
  </div>
</template>

<script>
import '../../assets/css/profile.scss'
import axios from 'axios'
import { Carousel, Slide } from 'vue-carousel'

  export default {
    data(){
      return {
        currentPage:1,
        perPage:5,
        currentPage2:1,
        perPage2:5,
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
    computed:{
      rows(){
        return this.data.length;
      },
      rows2(){
        return this.replydata.length;
      }
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
            console.log('re')
            console.log(response.data.data.myrp)
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
            // console.log(this.follower)
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
          .catch((error) => {
            alert('세션 만료.\n다시 로그인 해주세요.')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
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
        if (grade < 100){return 0}
        else if (grade>=100 && grade<200){return 1}
        else if (grade>=200 && grade<300){return 2}
        else if (grade>=300 && grade<400){return 3}
        else if (grade>=400 && grade<500){return 4}
        else if (grade>=500 && grade<600){return 5}
        else {return 6}
      },
      getimage(grade){
        return require('../../assets/img/lv'+this.level(grade)+'.png')
      },
      userdetail(userNo){
        this.$router.push('/profile/'+userNo)
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