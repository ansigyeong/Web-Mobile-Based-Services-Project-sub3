<template>

  <div>

    <full-page :options="options" id="fullpage">
      
      <!-- 1 -->
      <div class="section">
        <FirstMain />

      </div>

      <!-- 2 -->
      <div class="section">
          <p class="floating-icon">
            <img @click="goAsk" class="ftbtn" style="width: 200px; height: 200px;" src="@/assets/img/mainsticker.png" alt="">
          </p>

          <img class="screen2msg" src="@/assets/img/firstmsg.png">
          <img class="screen2img movingimg" src="@/assets/img/carousel.png">
          <img class="underthesea" src="@/assets/img/underthesea.png" alt="">

          <p class="scroll-icon">
            <img style="width: 200px; height: 135px;" src="@/assets/img/underthesea.png" alt="">
          </p>
      </div>

      <!-- 3 -->
      <div class="section">
          <p class="floating-icon">
            <img @click="goAsk" class="ftbtn" style="width: 200px; height: 200px;" src="@/assets/img/mainsticker.png" alt="">
          </p>

          <img class="screen3msg" src="@/assets/img/slide3text.png">
          <img class="screen3img movingimg" src="@/assets/img/man.png">
          <img class="underthesea" src="@/assets/img/underthesea.png" alt="">

          <p class="scroll-icon">
            <img style="width: 200px; height: 135px;" src="@/assets/img/underthesea.png" alt="">
          </p>
      </div>

      <!-- 4 -->

      <div class="section">
        <p class="floating-icon">
          <img @click="goAsk" class="ftbtn" style="width: 200px; height: 200px;" src="@/assets/img/mainsticker.png" alt="">
        </p>
        <p class="floating-btn">
          <img @click="goCList" style="width:100px; height:50px;" class="forubtn ftbtn" src="../../assets/img/cbtn.png" alt="">
          <img @click="goCppList" style="width:100px; height:50px;"  class="forubtn ftbtn" src="../../assets/img/cppbtn.png" alt="">
          <img @click="goJavaList" style="width:100px; height:50px;"  class="forubtn ftbtn" src="../../assets/img/javabtn.png" alt="">
          <img @click="goPythonList" style="width:100px; height:50px;"  class="forubtn ftbtn" src="../../assets/img/pythonbtn.png" alt="">
        </p>

      <div>
        <img class="screen4msg" src="@/assets/img/screen4msg.png" alt="">
      </div>
        <img class="screen4img" src="@/assets/img/bookshelf2.png" alt="">

        <p class="scroll-icon">
          <img style="width: 200px; height: 135px;" src="@/assets/img/underthesea.png" alt="">
        </p>
      
      </div>

      <!-- 5 -->
      <div class="section">
          <p class="floating-icon">
            <img @click="goAsk" class="ftbtn" style="width: 200px; height: 200px;" src="@/assets/img/mainsticker.png" alt="">
          </p>
            <!-- Text slides with image -->
          <MainFooter />
      </div>
    </full-page>
  </div>
</template>


<script>
  import swal from 'sweetalert';
  import '../../assets/css/main.scss'
  import '../../assets/css/mainfooter.scss'
  import '../../assets/css/ThirdMain.scss'
  import '../../assets/css/floatingicon.scss'
  import {Carousel3d, Slide}  from 'vue-carousel-3d'
  import RecordCarousel from '../../components/common/RecordCarousel.vue'
  import MainFooter from './MainFooter.vue'
  import ThirdMain from './ThirdMain.vue'
  import FloatingIcon from './FloatingIcon.vue'
  import FirstMain from './FirstMain.vue'

  export default {
    components: {
      Carousel3d,
      Slide,
      RecordCarousel,
      MainFooter,
      ThirdMain,
      FloatingIcon,
      FirstMain,
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
          sectionsColor: ['#D0EEFF','#D0EEFF','#B2C8E6','#6B91C6','#D0EEFF']
          //  sectionsColor: ['#D0EEFF','#D0EEFF','rgb(146, 187, 255)','rgb(100, 158, 255)','#D0EEFF']
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
        ],

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
        swal('', '로그인 후 이용해 주세요.', 'warning')
        this.$router.push('/login')
      }
    },
    moveReply(){
      if(this.$store.state.islogin){
        this.$router.push('???')
      }else{
        swal('', '로그인 후 이용해 주세요.', 'warning')
        this.$router.push('/login')
      }
    },
    moveList(path, lang){
      this.$router.push(path+lang); 
    },
    goAsk() {
      if (this.$store.state.islogin){
        this.$router.push('/askquestion')
      }
      else{
        this.$router.push('/login')
      }
    },
    goCList() {
      this.$router.push('/list/c')
    },
    goCppList() {
      this.$router.push('/list/cpp')
    },
    goJavaList() {
      this.$router.push('/list/java')
    },
    goPythonList() {
      this.$router.push('/list/python')
    }    
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

  .section_padding {
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

  .card:hover {
    box-shadow: 0 0 11px rgba(33,33,33,.2); 
    }

  body {
    /* background-size: cover; */
    background-size: cover;
  }

  .carousel-3d-slide {
    height: 0px !important;
    /* margin-top: 20px; */
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

  .ftbtn {
    cursor: pointer;
  }

    /* 2번째 화면 */
  /* .screen2msg {
    position: relative;
    float: left;
    margin-top: 100px;
    width: 550px;
    height: 350px;
    margin-left: 65px;
  } */

  /* 두 번째 화면 */
  .screen2msg {
    float: left;
    width: 550px;
    height: 350px;
    margin-left: 150px;
    margin-bottom: 180px;
    z-index: 1;
  }
  
    .screen2img {
    float: right;
    width: 580px;
    height: 350px;
    position: relative;
    bottom: -250px;
    right: 20%;
    margin-bottom: 0px;
  }



    /* 3번째 화면 */
  .screen3msg {
    float: left;
    width: 550px;
    height: 350px;
    margin-left: 150px;
    margin-bottom: 180px;
    z-index: 1;
  }
  
    .screen3img {
    float: right;
    width: 500px;
    height: 400px;
    position: relative;
    bottom: -250px;
    right: 20%;
    margin-bottom: 0px;
  }

  .scroll-icon {
    position: absolute;
    display: fixed;
    bottom: -2%;
    left: 20%;
    width: 25%;
    z-index: 100;
  }


  .floating-icon {
    position: absolute;
    display: block;
    top: 20%;
    right: 15%;
    width: 25%;
    z-index: 100;
  }



  /* 4번쨰 화면 */
  .fourbtn {
    bottom: 100%;
  }

  .floating-btn {
  position: absolute;
  display: block;
  top: 57%;
  left: 14%;
}

  .screen4msg {
    float: left;
    width: 550px;
    height: 350px;
    margin-left: 150px;
    margin-bottom: 180px;

  }
    .screen4img {
    float: right;
    width: 350px;
    height: 400px;
    position: relative;
    bottom: -250px;
    right: 23%;
    margin-bottom: 0px;
  }

</style>