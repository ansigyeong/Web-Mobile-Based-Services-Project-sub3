<template>
  <div class="container">
    <b-table stacked :items="items" class = "custom"></b-table>
    <span v-if="this.$store.state.islogin"> 
    <div>
        <b-button @click="deletequestion">글 삭제</b-button>
        <b-button @click="updatequestion">글 수정</b-button>
        <span v-if="this.flag">
        <b-button @click="selectquestion">찜 하기</b-button>
        </span>
        <span v-else>
        <b-button @click="selectdelete">찜 삭제</b-button>
        </span>
    </div>
    </span>
    <h2>댓글 목록</h2>
    <span v-if="this.$store.state.islogin">
    <b-table :items="replyitems" :fields="fields" striped responsive="sm">
        <slot></slot>
            <template v-slot:cell(rpdelete)="row">
            <b-button size="sm" @click="replydelete(row.item.rpNo)" class="mr-1">
            댓글 삭제
            </b-button>
            </template>
            <template v-slot:cell(rpupdate)="row">
            <b-button size="sm" @click="replyupdate(row.item.rpNo)" class="mr-1">
            댓글 수정
            </b-button>
            </template>
            <template v-slot:cell(replylike)="row">
            <b-button size="sm" @click="replylike(row.item.rpNo)" class="mr-1">
            좋아요
            </b-button>
            </template>
    </b-table>
    </span>
    <span v-else>
        <b-table :items="replyitems" :fields="fields" striped responsive="sm"></b-table>
    </span>
    <span v-if="this.$store.state.islogin">  
    <h2>댓글 작성</h2>
    <b-container fluid>
        <b-row class="my-1">
            <b-col sm="10">
            <b-form-input id="input-default" placeholder="댓글을 입력하세요" v-model="replycontents"></b-form-input>
            </b-col>
        </b-row>
        <b-row>
            <b-button @click="writereply">댓글 등록</b-button>
        </b-row>
    </b-container>
    </span>
  </div>
</template>

<script> 
import axios from 'axios'
    export default { 
        data () {
            return {
                items: null,
                title: null,
                contents: null,
                lang: null,
                replycontents: null,
                replyitems: null,
                fields: [{key:'rpLike', label:'좋아요'},
                    {key:'name', label: '작성자'},
                    {key:'contents', label: '내용'},
                    {key: 'createDate', label: '작성 시간'},
                    {key: 'rpdelete', label: '댓글 삭제'},
                    {key: 'rpupdate', label: '댓글 수정'},
                    {key: 'replylike', label: '좋아요'}],
                flag: true,
                selectlist: null
                } 
        },
        created() {
            console.log(this.$store.state.token)
            this.queNo = this.$route.params.queNo
            console.log(this.queNo)
            this.getdetail()
            this.checkflag()
        },
        methods: {
            getdetail() {
                axios.get(this.$store.state.base_url +'/question/detailQuestion',{     
                    params: {
                        queNo: this.queNo,
                    }
                })
                .then((response) => {
                    console.log(response)
                    console.log(response.data.data.question.contents)
                    this.items = [{
                    '제목':response.data.data.question.title,
                    '내용':response.data.data.question.contents,
                    '질문자':response.data.data.user.name,
                    '작성시간':response.data.data.question.createDate,
                    '언어':response.data.data.question.lang
                    }]
                    this.title = response.data.data.question.title
                    this.contents = response.data.data.question.contents
                    this.lang = response.data.data.question.lang
                    this.replyitems = response.data.data.rpList
                })
                .catch((error) => {
                    console.log(error)
                })
            },
            deletequestion() {
                axios.get(this.$store.state.base_url +'/question/deleteQuestion',{     
                    params: {
                        queNo: this.queNo,
                    }, 
                    headers : {
                        'ACCESS-TOKEN' : this.$store.state.token
                    }   
                })
                .then((response) => {
                    console.log(response.data.status)
                    if (response.data.status) {
                        alert('질문이 성공적으로 삭제 되었습니다.')
                        this.$router.push("/list")
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
                axios.post(this.$store.state.base_url +'/reply/write', body, config)
                .then((response) => {
                    axios.get(this.$store.state.base_url +'/question/detailQuestion',{     
                        params: {
                            queNo: this.queNo,
                        }
                    })
                    .then((response) =>{ 
                        this.replyitems = response.data.data.rpList
                        this.replycontents = null
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
                axios.get(this.$store.state.base_url +'/reply/deleteReply',{
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
                        axios.get(this.$store.state.base_url +'/question/detailQuestion',{     
                            params: {
                                queNo: this.queNo,
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
                    console.log(response)
                })
                .catch((error) => {
                    console.log(error)
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
                axios.post(this.$store.state.base_url +'/cart/regist', body, config)  
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
                axios.get(this.$store.state.base_url +'/cart/list',{     
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
            axios.get(this.$store.state.base_url +'/cart/delete',{     
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
            }
    }
    }
</script>

<style scoped>

.custom {
   width: 80rem; 
}
</style>