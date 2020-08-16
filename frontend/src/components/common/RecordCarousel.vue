<template>
  <div id="carousel" >
      <carousel-3d class="carousel-3d-container" style="height: 430px !important;" >
        <span v-for="(item, idx) in items" :key="idx">
          <slide :index="idx">
            <div class="crs-bx" style="border: solid 3px #000; heigt: 400px;">
              <img class="grade-img" :src="require('../../assets/img/lv'+item.recordImg+'.png')"/>
              <div class="text-box">
                <h2>{{ item.level }}</h2>
                <h3>{{ item.animal }}</h3>
              </div>
            </div>
          </slide>
        </span>
      </carousel-3d>
</div>
</template>


<script>
  import {Carousel3d, Slide}  from 'vue-carousel-3d'
  export default {
    name: 'RecordCarousel',
    components: {
      Carousel3d,
      Slide
    },
    data() {
      return {
            items : [
            {animal: ' 백상아리 ', level: 'Lv. 7', score: '600 점 이상', recordImg: 6 }, 
            {animal: '🦠 플랑크톤 🦠', level: 'Lv. 1', score: '0 ~ 99 점', recordImg: 0 }, 
            {animal: ' 멸치 ', level: 'LV. 2', score: '100 ~ 199 점', recordImg: 1}, 
            {animal: ' 새우 ', level: 'LV. 3', score: '200 ~ 299 점', recordImg: 2}, 
            {animal: ' 해파리 ', level: 'LV. 4', score: '300 ~ 399 점', recordImg: 3}, 
            {animal: ' 문어 ', level: 'LV. 5', score: '400 ~ 499 점', recordImg: 4}, 
            {animal: ' 돌고래 ', level: 'LV. 6', score: '500 ~ 599 점', recordImg: 5}
            ]
      }
    }
  }
</script>


<style scoped>
  .carousel-3d-container {
    position: relative;
    bottom: -100px;
    left: 235px;
  }
  .carousel-3d-slide {
  height: 410px !important;
  margin-top: 20px;
  }
  .crs-bx {
    padding-top: 20px;
    text-align: center;
    vertical-align: middle;
    border: solid 2px #000;
    background-color: white;
    height: 410px;
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