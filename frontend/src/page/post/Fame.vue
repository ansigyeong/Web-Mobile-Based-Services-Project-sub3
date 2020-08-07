<template>
  <div class="container">
    <h1>명예의 전당</h1>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <div class="mt-5">
      <div class="row">
        <div v-for="(item,index) in items" :key="index">
          <div class="card" :class="'mark'+index">
            <img class="card-img-top" :src="require('../../assets/img/lv'+level(item.grade)+'.png')"/>
            <div class="card-body">
              <h3>{{ item.name }}</h3>
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
        all: [
          {
            category: "AAA",
            title: "홍길동",
            imgSrc: "https://picsum.photos/200/150.jpg",
            description:
              "🐬 돌고래 등급 🐬"
          },
          {
            category: "BBB",
            title: "김길동",
            imgSrc: "https://picsum.photos/200/150.jpg",
            description:
              "🦈 백상아리 등급 🦈"
          },
          {
            category: "CCC",
            title: "이길동",
            imgSrc: "https://picsum.photos/200/150.jpg",
            description:
              "🐬 돌고래 등급 🐬"
          }
        ],
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
          console.log(this.items)
        })
        .catch((error) => {
          console.log(error)
        })
      },
      level(grade){
        if (grade>= 0 && grade < 30){return 0}
        else if (grade>=30 && grade<50){return 1}
        else if (grade>=50 && grade<70){return 2}
        else if (grade>=70 && grade<90){return 3}
        else if (grade>=90 && grade<110){return 4}
        else if (grade>=110 && grade<130){return 5}
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
      }
    },
    created() {
      this.getlist()
    }
  }
</script>

<style scoped>
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
  }
  .mark1 {
    border: 2px solid #ffb52e;
    width: 397px;
    margin-right: 16px;
  }
  .mark2 {
    border: 2px solid #deb887;
    width: 290px;
    margin-top: 50px;
  }
  .container{
    padding-left: 100px;
    padding-right: 100px;
  }
</style>