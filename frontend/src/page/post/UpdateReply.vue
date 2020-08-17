<template>
  <div class="container">
      <div style="text-align: left">
        <div style="display:flex; justify-content:space-between">
            <h2>{{this.items.title}}</h2>
        </div>
        <div style="display:flex; justify-content:space-between">
            <h7>작성 시간: {{this.items.createDate}}</h7>
            <div style="width=175px; height=40px"></div>
        </div>
      </div>
        <hr style="margin-top:10px">
        <b-media>
          <template v-slot:aside>
            <div class="userlanding">
                <img :src="getimage(user.grade)" width="100px" height="100px" alt="">
                <p style="margin-top:5px; margin-bottom:3px">
                    <a @click="userdetail(user.userNo)" style="color: blueviolet">{{user.name}}</a>
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
    <span v-if="this.$store.state.islogin">  
    <b-container fluid>
            <div style="text-align:center">
            <h2>댓글 수정</h2>
            <div style="text-align:center">
                <editor api-key="vem3wnp12tvfllgyuf92uzd6e04f9ddz4ke9mzv8uh71ctgq" :init="{
                    height: 300,
                    width: 750,
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
            <b-button @click="update">답변 등록</b-button>
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
import jwt_decode from 'jwt-decode'

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
                replycontents: this.$store.state.updatebox.contents,
                replyitems: null,
                flag: true,
                selectlist: null,
                user: null,
                isme : null
                } 
        },
        created() {
            this.queNo = this.$route.params.queNo;
            this.getdetail();
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

                    this.items = response.data.data.question
                    this.user = response.data.data.user
                    this.replyitems = response.data.data.rpList
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
                console.log(this.tag)
                this.$router.push(path+tag);
            },
            update() {
                let config = {
                    headers: {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                }
                let body = {
                    queNo: this.$route.params.queNo,
                    rpNo: this.$route.params.rpNo,
                    contents: this.replycontents,
                }
                axios.put(this.$store.state.base_url +'/reply', body, config)
                .then((response) => {
                    console.log(response)
                    if (response.data.data == 'modifyReply success'){
                    swal('', '성공적으로 질문이 수정 되었습니다.', 'success')
                    }
                    else {
                    swal('', '질문 작성자만 수정이 가능 합니다.', 'warning')
                    }
                    this.$router.push('/detail/'+this.$route.params.queNo+'/'+this.$store.state.updatebox.lang)
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
    border-radius: 10px;
}

.heart{
    width: 40px;
    height: 40px;
    margin-top: 55px;
}
</style>
