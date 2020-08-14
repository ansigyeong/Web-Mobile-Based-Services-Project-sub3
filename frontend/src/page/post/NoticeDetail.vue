<template>
  <div class="container">
           
    <v-row rows="3" style="border: 0.2px solid black; width:1050px">
        <v-col class="a" cols="3">제목</v-col>
        <v-col class="b" cols="9">{{this.items.title}}</v-col>
    </v-row>
    <v-row rows="3">
    <div style="text-align: left; color: black !important;border: 0.2px solid black;">
            <div v-html="items.contents" style="width:1050px; ">
            </div>
            <v-col cols="3" style="float:right">{{this.items.createDate}}</v-col>

    </div>        
    </v-row>
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
                        swal('', '공지가 성공적으로 삭제 되었습니다.', 'success')
                        this.$router.go(-1)
                    }
                    else {
                        swal('', '운영자만 삭제가 가능합니다.', 'warning')
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

</style>