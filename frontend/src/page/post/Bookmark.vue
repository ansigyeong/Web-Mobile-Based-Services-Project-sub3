<template>
  <div class="container">
      <h1 style="margin: 20px;">🏷️ 찜 목록 🏷️</h1>

      <div class="mypage">
        <div class="bookmark_box">
              <!-- c -->
              <p class="langbtns">
                <span v-if="this.$route.params.lang == 'c'">
                  <img src="../../assets/img/cpbtn.png" class="langbtn" alt="">
                </span>
                <span v-else>
                  <img @click="goCBmk" class="langbtn" src="../../assets/img/cbtn.png" alt="">
                </span>
              <!-- cpp -->
                <span v-if="this.$route.params.lang == 'cpp'">
                  <img src="../../assets/img/cpppbtn.png" class="langbtn" alt="">
                </span>
                <span v-else>
                  <img @click="goCppBmk" class="langbtn" src="../../assets/img/cppbtn.png" alt="">
                </span>
              <!-- java -->
                <span v-if="this.$route.params.lang == 'java'">
                  <img src="../../assets/img/javapbtn.png" class="langbtn" alt="">
                </span>
                <span v-else>
                  <img @click="goJavaBmk" class="langbtn" src="../../assets/img/javabtn.png" alt="">
                </span>
              <!-- python -->
                <span v-if="this.$route.params.lang == 'python'">
                  <img src="../../assets/img/pythonpbtn.png" class="langbtn" alt="">
                </span>
                <span v-else>
                  <img @click="goPythonBmk" class="langbtn" src="../../assets/img/pythonbtn.png" alt="">
                </span>
              <!-- others -->
                <span v-if="this.$route.params.lang == 'etc'">
                  <img src="../../assets/img/otherspbtn.png" class="langbtn" alt="">
                </span>
                <span v-else>
                  <img @click="goOthersBmk" class="langbtn" src="../../assets/img/othersbtn.png" alt="">
                </span>
              </p>
        </div>
      </div>

      <div class="que" style="margin-top:40px" v-for="item in paginatedData" :key="item.id"  >
        <div class="stats">
          <h6 style="text-size:small;">답글수</h6>
          <div class="like">
            <strong>{{item.rpCnt}}</strong>
            <span>개</span>
            </div>
        </div>
        <div class="summary">
          <div class="title" style="text-align:left"><a class="tt title-detail" @click="detail(item.queNo,item.lang)">Q: {{item.title}}</a></div>
          <div class="text">
            {{txt(item.contents)}}
          </div>
          <div class="tags">
            <a class="post-tag" @click="moveTagList('/taglist/', item.firstTag)" v-if="item.firstTag!=''">{{item.firstTag}}</a>
            <a class="post-tag" @click="moveTagList('/taglist/', item.secondTag)" v-if="item.secondTag!=''">{{item.secondTag}}</a>
            <a class="post-tag" @click="moveTagList('/taglist/', item.thirdTag)" v-if="item.thirdTag!=''">{{item.thirdTag}}</a>
            </div>
          <div class="others">
            <span class="writerlabel">작성자</span>
            <a @click="userdetail(item.userNo)" class="writer nickname-in-detail">{{item.name}}</a>
            <span class="date">{{item.createDate}}</span>
            </div>
        </div>
      </div>
      <div class="outter">
          <router-link to="/quiz">
            <img src="../../assets/img/quiz.png"> 
          </router-link>    
      </div>

      <div class="pagination" v-show="pageCount!==0" style="float:right;">
        
        <button :disabled="pageNum === 0" @click="firstPage" class="page-btn"><i class="fas fa-caret-square-left"></i></button>
        <button :disabled="pageNum === 0" @click="prevPage" class="page-btn"><i class="far fa-caret-square-left"></i></button>
        <span class="page-count" style="padding-left:5px;padding-right:5px;font-size:1.2em;">{{ pageNum + 1 }} / {{ pageCount }}</span>
        <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn"><i class="far fa-caret-square-right"></i></button>
        <button :disabled="pageNum >= pageCount - 1" @click="lastPage" class="page-btn"><i class="fas fa-caret-square-right"></i></button>
      </div>


  </div>
</template>

<script>
import axios from 'axios'
  export default {
    data(){
      return {
        data : [],
        pageNum: 0,
        type: 0,
        sorting_type: 0,
        item : {'contents':[]}
      }
    },
    props: {
      pageSize:{
        type: Number,
        required: false,
        default:7
      }
    },
    methods: {
      getlist(language) {
        axios.get(this.$store.state.base_url +'/cart',{     
            params: {
              type: this.sorting_type,
            }, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
          this.lang = response.data.data.user.lang
          if (this.lang == null){
            this.lang = 'etc'
          }
          if (language == null){
            this.$router.push('/bookmark/'+this.lang)
          }
          this.data = response.data.data[language]
        })
        .catch((error) => {
          swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
          this.$cookies.remove('auth-token')
          this.$store.commit('checkToken',this.$cookies.get('auth-token'))
          this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
          this.$router.push('/login')
        })
      },
      detail(queNo,lang) {
        this.$router.push('/detail/'+queNo+'/'+lang)
      },
      txt(contents) {
        var temp = contents.replace(/(<([^>]+)>)/ig,"")
        var temp1 = temp.replace("&nbsp;", "")
        var tem = temp1.substring(0,400)
        if (contents.length > 400){
          tem = tem + ' ...  '
        }
        return tem
      },
      nextPage () {
      this.pageNum += 1;
      },
      prevPage () {
        this.pageNum -= 1;
      },
      firstPage () {
        this.pageNum = 0;
      },
      lastPage () {
        this.pageNum = this.pageCount-1;
      },
      selectdelete(queNo,num){
        axios.delete(this.$store.state.base_url +'/cart',{     
            params: {
                queNo: queNo
            }, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
            axios.get(this.$store.state.base_url +'/cart',{     
                params: null, 
                headers : {
                    'ACCESS-TOKEN' : this.$store.state.token
                }   
            })
            .then((response) => {
                if (num == 0) { this.cpp = response.data.data.cpp; }
                if (num == 1) { this.c = response.data.data.c; }
                if (num == 2) { this.java = response.data.data.java; }
                if (num == 3) { this.python = response.data.data.python; }        
            })
        })
        .catch((error) => {
            swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
      },
      moveTagList(path, tag){
          this.$router.push(path+tag);
      },
      one(lang,keyword){
        this.sorting_type = 0;
        this.getlist(lang,keyword)
      },
      two(lang,keyword){
        this.sorting_type = 1;
        this.getlist(lang,keyword)

      },

      goCBmk() {
        this.$router.push('/bookmark/c')
      },

      goCppBmk() {
        this.$router.push('/bookmark/cpp')
      },

      goJavaBmk() {
        this.$router.push('/bookmark/java')
      },

      goPythonBmk() {
        this.$router.push('/bookmark/python')
      },
      goOthersBmk() {
        this.$router.push('/bookmark/etc')
      }


    },
    created() {
      this.getlist(this.$route.params.lang)
    },
    beforeRouteUpdate (to, from, next){
        this.getlist(to.params.lang);
        next();
    },
    computed:{
      pageCount() {
        let listleng = this.data.length,
        listSize = this.pageSize,
        page = Math.floor(listleng/listSize)

        if(listleng % listSize > 0) page +=1;

        return page;
      },
      paginatedData() {
        const start = this.pageNum*this.pageSize,
        end = start + this.pageSize;
        if(this.data!=null)
        return this.data.slice(start, end);
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

  .que{
    display: flex;
    padding: 12px 8px;
    float: none;
    overflow: hidden;
    border-bottom: 1px solid black;
  }
  .stats{
    margin-right: 12px;
    width: 58px;
    float: none;
    text-align: center;
    width: 60px;
    overflow-x:auto;
    overflow-y:auto;
    /* margin-bottom: 8px; */
    line-height:3em;
  }
  .like{
    padding: 7px 0 5px;
    border-radius: 3px;
    background-color: #FEDFE6;
  }
  .summary{
    overflow-x:auto;
    overflow-y:auto;
    flex: 1 auto;
    width: 100px;
    float: none;
    margin: 0;
    margin-left: 3%;
    margin-right: 3%;
    overflow: hidden;
  }
  .tt{
    font-weight: 400;
    word-wrap: break-word;
    word-break: break-word;
    font-size: 25px;
    line-height: 1.4;
    margin-bottom: .5em;
  }
  .contents{
    word-wrap: break-word;
    word-break: break-word;
    padding-bottom: 5px;
    height: 50px;
    overflow: hidden;
  }

  .tags{
    line-height: 18px;
    float: left;
    font-family: 'CookieRun-Regular'
  }
  .post-tag{
    font-size: 15px;
    color: cadetblue;
    background-color: rgb(211, 247, 247);
    border-color: transparent;
    display: inline-block;
    padding: .4em .5em;
    margin: 4px 4px 4px 4px;
    line-height: 1;
    white-space: nowrap;
    text-decoration: none;
    text-align: center;
    border-width: 1px;
    border-style: solid;
    border-radius: 30%;
    font-family: 'CookieRun-Regular'
  }
  .others{
    float: right;
    margin-right: 5%;

  }
  .writer{
    font-size: 14px;
    color: blueviolet;
    cursor: pointer;
    margin-right: 10px;
    margin-left: 10px;
  }
  .date{
    font-size: 12px;
  }
  .h1{
    color: cadetblue;
    background-color: rgb(211, 247, 247);
    border-color: transparent;
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    text-decoration: none;
    text-align: center;
    border-width: 1px;
    border-style: solid;
    border-radius: 30%;
  }
  .mainlang{
    margin-left: 25%;
  }
  /* .bookmark_box .bookmark_list{
    display: table;
    table-layout: fixed;
    box-sizing: border-box;
    width: 100%;
  } */
  .bookmark_list{
    margin: 0;
    padding: 0px 700px 0px 0;
    /* border-bottom: 1px solid #e9e9e9; */
    list-style: none;
    height: 55px;
    width: 785px;
  }
  .bookmark_box{
    padding: 0 700px 0 0;
    /* border-top: 1px solid #e9e9e9; */
  }
  .bookmark_li{
    /* border-right: 1px solid #e9e9e9; */
    display: inline;
    font-size: 30px;
    /* padding-left: 3rem;
    padding-right: 3rem; */
    padding: 5.7px 49px 7px 52px;
    text-align: center;
  }

  .mypage{
    padding-top: 20px;
  }


  .outter{
    position: fixed;
    right:50px;
    bottom:0;
    width: 230px;
    top: 250px;
           /* padding: 150px 50px 0 0 */
  }
  .mainlang{
    margin-left: 25%;
  }
  .page-btn{
    padding-left: 5px;
    padding-right: 5px;
    font-size: 1.3em;
    color: pink;
  }
  .text{
    text-align:left; 
    min-height:50px; 
    margin-top:10px; 
    margin-bottom:10px

  }

  .langbtns {
  position: absolute;
  display: block;
  top: 23.5%;
  left: 33%;
  margin-bottom: 10px;
}

  .langbtn {
      cursor: pointer;
      height: 60px;
  }


.writerlabel {
  font-size: 0.9rem;
}

    .nickname-in-detail {
        font-family: 'CookieRun-Regular';
        color:rgb(105, 0, 202);
        font-size: 1.1rem;
    }

    .nickname-in-detail:hover {
        color: rgb(64, 0, 123);
    }

  .title-detail {
    font-family: 'CookieRun-Regular';
    color:rgb(105, 0, 202);
    font-size: 25px;   
    margin: 0;
    padding: 0;
  }

  .title-detail:hover {
      color: rgb(64, 0, 123);
  }
</style>