<template>
  <div class="container">
    <a class="maintag">{{this.$route.params.tag}}</a>
    <template>
    <div class="que" v-for="item in paginatedData" :key="item.id"  >
      <div class="stats">
        <h6 style="text-size:small;">답글수</h6>
        <div class="like">
          <!-- <p >답글수</p> -->
          <strong>{{item.rpCnt}}</strong>
          <span>개</span>
          </div>
      </div>
      <div class="summary">
        <div class="title"><a class="tt" @click="detail(item.queNo,item.lang)"> Q: {{item.title}}</a></div>
        <div v-html="item.contents" style="text-align:left"></div>
        <div class="tags">
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag1)" v-if="item.tag1!=''">{{item.tag1}}</a>
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag2)" v-if="item.tag2!=''">{{item.tag2}}</a>
          <a class="post-tag" @click="moveTagList('/taglist/', item.tag3)" v-if="item.tag3!=''">{{item.tag3}}</a>
          </div>
        <div class="others">
          <!-- <span class="lang">{{item.lang}}</span> -->
          <a @click="userdetail(item.userNo)" class="writer">{{item.name}}</a>
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


    <!-- <b-table :items="data" :fields="fields" :per-page="perPage" :current-page="currentPage" striped responsive="sm">
      <slot></slot>
      
      <template v-slot:cell(useractions)="row">
        <a size="sm" @click="userdetail(row.item.userNo)" class="mr-1">
          {{row.item.name}}
        </a>
      </template>
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
          상세보기
        </b-button>
      </template>
    </b-table> -->
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
          console.log(response)
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
        console.log(this.tag)
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
        console.log(start)
        console.log(end)
        console.log(this.data)
        if(this.data!=null)
        return this.data.slice(start, end);
      }
    }
  }
</script>

<style scoped>
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
    font-size: 18px;
    line-height: 1.4;
    margin-bottom: .5em;
  }
  .contents{
    word-wrap: break-word;
    word-break: break-word;
    padding-bottom: 5px;
    height: px;
    overflow: hidden;
  }

  .tags{
    line-height: 18px;
    float: left;
    margin-left: 5%;
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
  .maintag{
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
  .mainlang{
    margin-left: 25%;
  }
  .page-btn{
    padding-left: 5px;
    padding-right: 5px;
    font-size: 1.3em;
    color: pink;
  }
</style>