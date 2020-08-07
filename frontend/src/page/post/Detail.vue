<template>
  <div class="container">
    <b-table stacked :items="items" :fields="userfields" class = "custom">
        <template v-slot:cell(contentsactions)="row">
        <viewer :initialValue = "row.item.contents" />
        </template>
        <template v-slot:cell(useractions)="row">
        <a size="sm" @click="userdetail(row.item.userNo)" class="mr-1">
          {{row.item.name}}
        </a>
      </template>
    </b-table>
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
    <viewer :initialValue = "contents" />
    <h2>댓글 목록</h2>
    <span v-if="this.$store.state.islogin">
    <b-table :items="replyitems" :fields="fields" striped responsive="sm">
        <slot></slot>
            <template v-slot:cell(replyactions)="row">
            <a size="sm" @click="userdetail(row.item.userNo)" class="mr-1">
            {{row.item.name}}
            </a>
            </template>
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
            {{row.item.exist}}
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
                    console.log(response.data.data.question.contents)
                    this.items = [{
                    title: response.data.data.question.title,
                    contents:response.data.data.question.contents,
                    createDate:response.data.data.question.createDate,
                    name:response.data.data.user.name,
                    lang:response.data.data.question.lang,
                    userNo: response.data.data.user.userNo
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
            }
        
    },
    }
</script>

<style scoped>

.custom {
   width: 80rem; 
}
</style>