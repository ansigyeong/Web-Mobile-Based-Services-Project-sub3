<template>
  <div>
    <h1>지금 바로 <strong>백상아리</strong>에 도전하세요</h1>
    <div id="carousel">
      <carousel-3d  style="height: 480px;">
        <span v-for="n in 7" :key="n">
          <slide :index="n-1">
            <div class="crs-bx" style="border: solid 7px #ffd700;">
              <img class="grade-img" :src="require('../../assets/img/lv'+remainder(level(user.grade)+n-1)+'.png')"/>
              <div class="text-box">
                <h3>{{animal(remainder(level(user.grade)+n-1))}}</h3>
                <h2>LV. {{remainder(level(user.grade)+n-1)+1}}</h2>
                <h3>{{boundary[remainder(level(user.grade)+n-1)]}}</h3>
              </div>
            </div>
          </slide>
        </span>
      </carousel-3d>
    </div>
  </div>  
</template>

<script>
  import {Carousel3d, Slide}  from 'vue-carousel-3d'
  import axios from 'axios'
  export default {
    name: 'RecordCarousel',
    components: {
      Carousel3d,
      Slide
    },
    data () {
      return {
        user : null,
        boundary : [
          '0 ~ 99 점',
          '100 ~ 199 점',
          '200 ~ 299 점',
          '300 ~ 399 점',
          '400 ~ 499 점',
          '500 ~ 599 점',
          '600 점 이상'],
        line : [0,100,200,300,400,500,600],
        grade: 650,
      }
    },
    methods : {
      getuser() {
        axios.get(this.$store.state.base_url + '/info/mygrade',{
          params: {
          },
          headers: {
            "ACCESS-TOKEN" : this.$store.state.token
            }
        })
        .then((response) => {
          this.user = response.data.data.hof
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
      animal(level){
        if (level==0){return '🦠 플랑크톤 🦠'}
        else if (level==1){return '🐟 멸치 🐟'}
        else if (level==2){return '🦐 새우 🦐'}
        else if (level==3){return '🦑 해파리 🦑'}
        else if (level==4){return '🐙 문어 🐙'}
        else if (level==5){return '🦈 돌고래 🦈'}
        else {return '🐳 백상아리 🐳'}
      },
      stage(level){
        if (level==0){return '플랑크톤'}
        else if (level==1){return '멸치'}
        else if (level==2){return '새우'}
        else if (level==3){return '해파리'}
        else if (level==4){return '문어'}
        else if (level==5){return '돌고래'}
        else {return '백상아리'}   
      },
      remainder(val){
        return val%7
      }
    },
    created() {
      this.getuser()
    }
  }
</script>

<style scoped>
  body {
    background-size: cover;
  }
  .carousel-3d-slide {
    height: auto !important;
    margin-top: 20px;
  }
  .crs-bx {
    padding-top: 20px;
    text-align: center;
    vertical-align: middle;
    border: solid 2px #000;
    background-color: white;
    height: 450px;
  }
  .grade-img {
    padding: 30px;
    border: solid 3px #E2E2E2;
    width: 250px;
    height: 250px;
  }
  .text-box {
    margin: 20px;
  } 
</style>