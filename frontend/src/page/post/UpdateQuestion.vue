<template>
  <div class="container">
    <h1>게시글 수정</h1>
    <v-form>
      <v-text-field v-model="title"
        label="제목을 입력해주세요"
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-divider></v-divider>
    <div>
      <editor api-key="vem3wnp12tvfllgyuf92uzd6e04f9ddz4ke9mzv8uh71ctgq" :init="{
          height: 500,
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
        }" v-model="contents" />
    </div>
      <v-card-actions style="float:right">
        <v-btn text @click="back">Cancel</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="update">Submit</v-btn>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>
import axios from 'axios'
import Editor from '@tinymce/tinymce-vue'
export default {
  components: {
      'editor': Editor
  },
  data () {
  return {
      contents: this.$store.state.updatebox.contents,
      title: this.$store.state.updatebox.title,
      lang: this.$store.state.updatebox.lang,
      }
  },
  created(){
    console.log(this.$store.state.updatebox)
  },
  methods: {
    update() {
        let config = {
            headers: {
                "ACCESS-TOKEN": this.$store.state.token
            }
        }
        let body = {
            queNo: this.$route.params.queNo,
            contents: this.contents,
            title: this.title,
            lang: this.lang,
        }
        axios.put(this.$store.state.base_url +'/question', body, config)
        .then((response) => {
            if (response.data.status){
              swal('', '성공적으로 질문이 수정 되었습니다.', 'success')
            }
            else {
              swal('', '질문 작성자만 수정이 가능 합니다.', 'warning')
            }
            this.$router.push('/detail/'+this.$route.params.queNo)
          })
        .catch((error) => {
            swal('', '세션 만료.\n다시 로그인 해주세요.', 'warning')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
    }
  }
}
</script>

<style scoped>
            @font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

  * {
      font-family: 'CookieRun-Regular';
  }
            
</style>

