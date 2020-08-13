<template>
  <div class="container">
      <div style="text-align: left">
        <h2>{{this.items.title}}</h2>
        <div style="display:flex; justify-content:space-between">
            <h7>작성 시간: {{this.items.createDate}}</h7>
            <span v-if="this.$store.state.islogin"> 
            <div>
                <a class="land" @click="deletequestion">글 삭제</a>
                <a class="land" @click="updatequestion">글 수정</a>
                <span v-if="this.flag">
                <img  src='../../assets/img/nostar.png' class="land" width="30px"  @click="selectquestion">
                </span>
                <span v-else>
                <img src='../../assets/img/star.png' class="land" width="30px" @click="selectdelete">
                </span>
            </div>
            </span>   
        </div>
      </div>
        <hr>
        <b-media>
          <template v-slot:aside>
            <div class="userlanding">
                <img :src="getimage(items.grade)" width="100px" height="100px" alt="">
                <p style="margin-top:5px; margin-bottom:3px">{{items.name}}</p>
            </div>
          </template>
        <div class="bording" >
            <div style="height:10px"></div>
            <div v-html="items.contents" style="margin:20px"></div>
            <div style="height:10px"></div>
        </div>
        </b-media>  

    <hr>
    <h2>{{replyitems.length}}개의 답변</h2>
    <span v-for="(item,idx) in replyitems" :key="idx">
        <b-media right-align>
        <div class="bording" >
            <div style="height:10px"></div>
            <div v-html="item.contents" style="margin:20px"></div>
            <div style="height:10px"></div>
        </div>
          <template v-slot:aside>
            <div style="margin-right:20px">
                <div class="userlanding">
                    <img src='../../assets/img/lv2.png' width="100px" height="100px" alt="">
                    <p style="margin-top:5px; margin-bottom:3px">{{item.name}}</p>
                </div>
                <img src="../../assets/img/delete.png" @click="replydelete(item.rpNo)" class="photo">
                <img src="../../assets/img/update.png" @click="replyupdate(item.rpNo)" class="photo">        
            </div>
          </template>
        </b-media> 
        <div style="height:40px"></div> 
    </span>
   
    <span v-if="this.$store.state.islogin">  
    <b-container fluid>
            <div style="text-align:center">
            <h2>댓글 작성</h2>
            <div style="text-align:center">
                <editor api-key="vem3wnp12tvfllgyuf92uzd6e04f9ddz4ke9mzv8uh71ctgq" :init="{
                    height: 300,
                    width: 1000,
                    menubar: ['file edit view insert format tools'],
                    plugins: [
                        'advlist autolink lists link image charmap print preview anchor',
                        'searchreplace visualblocks code fullscreen',
                        'insertdatetime media table paste code help wordcount codesample'
                    ],
                    toolbar:
                        'undo redo codesample | formatselect | bold italic backcolor | \
                        alignleft aligncenter alignright alignjustify | \
                        bullist numlist outdent indent | removeformat | help'
                    }" v-model="replycontents" />
            </div>
            </div>
            <b-button @click="writereply">답변 등록</b-button>
    </b-container>
    </span>
<i class="fas fa-heart"></i>
  </div>
</template>

<script> 
import axios from 'axios'
import Editor from '@tinymce/tinymce-vue'
import { component as VueCodeHighlight } from 'vue-code-highlight'
import VuePrism from 'vue-prism'
    export default { 
        components: {
            'editor': Editor,
            VueCodeHighlight,
            VuePrism
        },
        data () {
            return {
                items: null,
                title: null,
                contents: null,
                lang: null,
                replycontents: null,
                replyitems: null,
                fields: [{key:'rpLike', label:'좋아요'},
                    {key:'replyactions', label: '작성자'},
                    {key:'contents', label: '내용'},
                    {key: 'createDate', label: '작성 시간'},
                    {key: 'rpdelete', label: '댓글 삭제'},
                    {key: 'rpupdate', label: '댓글 수정'},
                    {key: 'replylike', label: '좋아요'}],
                flag: true,
                selectlist: null,
                userfields: [{key:'title', label:'제목'},
                {key:'contentsactions', label:'내용'},
                {key:'createDate', label:'작성시간'},
                {key:'useractions', label:'질문자'},
                {key:'lang', label:'언어'}],
                } 
        },
        created() {
            this.queNo = this.$route.params.queNo;
            this.getdetail();
            this.checkflag()
        },
        computed: {
            getcontents: function() 
            {
                return this.contents
            }
        },
        methods: {
            userdetail(userNo){
                this.$router.push('/profile/'+userNo)
            },
            getdetail() {
                axios.get(this.$store.state.base_url +'/question/detail',{     
                    params: {
                        queNo: this.queNo,
                        type: 0
                    },
                    headers : {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                })
                .then((response) => {
                    console.log(response)
                    this.items = {
                    title: response.data.data.question.title,
                    contents:response.data.data.question.contents,
                    createDate:response.data.data.question.createDate,
                    name:response.data.data.user.name,
                    lang:response.data.data.question.lang,
                    userNo: response.data.data.user.userNo,
                    grade: response.data.data.user.grade
                    }
                    this.title = response.data.data.question.title
                    this.contents = response.data.data.question.contents
                    this.lang = response.data.data.question.lang
                    this.replyitems = response.data.data.rpList
                    console.log(this.items)
                })
                .catch((error) => {
                    console.log(error)
                })
            },
            deletequestion() {
                axios.delete(this.$store.state.base_url +'/question',{     
                    params: {
                        queNo: this.queNo,
                    }, 
                    headers : {
                        'ACCESS-TOKEN' : this.$store.state.token
                    }   
                })
                .then((response) => {
                    console.log(response)
                    if (response.data.status) {
                        alert('질문이 성공적으로 삭제 되었습니다.')
                        this.$router.go(-1)
                    }
                    else {
                        alert('작성자만 삭제가 가능합니다.')
                    }
                })
          
            },
            updatequestion(queNo) {
                this.$store.commit("updateinfo",{
                    title : this.title,
                    contents : this.contents,
                    lang : this.lang
                })
                this.$router.push('/updatequestion/'+this.$route.params.queNo)
            },
            writereply() {
                let config = {
                    headers: {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                }
                let body = {
                    queNo: this.queNo,
                    contents: this.replycontents
                }
                axios.post(this.$store.state.base_url +'/reply', body, config)
                .then((response) => {
                    axios.get(this.$store.state.base_url +'/question/detail',{     
                        params: {
                            queNo: this.queNo,
                            type: 0
                        },
                        headers : {
                            "ACCESS-TOKEN": this.$store.state.token
                        }
                    })
                    .then((response) =>{ 
                        this.replyitems = response.data.data.rpList
                        this.replycontents = ''
                        alert('댓글이 등록 되었습니다.')
                    })
                })
                .catch((error) => {
                    alert('세션 만료.\n다시 로그인 해주세요.')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            replydelete(rpNo) {
                axios.delete(this.$store.state.base_url +'/reply',{
                    params: {
                        rpNo: rpNo,
                    }, 
                    headers : {
                        'ACCESS-TOKEN' : this.$store.state.token
                    }   
                })
                .then((response) => {
                    console.log(response.data.status)
                    if (response.data.status) {
                        alert('댓글이 정상적으로 삭제 되었습니다.')
                        axios.get(this.$store.state.base_url +'/question/detail',{     
                            params: {
                                queNo: this.queNo,
                                type: 0
                            },
                            headers : {
                                "ACCESS-TOKEN": this.$store.state.token
                            }
                        })
                        .then((response) =>{ 
                            this.replyitems = response.data.data.rpList
                        })
                    }
                    else {
                        alert('댓글 작성자만 삭제가 가능합니다.')
                    }
                })
                .catch((error) => {
                    alert('세션 만료.\n다시 로그인 해주세요.')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            replylike(rpNo) {
                let config = {
                    headers: {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                }
                let body = {
                    rpNo: rpNo
                }
                axios.post(this.$store.state.base_url +'/reply/like', body, config)
                .then((response) => {
                   axios.get(this.$store.state.base_url +'/question/detail',{     
                        params: {
                            queNo: this.queNo,
                            type: 0
                        }, 
                        headers : {
                            "ACCESS-TOKEN": this.$store.state.token
                        }
                    })
                    .then((response) =>{ 
                        this.replyitems = response.data.data.rpList
                        console.log(this.replyitems)
                        }
                    )
                })
                .catch((error) => {
                    alert('세션 만료.\n다시 로그인 해주세요.')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            selectquestion() {
                let config = {
                    headers: {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                }
                let body = {
                    queNo: this.queNo
                }
                axios.post(this.$store.state.base_url +'/cart', body, config)  
                .then((response) => {
                    alert('성공적으로 찜등록 되었습니다.')
                    this.checkflag()
                })
                .catch((error) => {
                    alert('세션 만료.\n다시 로그인 해주세요.')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            checkflag() {
                axios.get(this.$store.state.base_url +'/cart',{     
                params: null, 
                headers : {
                    'ACCESS-TOKEN' : this.$store.state.token
                }   
            })
            .then((response) => {
                this.selectlist = response.data.data[this.lang]
                console.log(this.selectlist)
                var temp = true
                for (var i in this.selectlist){
                    if (this.queNo == this.selectlist[i].queNo){
                        temp = false
                        break
                    }
                }
                this.flag = temp
                })
            },
            selectdelete(queNo){
            axios.delete(this.$store.state.base_url +'/cart',{     
                params: {
                    queNo: this.queNo
                }, 
                headers : {
                    'ACCESS-TOKEN' : this.$store.state.token
                }   
            })
            .then((response) => {
                alert('찜 목록에서 삭제 되었습니다.')
                this.checkflag()
            })
            },
            level(grade){
                if (grade < 100){return 0}
                else if (grade>=100 && grade<200){return 1}
                else if (grade>=200 && grade<300){return 2}
                else if (grade>=300 && grade<400){return 3}
                else if (grade>=400 && grade<500){return 4}
                else if (grade>=500 && grade<600){return 5}
                else {return 6}
            },
            getimage(grade){
                return require('../../assets/img/lv'+this.level(grade)+'.png')
            },       
    },
    }
</script>

<style scoped>

.custom {
   width: 80rem; 
}

.inner {
    position: absolute;
    bottom: 0;
}

.media.box {
    width: 30px;
}
code {
    color: black !important;
    background-color: white !important;
}

.land {
    margin-left: 10px;
}

.bording {
    background-color:#EEEEEE;
    border-radius: 10px;
    text-align: left;
    min-height: 170px;
}

.userlanding{
    border-radius: 10px;
    border: skyblue 3px solid;
    margin-bottom: 8px;
}

.photo {
    width: 30px;
}
</style>
