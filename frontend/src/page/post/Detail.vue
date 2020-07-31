<template>
  <div>
    <b-table stacked :items="items" class = "custom"></b-table>
    <span v-if="this.$store.state.islogin"> 
    <div>
        <b-button @click="deletequestion">글 삭제</b-button>
    </div>
    </span>
  </div>
</template>

<script> 
import axios from 'axios'
    export default { 
        data () {
            return {
                items: null,
            } 
        },
        created() {
            this.queNo = this.$store.state.queNo
            console.log(this.queNo)
            this.getdetail()
            console.log('hi')
            console.log(this.title)
            console.log(this.items)
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
                    headers:{
                        token : this.$store.state.token
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
            updatequestion() {

            }

        }
    }
</script>

<style scoped>

.custom {
   width: 80rem; 
}
</style>