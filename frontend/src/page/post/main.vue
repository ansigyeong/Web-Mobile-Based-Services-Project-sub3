<template>
<div>
     <full-page :options="options" id="fullpage">
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
                <div class="col-md-2" v-for="(item,index) in all" :key="index">
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
    <!-- <b-carousel
      id="carousel-1"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"

    >
       Text slides with image
      <b-carousel-slide
        caption="First slide"
        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
      ></b-carousel-slide>
    </b-carousel>  -->
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
  export default {
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
</style>