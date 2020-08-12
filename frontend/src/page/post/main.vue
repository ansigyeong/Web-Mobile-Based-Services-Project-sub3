<template>
<div>
<full-page :options="options">
<!-- 1 -->
  <div style="padding-top:50px;">
    <div class="section">
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

      <!-- Slide with blank fluid image to maintain slide aspect ratio -->
      <!-- <b-carousel-slide caption="Blank Image" img-blank img-alt="Blank image">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eros felis, tincidunt
          a tincidunt eget, convallis vel est. Ut pellentesque ut lacus vel interdum.
        </p>
      </b-carousel-slide> -->
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
          // controlArrows: true,
          // scrollBar: true
        }
      }
    },
    methods: {
      onSlideStart(slide) {
        this.sliding = true
      },
      onSlideEnd(slide) {
        this.sliding = false
      }
    },
    destroyed(){
      $.fn.fullpage.destroy('all');
    }
  }
</script>