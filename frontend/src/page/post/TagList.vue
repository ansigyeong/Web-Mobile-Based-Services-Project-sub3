<template>
  <div class="container">
    <a class="maintag" style="margin: 20px;">{{this.$route.params.tag}}</a>
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
        <div class="title" style="text-align:left"><a class="tt title-detail" @click="detail(item.queNo,item.lang)">Q: {{item.title}}</a></div>
        <div class="text">
          {{txt(item.contents)}}
        </div>
        <div class="tags">
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag1)" v-if="item.tag1!=''">{{item.tag1}}</a>
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag2)" v-if="item.tag2!=''">{{item.tag2}}</a>
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag3)" v-if="item.tag3!=''">{{item.tag3}}</a>
          </div>
        <div class="others">
          <span class="writerlabel">작성자</span>
          <a @click="userdetail(item.userNo)" class="writer nickname-in-detail">{{item.name}}</a>
          <span class="date">{{item.createDate}}</span>
          </div>
      </div>
    </div>
     <div class="pagination" style="float:right;">
      
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
        data: null,
        fields: [{key:'rpCnt', label:'답글 수'},
        {key:'lang', label: '언어'},
        {key:'title', label: '제목'},
        {key: 'createDate', label: '작성 시간'},
        {key: 'useractions', label:'질문자'},
        {key: 'actions', label: '상세 보기' }],
        sorting_type: 0,
        tag: this.$route.params.tag
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
      getlist(tag) {
        axios.get(this.$store.state.base_url +'/tagList',{
          params:{
            type: this.sorting_type,
            tag: tag}
        })
        .then((response) => {
          this.data = response.data.data.list
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
      this.getlist(this.$route.params.tag)
    },
    beforeRouteUpdate (to, from, next){
        this.getlist(to.params.tag);
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
  .maintag{
    font-family: 'CookieRun-Regular';
    font-size: 30px;
    color: cadetblue;
    background-color: rgb(211, 247, 247);
    border-color: transparent;
    display: inline-block;
    padding: .4em .5em;
    margin: 7px 7px 7px 7px;
    line-height: 1;
    white-space: nowrap;
    text-decoration: none;
    text-align: center;
    border-width: 1px;
    border-style: solid;
    border-radius: 30%;
  }

.outter{
    position: fixed;
    right:50px;
    bottom:0;
    width: 190px;
    top: 200px
           /* padding: 150px 50px 0 0 */
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