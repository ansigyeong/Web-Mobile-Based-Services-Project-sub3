<template>
  <div class="container">
    <!-- 공지사항 -->
    <!-- <s_notice_rep>
        <s_permalink_article_rep> -->
        <!-- notice view -->
        <h1 id="noticeBody" class="sr-only">[##_notice_rep_title_##] 본문</h1>
        <!-- notice header -->
        <div class="notice-header">
            <div class="title-wrap">
                <p class="category-info"><a href="/notice" style="color:#ff8149; float: left;">공지사항</a></p><br>
                <h2 class="notice-title">{{this.items.title}}</h2>
            </div>
            <div class="info-wrap">
                <ul class="notice-info">
                    <li class="author"><i class="fa fa-user-circle"></i>운영자</li>
                    <li class="date"><i class="far fa-clock"></i>{{this.items.createDate}}</li>
                </ul>
            </div>
        </div>
 
        <div class="contents" v-html="items.contents">
            {{this.items.contents}}
        </div>
        <!-- </s_permalink_article_rep>
    </s_notice_rep>   -->
        <!-- //notice view -->



    <!-- <v-row rows="3" style="border: 0.2px solid black; width:1050px">
        <v-col class="a" cols="3">제목</v-col>
        <v-col class="b" cols="9">{{this.items.title}}</v-col>
    </v-row>
    <v-row rows="3">
    <div style="text-align: left; color: black !important;border: 0.2px solid black;">
            <div v-html="items.contents" style="width:1050px; ">
            </div>
            <v-col cols="3" style="float:right">{{this.items.createDate}}</v-col>

    </div>        
    </v-row> -->
    <v-btn style="float:right;" x-large outlined text @click="back">이전</v-btn>

    <div>
        <v-btn v-show="this.info=='ROLE_ADMIN'" style="float:right;" x-large outlined text @click="deletenotice">글 삭제</v-btn>
        <v-btn v-show="this.info=='ROLE_ADMIN'" style="float:right;" x-large outlined text @click="updatenotice">글 수정</v-btn>
    </div>
  </div>
</template>

<script> 
import axios from 'axios'
    export default { 
        data () {
            return {
                info: '',
                items: null,
                title: null,
                contents: null,
                createDate: null,
                } 
        },
        created() {
            this.noticeNo = this.$route.params.noticeNo;
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
            back() {
                this.$router.push('/notice')
            },
            getdetail() {
                axios.get(this.$store.state.base_url +'/notice/detail',{     
                    params: {
                        noticeNo: this.noticeNo
                    },
                    headers : {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                })
                .then((response) => {
                    this.items = {
                        title: response.data.data.notice.title,
                        contents:response.data.data.notice.contents,
                        createDate:response.data.data.notice.createDate,
                    }
                    this.info = response.data.data.user.role;
                    this.title = response.data.data.notice.title
                    this.contents = response.data.data.notice.contents
                    this.createDate = response.data.data.notice.createDate
                })
                .catch((error) => {
                    console.log(error)
                })
            },
            deletenotice() {
                axios.delete(this.$store.state.base_url +'/notice',{     
                    params: {
                        noticeNo: this.noticeNo,
                    }, 
                    headers : {
                        'ACCESS-TOKEN' : this.$store.state.token
                    }   
                })
                .then((response) => {
                    console.log(response)
                    if (response.data.status) {
                        alert('공지가 성공적으로 삭제 되었습니다.')
                        this.$router.go(-1)
                    }
                    else {
                        alert('운영자만 삭제가 가능합니다.')
                    }
                })
          
            },
            updatenotice(noticeNo) {
                console.log('up')
                this.$store.commit("updateinfo",{
                    title : this.title,
                    contents : this.contents,
                    lang : this.lang
                })
                this.$router.push('/noticemodify/'+this.$route.params.noticeNo)
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

code {
    color: black !important;
    background-color: white !important;
}
 .a{
    border-right: 0.2px solid black;
    background-color: darkgray;
}

.notice-header {margin-bottom:10px; background:#fff; border:1px solid #ddd}
.notice-header:after {display:block; clear:both; content:''}
.notice-header .title-wrap {padding:20px 20px 10px 20px; height: 120px;}
/* .notice-header .title-wrap .category-info {color:#ff8149; float: left;} */
.notice-header .title-wrap .notice-title {margin-top:5px; font-size:2em; color:#111; line-height:1.5em; float: left;}
.notice-header .info-wrap {margin-top:10px; padding:10px 20px; background:#ff8149}
.notice-header .info-wrap:after {display:block; clear:both; content:''}
.notice-header .info-wrap .notice-info {float:left; list-style:none}
.notice-header .info-wrap .notice-info:after {display:block; clear:both; content:''}
.notice-header .info-wrap .notice-info li {margin-right:15px; float:left; font-size:0.875em; color:#fff5f0;}
.notice-header .info-wrap .notice-info li i {margin-right:4px; color:#fff}
.contents {margin-bottom:10px; background:#fff; border:1px solid #ddd; font-size: 1em; height: 600px; overflow: scroll;} 


</style>