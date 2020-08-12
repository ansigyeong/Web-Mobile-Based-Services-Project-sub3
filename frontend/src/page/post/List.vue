<template>
  <div class="container">
    <h1>{{this.$route.params.lang}}</h1>
    <template>
    <div class="que" v-for="item in data" :key="item.id" >
      <div class="stats">
        <h6 style="text-size:small;">답글수</h6>
        <div class="like">
          <!-- <p >답글수</p> -->
          <strong>{{item.rpCnt}}</strong>
          <span>개</span>
          </div>
      </div>
      <div class="summary">
        <div class="title"><a class="tt" @click="detail(item.queNo)"> Q: {{item.title}}</a></div>
        <div class="contents">{{item.contents}}</div>
        <div class="tags">
          <a class="post-tag" v-if="item.tag1!=''">{{item.tag1}}</a>
          <a class="post-tag" v-if="item.tag2!=''">{{item.tag2}}</a>
          <a class="post-tag" v-if="item.tag3!=''">{{item.tag3}}</a>
          </div>
        <div class="others">
          <!-- <span class="lang">{{item.lang}}</span> -->
          <a @click="userdetail(item.userNo)" class="writer">{{item.name}}</a>
          <span class="date">{{item.createDate}}</span>
          </div>
      </div>
    </div>
    <b-pagination
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        align="center"
      ></b-pagination>
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
        currentPage:1,
        perPage:10,
        type: 0,
        data: null,
        fields: [{key:'rpCnt', label:'답글 수'},
        {key:'lang', label: '언어'},
        {key:'title', label: '제목'},
        {key: 'createDate', label: '작성 시간'},
        {key: 'useractions', label:'질문자'},
        {key: 'actions', label: '상세 보기' }],
        sorting_type: 0,
        lang: this.$route.params.lang,
        keyword: this.$route.params.keyword
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
          console.log(response)
          this.data = response.data.data.list
        })
      },
      detail(queNo) {
        this.$router.push('/detail/'+queNo)
      },
      userdetail(userNo){
        this.$router.push('/profile/'+userNo)
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
      data(){
        const items = this.$store.getters.loadedData
        return items.slice(
          (this.currentPage - 1) * this.perPage,
          this.currentPage * this.perPage
        )
      },
      rows(){
        return this.data.length;
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
</style>