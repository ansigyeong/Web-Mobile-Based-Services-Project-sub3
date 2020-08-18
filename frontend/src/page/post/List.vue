<template>
  <div class="container">
    <span v-if="this.$route.params.lang == 'java'">
      <h1 style="margin: 20px;">📔 Java 📔</h1>
    </span>
    <span v-if="this.$route.params.lang == 'cpp'">
      <h1 style="margin: 20px;">📔 C++ 📔</h1>
    </span>
    <span v-if="this.$route.params.lang == 'c'">
      <h1 style="margin: 20px;">📔 C언어 📔</h1>
    </span>
    <span v-if="this.$route.params.lang == 'python'">
      <h1 style="margin: 20px;">📔 Python 📔</h1>
    </span>
    <span v-if="this.$route.params.lang == 'all'">
      <h1 style="margin: 20px;">📔 All 📔</h1>
    </span>
    <span v-if="this.$route.params.lang == 'others'">
      <h1 style="margin: 20px;">📔 Others 📔</h1>
    </span>
    <div>
      <v-row>
        <v-col class="mainlang">
        </v-col>
        <v-col class="sort" cols="3" style="height:50px;">
          <v-btn v-show="this.sorting_type==0" color="success" text @click="one(lang,keyword)">최신순</v-btn>
          <v-btn v-show="this.sorting_type==1" color="primary" text @click="one(lang,keyword)">최신순</v-btn>
          <v-btn v-show="this.sorting_type==0" color="primary" text @click="two(lang,keyword)">답글순</v-btn>
          <v-btn v-show="this.sorting_type==1" color="success" text @click="two(lang,keyword)">답글순</v-btn>
        </v-col>
      </v-row>
    </div>
    <template>
    <div class="que" v-for="item in paginatedData" :key="item.id"  >
      <div class="stats">
        <h6 style="text-size:small;">답글수</h6>
        <div class="like">
          <strong>{{item.rpCnt}}</strong>
          <span>개</span>
          </div>
      </div>
      <div class="summary">
        <div class="title" style="text-align:left"><a class="tt" @click="detail(item.queNo,item.lang)">Q: {{item.title}}</a></div>
        <div class="text">
          {{txt(item.contents)}}
        </div>
        <div class="tags">
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag1)" v-if="item.tag1!=''">{{item.tag1}}</a>
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag2)" v-if="item.tag2!=''">{{item.tag2}}</a>
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag3)" v-if="item.tag3!=''">{{item.tag3}}</a>
          </div>
        <div class="others">
          <a @click="userdetail(item.userNo)" class="writer">{{item.name}}</a>
          <span class="date">{{item.createDate}}</span>
          </div>
      </div>
    </div>
    <div class="outter">
  <a href="https://redmonk.com/sogrady/2020/02/28/language-rankings-1-20/?utm_source=rss&utm_medium=rss&utm_campaign=language-rankings-1-20" target="_blank">
           <img src="../../assets/img/redmonk.png"> 
        </a>      
    </div>
    <div class="pagination" v-show="pageCount!==0" style="float:right;">
      
      <button :disabled="pageNum === 0" @click="firstPage" class="page-btn"><i class="fas fa-caret-square-left"></i></button>
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn"><i class="far fa-caret-square-left"></i></button>
      <span class="page-count" style="padding-left:5px;padding-right:5px;font-size:1.2em;">{{ pageNum + 1 }} / {{ pageCount }}</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn"><i class="far fa-caret-square-right"></i></button>
      <button :disabled="pageNum >= pageCount - 1" @click="lastPage" class="page-btn"><i class="fas fa-caret-square-right"></i></button>
    </div>
    </template>

  </div>
</template>

<script>
import axios from 'axios'
  export default {
    data(){
      return {
        pageNum: 0,
        type: 0,
        data: [],
        sorting_type: 0,
        lang: this.$route.params.lang,
        keyword: this.$route.params.keyword
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
      getlist(lang, keyword) {
        axios.get(this.$store.state.base_url +'/question',{
          params:{
            lang: lang,
            type: this.sorting_type,
            keyword: keyword}
        })
        .then((response) => {
          this.data = response.data.data.list
          if(this.data.length == 0){
            swal('', '게시글이 존재하지 않습니다.\n첫번째 질문을 작성해 보세요.', 'warning')
            this.$router.push('/askquestion')
          }
        })
      },
      detail(queNo,lang) {
        this.$router.push('/detail/'+queNo+'/'+lang)
      },
      userdetail(userNo){
        this.$router.push('/profile/'+userNo)
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
      txt(contents) {
        var temp = contents.replace(/(<([^>]+)>)/ig,"")
        var temp1 = temp.replace("&nbsp;", "")
        var tem = temp1.substring(0,400)
        if (contents.length > 400){
          tem = tem + ' ...  '
        }
        return tem
      }
    },
    created() {
      this.getlist(this.$route.params.lang,this.$route.params.keyword)
    },
    beforeRouteUpdate (to, from, next){
        this.getlist(to.params.lang, to.params.keyword);
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

  .container{
    padding-left: 130px;
    padding-right: 130px;
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
    background-color: #d091d8;
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
  }
  .post-tag{
    font-size: 12px;
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
  /* .pagination {
    display: inline-block;
  }
  .pagination button {
    color: black;
    float: left;
    padding: 8px 16px;
    text-decoration: none;
} */

.outter{
    position: fixed;
    right:50px;
    bottom:0;
    height:px;
    width: 170px;
    top: 200px;
           /* padding: 150px 50px 0 0 */
}
</style>