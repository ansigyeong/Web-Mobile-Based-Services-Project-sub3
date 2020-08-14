<template>
  <div class="container">
      <h1>찜 목록</h1>
      <b-form-select v-model="lang" :options="langname" ></b-form-select>
      <span v-for=" idx in 4" :key="idx">
        <h2>{{langname[idx-1]}}</h2>
          <template>
          <div class="que" v-for="item in langs[idx-1]" :key="item.id"  >
            <div class="stats">
              <h6 style="text-size:small;">답글수</h6>
              <div class="like">
                <strong>{{item.rpCnt}}</strong>
                <span>개</span>
                </div>
            </div>
            <div class="summary">
              <div class="title"><a class="tt" @click="detail(item.queNo,item.lang)"> Q: {{item.title}}</a></div>
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
          </template>


      </span>

  </div>
</template>

<script>
import axios from 'axios'
  export default {
    data(){
      return {
          cpp : null,
          python : null,
          c : null,
          java : null,
            fields: [{key:'rpCnt', label:'답글 수'},
            {key:'title', label: '제목'},
            {key: 'createDate', label: '작성 시간'},
            {key: 'actions', label: '상세 보기' },
            {key: 'deletebtn', label:'찜 삭제'}],
          
          langname : ['C','C++','Java','Python'],
          langs : null,
          lang: 'Python'

      }
    },
    methods: {
      getlist() {
        axios.get(this.$store.state.base_url +'/cart',{     
            params: null, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
          console.log(response)
          this.cpp = response.data.data.cpp;
          this.c = response.data.data.c;
          this.java = response.data.data.java;
          this.python = response.data.data.python;
          this.langs = [this.c,this.cpp,this.java,this.python]
        })
        .catch((error) => {
            swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
      },
      indexing(lang) {
        for (var i=0; i < 4; i++){
          if (this.langname[i] == lang){
            return i
          }
        }
      },
      detail(queNo,lang) {
        this.$router.push('/detail/'+queNo+'/'+lang)
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
      }
    },
    created() {
      this.getlist()
    }
  }
</script>

<style scoped>

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
    height: 50px;
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

</style>