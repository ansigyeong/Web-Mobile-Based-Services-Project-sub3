<template>
  <div class="container">
    <h1>게시글 수정</h1>
    <v-form>
      <v-text-field v-model="lang"
        placeholder= this.lang
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-text-field v-model="title"
        placeholder= this.title
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-divider></v-divider>
      <v-textarea
        v-model="contents"
        placeholder= this.contents
        counter
        maxlength="120"
        full-width
        single-line
      ></v-textarea>
      <v-card-actions>
        <v-btn text @click="back">Cancel</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="update">Submit</v-btn>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>
    import axios from 'axios'
    export default {
        data () {
        return {
            contents: this.$store.state.updatebox.contents,
            title: this.$store.state.updatebox.title,
            lang: this.$store.state.updatebox.lang,
            }
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
                      alert('성공적으로 질문이 수정 되었습니다.')
                    }
                    else {
                      alert('질문 작성자만 수정이 가능 합니다.')
                    }
                    this.$router.push('/detail/'+this.$route.params.queNo)
                  })
                .catch((error) => {
                    alert('세션 만료.\n다시 로그인 해주세요.')
                    this.$cookies.remove('auth-token')
                    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                    this.$router.push('/login')
                })
            }
        }
}
</script>

