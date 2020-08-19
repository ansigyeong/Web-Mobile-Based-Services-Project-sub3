<template>
  <div class="container">
      <h1 style="margin-top:20px">📖 질문 상세보기 📖</h1>
      <div style="text-align: left">
        <div style="display:flex; justify-content:space-between; margin-top:30px">
            <h2>{{this.items.title}}</h2>
        </div>
        <div style="display:flex;">
            <h7 style="margin-top: 9px;">작성 시간: {{this.items.createDate}}</h7>
                <span v-if="$store.state.islogin"> 
                    <div>
                        <span v-if="this.flag">
                        <img  src='../../assets/img/nostar.png' class="land" width="40px"  @click="selectquestion">
                        </span>
                        <span v-else>
                        <img src='../../assets/img/star.png' class="land" width="40px" @click="selectdelete">
                        </span>
                        <span v-if="this.isme">
                        <a class="land only-for-me-msg" @click="deletequestion">글 삭제</a>
                        <a class="land only-for-me-msg" @click="updatequestion(user.email)">글 수정</a>
                        </span>
                    </div>
                </span>   
        </div>
      </div>

        <hr style="margin-top:10px">
        <b-media>
          <template v-slot:aside>
            <div class="userlanding">
                <img :src="getimage(user.grade)" width="100px" height="100px" alt="">
                <p style="margin-top:5px; margin-bottom:3px">
                    <a @click="userdetail(user.userNo)" class="nickname-in-detail">{{user.name}}</a>
                </p>
            </div>
          </template>
        <div class="bording" >
            <div style="height:10px"></div>
            <div v-html="items.contents" style="margin:20px"></div>
            <div style="height:10px"></div>
        </div>
        <div class="tags" style="margin-top:10px">
            <a class="post-tag" @click="moveTagList('/taglist/', items.firstTag)" v-if="this.items.firstTag!=''">{{this.items.firstTag}}</a>
            <a class="post-tag" @click="moveTagList('/taglist/', items.secondTag)" v-if="this.items.secondTag!=''">{{this.items.secondTag}}</a>
            <a class="post-tag" @click="moveTagList('/taglist/', items.thirdTag)" v-if="this.items.thirdTag!=''">{{this.items.thirdTag}}</a>
        </div>
        </b-media>  

    <hr>
    <h2>{{replyitems.length}}개의 답변</h2>
    <span v-for="(item,idx) in replyitems" :key="idx">
        <b-media right-align>
        <div>
            <div style="float:left;">

            <span v-if="$store.state.islogin">
                <span v-if="item.exist=='좋아요'">
                    <img src="../../assets/img/heart.png"  @click="replylike(item.rpNo,idx)" class="heart">
                </span>
                <span v-else-if="item.exist=='좋아요취소'">
                    <img src="../../assets/img/pinkht.png" @click="replylike(item.rpNo,idx)" class="heart">
                </span>
            </span>
            <span v-else>
                <img src="../../assets/img/grayheart.png" class="heart">
            </span>
                <p style="margin-top:5px">{{item.rpLike}}</p>
            </div>
            <div class="bording" style="margin-left:50px" >
                <div style="height:10px"></div>
                <div v-html="item.contents" style="margin:20px"></div>
                <div style="height:10px"></div>
            </div>
        </div>
          <template v-slot:aside>
            <div>
                <div class="userlanding">
                    <img :src="getimage(item.grade)" width="100px" height="100px" alt="">
                    <p style="margin-top:5px; margin-bottom:3px">
                        <a @click="userdetail(item.userNo)" class="nickname-in-detail">{{item.name}}</a>
                    </p>
                </div>
                <p style="margin-bottom:3px"> 
                    <span v-if="$store.state.islogin">
                        <img src="../../assets/img/delete.png" @click="replydelete(item.rpNo)" class="photo">
                        <img src="../../assets/img/update.png" @click="replyupdate(item.rpNo, item.contents, item.email)" class="photo">   
                    </span> 
                </p>    
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
                    width: 800,
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
            <b-button class="dd" @click="writereply">답변 등록</b-button>
    </b-container>
    </span>

  </div>
</template>

<script> 
import axios from 'axios'
import Editor from '@tinymce/tinymce-vue'
import { component as VueCodeHighlight } from 'vue-code-highlight'
import VuePrism from 'vue-prism'
import jwt_decode from 'jwt-decode'

    export default { 
        components: {
            'editor': Editor,
            VueCodeHighlight,
            VuePrism
        },
        data () {
            return {
                items: {'title':null, 'grade':null},
                title: null,
                contents: null,
                lang: null,
                replycontents: null,
                replyitems: [],
                flag: true,
                selectlist: null,
                user: {'grade':null},
                isme : null,
                myemail: null,
                } 
        },
        beforeCreate() {
        },
        created() {
            this.checkflag();
            this.queNo = this.$route.params.queNo;
            this.getdetail();
        },
        computed: {
            getcontents: function() 
            {
                return this.contents
            },
            tracklang: function()
            {
                return this.lang
            }
        },
        methods: {
            userdetail(userNo){
                this.$router.push('/profile/'+userNo)
            },
            getdetail() {
                let headers = null;
                let params = {
                        queNo: this.queNo,
                        type: 0
                    };
                if (this.$store.state.islogin){
                    headers  = {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                }
                axios.get(this.$store.state.base_url +'/question/detail',{     
                    params,
                    headers
                })
                .then((response) => {
                    this.items = response.data.data.question
                    this.user = response.data.data.user
                    this.replyitems = response.data.data.rpList

                    var token = this.$store.state.token
                    if (this.$store.state.islogin)
                    {
                        var decoded = jwt_decode(token) //payload
                        this.myemail = decoded.sub

                        if (decoded.sub == this.user.email){
                            this.isme = true
                        }
                        else {
                            this.isme = false
                        }
                    }
                })
                .catch((error) => {
                    swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
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
                    if (response.data.data == 'user fail') {
                        swal('', '작성자만 삭제가 가능합니다.', 'warning')
                    }
                    else {
                        swal('', '질문이 성공적으로 삭제 되었습니다.', 'success')
                        this.$router.go(-1)
                    }
                })
                .catch((error) => {
                    swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            updatequestion(email) {

                if (this.isme)
                {
                    this.$store.commit("updateinfo",{
                        title : this.items.title,
                        contents : this.items.contents,
                        lang : this.items.lang
                    })
                    this.$router.push('/updatequestion/'+this.$route.params.queNo)
                }
                else 
                {
                    alert('질문 작성자만 수정이 가능합니다.')
                }
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

                if (this.replycontents == null | this.noblank(this.replycontents) == '')
                {
                    swal('', '내용을 입력하세요.', 'warning')
                }
                else
                {
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
                            swal('', '댓글이 등록 되었습니다.', 'success')
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
                    if (response.data.data != 'user fail') {
                        swal('', '댓글이 정상적으로 삭제 되었습니다.', 'success')
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
                        swal('', '댓글 작성자만 삭제가 가능합니다.', 'warning')
                    }
                })
                .catch((error) => {
                    swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            replyupdate(rpNo, contents, email) {
                if (this.myemail == email)
                {
                    this.$store.commit("updateinfo",{
                        title : '',
                        contents : contents,
                        lang : this.$route.params.lang,
                    })
                    this.$router.push('/updatereply/'+this.$route.params.queNo+'/'+this.$route.params.lang+'/'+rpNo)
                }
                else 
                {
                    swal('', '댓글 작성자만 수정이 가능합니다.', 'warning')
                }
            },
            replylike(rpNo,idx) {
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
                        this.user.grade = response.data.data.user.grade
                        }
                    )
                })
                .catch((error) => {
                    swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
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
                    swal('', '성공적으로 찜등록 되었습니다.', 'success')
                    this.checkflag()
                })
                .catch((error) => {
                    swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            },
            checkflag() {
                if (this.$store.state.islogin){
                    axios.get(this.$store.state.base_url +'/cart',{     
                    params: null, 
                    headers : {
                        'ACCESS-TOKEN' : this.$store.state.token
                    }   
                })
                .then((response) => {
                    this.selectlist = response.data.data[this.$route.params.lang]
                    var temp = true
                    for (var i in this.selectlist){
                        if (this.queNo == this.selectlist[i].queNo){
                            temp = false
                            break
                        }
                    }
                    this.flag = temp
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
                swal('','찜 목록에서 삭제 되었습니다.', 'success')
                this.checkflag()
                })
            .catch((error) => {
                swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
                this.$cookies.remove('auth-token')
                this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                this.$router.push('/login')
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
            userdetail(userNo){
                this.$router.push('/profile/'+userNo)
            },     
            moveTagList(path, tag){
                this.$router.push(path+tag);
            },
            noblank(contents){
                return contents.replace(/(\s*)/g, "").replace("&nbsp;", "")
            },
    },
    }
</script>

<style scoped>
            @font-face {
    font-family: 'CookieRun-Regular' !important;
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}   
    .nickname-in-detail {
        font-family: 'CookieRun-Regular';
        color:rgb(105, 0, 202);
        font-size: 1.2rem;
    }

    .nickname-in-detail:hover {
        color: rgb(64, 0, 123);
    }

  * {
      font-family: 'CookieRun-Regular';
  }
            
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

.land {
    margin-left: 10px;
}

.bording {
    background-color: #e2f4ff;
    border-radius: 10px;
    text-align: left;
    min-height: 170px;
}

.userlanding{
    /* border-radius: 10px; */
    /* border: black 1px solid; */
    margin-bottom: 8px;
}

.photo {
    width: 30px;
}

.tags{
    line-height: 18px;
    float: left;
}

.post-tag{
    font-size: 15px;
    font-family: 'CookieRun-Regular';
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
    border-radius: 10px;
}

.heart{
    width: 30px;
    height: 30px;
    margin-top: 0;
    margin-left: 0px;
}


  .dd{
      margin-top: 15px;
    color: rgb(11, 13, 15);
    border: rgb(11, 13, 15);
    background-color: rgb(140, 180, 231);
  }

  .for-star {
    position: absolute;
    display: block;
    top: 21%;
    left: 37.3%;
    /* width: 25%; */
    z-index: 100;
  }

.only-for-me-msg {
    font-family: 'CookieRun-Regular';
    color: rgb(91, 91, 91);
}

.only-for-me-msg:hover {
    color:rgb(59, 59, 59)
}
</style>
