<template>
<div>
     <full-page :options="options" id="fullpage">
       <img class="sticker" src="../../assets/img/mainsticker.png" alt="">
<!-- 1 -->
  <div style="padding-top:50px;" class="section">
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
      
    >
      <!-- Text slides with image -->
      <b-carousel-slide
        caption="First slide"
        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
        <h1>Hello world!</h1>
      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>

      <!-- Slides with img slot -->
      <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
      <b-carousel-slide>
        <template v-slot:img>
          <img
            class="d-block img-fluid w-100"
            width="1024"
            height="480"
            src="https://picsum.photos/1024/480/?image=55"
            alt="image slot"
          >
        </template>
      </b-carousel-slide>
    </b-carousel>
  </div>
<!-- 2 -->
<div class="section">
    <div id="carousel">
      <carousel-3d  style="height: 550px;">
        <span v-for="(item, idx) in items" :key="idx">
             <slide :index="idx">
           
              <div class="crs-bx" style="border: solid 7px #ffd700;">
                <img class="grade-img" :src="item.recodeImg"/>
                <div class="text-box">
                  <h2>{{item.level}}</h2>
                  <h3>{{item.animal}}</h3>
                </div>
              </div>
          
          </slide>
        </span>
      </carousel-3d>
    </div>
</div>
<!-- 3 -->
<div class="section ">
    <div class="section_padding">
      <p style=" font-size:20pt;">질문, 답변, 좋아요 갯수에 따라</p>
      <p style="padding:0 130px 0 0; font-size:50pt;">레벨 업!</p>
      <div style="padding:0 180px 0 0;">
          <v-btn @click="moveQuestion">질문하러</v-btn>
          <v-btn @click="moveReply">답변하러</v-btn>
      </div>
    </div>

        <div class="mt-10 lan_card" >
            <div class="row">
              <div class="col-md-2"/>
                <div class="col-md-2" v-for="(item,idx) in all" :key="idx">
                    <div class="card" :class="'mark'+item.category"><img class="card-img-top" :src="item.imgSrc" @click="moveList('/list/', item.lang)"/>
                        <div class="card-body">
                            <h5 class="card-title" :class="'mark'+item.categorye">{{ item.title }}</h5>
                            <p class="card-text">{{ item.description }}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-2"/>
            </div>
        </div>

</div>
<!-- 4 -->
<div class="section">
    <b-carousel
      id="carousel-1"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"

    >
      <!-- Text slides with image -->
      <b-carousel-slide
        caption="First slide"
        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
        img-src="https://im5.ezgif.com/tmp/ezgif-5-1238766f947b.png"

      ></b-carousel-slide>

    </b-carousel>

</div>
<!-- 5 -->
<div class="section">
    <b-carousel
      id="carousel-1"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"

    >
      <!-- Text slides with image -->
    <b-carousel-slide
        img-src="https://im5.ezgif.com/tmp/ezgif-5-15fa9a01c137.gif">

        <div style="text-align:left; display: flex-box; justify-content: left; align-items: left; padding-bottom: 150px;">
            <h2 style="font-size: 60px;">헬로우 코드씨!</h2>
            <h4>어두운 코딩 바닷 속,</h4>
            <h4>헬로우 코드씨가</h4>
            <h4>한줄기 빛이되어드릴게요!</h4>
        </div>
 
        <div style="text-align: left; padding-bottom: 30px;">
            <h5>대표</h5>
            <p style="margin-bottom: 3px;">백단비 | miinding21@gmail.com</p>
            <p style="margin-bottom: 3px;">도정우 | miinding21@gmail.com</p>
            <p style="margin-bottom: 3px;">김홍준 | miinding21@gmail.com</p>
            <p style="margin-bottom: 3px;">안시경 | miinding21@gmail.com</p>
            <p style="margin-bottom: 3px;">김민지 | miinding21@gmail.com</p>
        </div>
    </b-carousel-slide>

    </b-carousel>
</div>
  
     </full-page>
</div>


</template>

<script>
  import {Carousel3d, Slide}  from 'vue-carousel-3d'

  export default {
    components: {
      Carousel3d,
      Slide
    },
    data() {
      return {
        slide: 0,
        sliding: null,
        options : {
          // licenseKey: 'YOUR_KEY_HERE',
          // afterLoad: this.afterLoad,
          // scrollOverflow: true,
          // scrollBar: false,
          navigation: true,
          // anchors: ['page1', 'page2', 'page3'],
          sectionsColor: ['#D0EEFF','#D0EEFF','#D0EEFF','#D0EEFF','#D0EEFF']
        },
                all: [
                {
                    category: "AAA",
                    lang: "c",
                    imgSrc: require("../../assets/img/C.png"),
                    description:
                    "C"
                },
                {
                    category: "BBB",
                    lang: "cpp",
                    imgSrc: require("../../assets/img/cpp.png"),
                    description:
                    "C++"
                },
                {
                    category: "AAA",
                    lang: "java",
                    imgSrc: require("../../assets/img/JAVA.png"),
                    description:
                    "JAVA"
                },
                {
                    category: "BBB",
                    lang: "python",
                    imgSrc: require("../../assets/img/Python.png"),
                    description:
                    "PYTHON"
                }
                ],
     items : [
        {index: '1', animal: '🦈 백상아리 🦈', level: 'Lv. 7', score: '600 점 이상', recodeImg: '../../assets/img/lv6.png' }, 
        {index: '2', animal: '🦠 플랑크톤 🦠', level: 'Lv. 1', score: '0 ~ 99 점', recodeImg: '../../assets/img/lv5.png' }, 
        {index: '3', animal: '🐟 멸치 🐟', level: 'LV. 2', score: '100 ~ 199 점', recodeImg: '../../assets/img/lv4.png'}, 
        {index: '4', animal: '🦐 새우 🦐', level: 'LV. 3', score: '200 ~ 299 점', recodeImg: '../../assets/img/lv3.png'}, 
        {index: '5', animal: '🦑 해파리 🦑', level: 'LV. 4', score: '300 ~ 399 점', recodeImg: '../../assets/img/lv2.png'}, 
        {index: '6', animal: '🐙 문어 🐙', level: 'LV. 5', score: '400 ~ 499 점', recodeImg: '../../assets/img/lv1.png'}, 
        {index: '7', animal: '🐬 돌고래 ', level: 'LV. 6', score: '500 ~ 599 점', recodeImg: '../../assets/img/lv0.png'}
        ]

      }
    },
    methods: {
      onSlideStart(slide) {
        this.sliding = true
      },
      onSlideEnd(slide) {
        this.sliding = false
      },
    moveQuestion(){
      if(this.$store.state.islogin){
        this.$router.push('/askquestion')

      }else{
        alert('로그인 후 이용해 주세요.')
        this.$router.push('/login')
      }
    },
    moveReply(){
      if(this.$store.state.islogin){
        this.$router.push('???')
      }else{
        alert('로그인 후 이용해 주세요.')
        this.$router.push('/login')
      }
    },
    moveList(path, lang){
      console.log(lang)
      this.$router.push(path+lang); 
    }    

    }
  }
</script>
<style scoped>
.section_padding{
  padding: 100px 900px 0 0;
  font-weight: bolder;
}
  .card-img-top {
    padding-right: 10px;
    padding-left: 10px;
    padding-top: 10px;
  }

  .card-title {
    padding: 0.8rem 1rem;
    color: #000;
  }
  .card {
    border: 2px solid thin;
  }
  .card:hover
   {
    box-shadow: 0 0 11px rgba(33,33,33,.2); 
    }
  /* .markBBB {
    border: 2px solid thin;
    margin-top: 70px;
  } */
  body {
    background-size: cover;
  }
  .carousel-3d-slide {
    height: 550px !important;
    margin-top: 20px;
    /* background-color: red; */
  }
  .crs-bx {
    padding-top: 20px;
    text-align: center;
    vertical-align: middle;
    border: solid 2px #000;
    background-color: white;
    height: 530px;
  }
  .grade-img {
    padding: 30px;
    border: solid 3px #E2E2E2;
    width: 250px;
    height: 250px;
  }
  .text-box {
    margin: 20px;
    height: 186px;
  } 
   .grade-guide {
    padding: 0px;
  } 
</style>