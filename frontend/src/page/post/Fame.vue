<template>
  <div class="container">
    <h1 style="margin: 20px;">👑 명예의 전당 👑</h1>
    <!-- <span v-if="$store.state.islogin">
                    
      <router-link to="/record" style="color:rgb(76, 75, 71);">내 기록</router-link>
    </span> -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <div class="mt-5">
      <div class="row">
        <div v-for="(item,index) in items" :key="index">
          <div class="card" :class="'mark'+index" >
            <img class="card-img-top" :src="require('../../assets/img/lv'+level(item.grade)+'.png')"/>
            <div class="card-body">
                        <span v-if="index==0">
            <img src="../../assets/img/silver.png" style="height:60px" alt="">
          </span>
          <span v-else-if="index==1">
            <img src="../../assets/img/gold.png" style="height:70px" alt="">
          </span>
          <span v-else>
            <img src="../../assets/img/bronz.png" style="height:55px" alt="">
          </span>
              <h3 class="touch" @click="userdetail(item.userNo)" href="">{{ item.name }}</h3>
              <p class="card-text">{{ description(level(item.grade))+' 등급' }}</p>
              <div class="content">
                <div class="data">
                  <ul>
                    <li>
                      {{item.queCnt}}
                      <span>질문 수</span>
                    </li>
                    <li>
                      {{item.rpCnt}}
                      <span>답변 수</span>
                    </li>
                    <li>
                      {{item.rpLike}}
                      <span>좋아요 수</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
                    
  import axios from 'axios'
  export default {
    data () {
      return {
        items : null,
      }
    },
    methods: {
      getlist() {
        var temp = null
        axios.get(this.$store.state.base_url + '/info/hof')
        .then((response) => {
          temp = response.data.data.data.slice(0,3)
          this.items = [
            temp[1],temp[0],temp[2]
          ]
          for(var i in this.items){
            if (this.items[i] == undefined){
              swal('','유저가 3명 이상이어야 집계가 가능합니다.', 'warning')
              this.$router.go(-1)
              break
            }
          }
        })
        .catch((error) => {
          console.log(error)
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
      description(level){
        if (level == 0) { return '플랑크톤'}
        else if (level == 1) {return '멸치'}
        else if (level == 2) {return '새우'}
        else if (level == 3) {return '해파리'}
        else if (level == 4) {return '문어'}
        else if (level == 5) {return '돌고래'}
        else {return '백상아리'}
      },
      userdetail(userNo){
        this.$router.push('/profile/'+userNo)
      }
    },
    userdetail(userNo){
        this.$router.push('/profile/'+userNo)
      },
    created() {
      this.getlist()
    }
  }
</script>
<style scoped>
                    
  .container {
    margin-left: 250px;
  }
  @font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
  }
  * {
      font-family: 'CookieRun-Regular';
  }
            
  .card-img-top {
    padding-right: 20px;
    padding-left: 20px;
    padding-top: 20px;
  }
  .data {
    margin-top: 0.6em;
    color: #81878b;
  }
  .data li {
    width: 32%;
    text-align: center;
    display: inline-block;
    font-size: 1.5em;
    font-family: "Lato";
    border-right: solid 1px #bdc3c7;
  }
  .data li:last-child {
    border: none;
  }
  .data li span {
    display: block;
    text-transform: uppercase;
    font-family: "Quicksand";
    font-size: 0.5em;
    margin-top: 0.6em;
    font-weight: 700;
  }
  .box {
    display: -webkit-inline-box;
    display: inline-flex;
    border: 1px solid #666;
    width: 100px;
    height: 100px;
  }
  .card-title {
    padding: 0.8rem 1rem;
    color: #000;
  }
  .mark0 {
    border: 2px solid #c0c0c0;
    width: 290px;
    margin-top: 50px;
    margin-right: 16px;
    box-shadow: 5px 5px 5px 5px gray;
  }
  .mark1 {
    border: 2px solid #ffb52e;
    width: 397px;
    margin-right: 16px;
    box-shadow: 5px 5px 5px 5px gray;
  }
  .mark2 {
    border: 2px solid #c0c0c0;
    width: 290px;
    margin-top: 50px;
    box-shadow: 5px 5px 5px 5px gray;
  }
  .container{
    padding-left: 100px;
    padding-right: 100px;
  }
  .touch {
    cursor: pointer;
  }
</style>