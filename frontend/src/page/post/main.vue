<template>
<div id="main_vue" class="container">
        <!-- 언어 별 카드 -->
    <img class="main_logo" src="@/assets/img/hello_logo.png" >

        <!-- <div>  
            <h1 class="title">For developers, by developers</h1>
                <h1 slot="header">Canyons</h1>
                <p slot="content">Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
        </div> -->

        <div class="mt-10 lan_card" >
            <div class="row">
                <div class="col-md-3" v-for="(item,index) in all" :key="index">
                    <div class="card" :class="'mark'+item.category"><img class="card-img-top" :src="item.imgSrc" @click="moveList('/list/', item.lang)"/>
                        <div class="card-body">
                            <h5 class="card-title" :class="'mark'+item.categorye">{{ item.title }}</h5>
                            <p class="card-text">{{ item.description }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 배너 -->

        <!-- 푸터 -->

</div>
</template>



<script>
    // import '../../assets/css/post.scss'

    export default {
        name:"Main",
        components:{

        },
        watch: {
        },
        created() {
        },
        methods: {
           moveList(path, lang){
            console.log(lang)
            this.$router.push(path+lang); 
          }
        },
        data() {
            return {
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
        }
    }

</script>



<style scoped>
.main_logo {
        padding-top: 100px;
    }
  .card-img-top {
    padding-right: 10px;
    padding-left: 10px;
    padding-top: 10px;
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
  .card {
    border: 2px solid thin;
  }
  .card:hover
   {
    box-shadow: 0 0 11px rgba(33,33,33,.2); 
    }
  .markBBB {
    border: 2px solid thin;
    margin-top: 70px;
  }
  .container{
    padding-left: 20px;
    padding-right: 20px;
  }
</style>