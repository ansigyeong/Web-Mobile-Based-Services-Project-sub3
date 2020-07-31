<template>
  <div class="container">
    <v-form>
      <v-text-field v-model="lang"
        label="언어를 입력해주세요"
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-text-field v-model="title"
        label="제목을 입력해주세요"
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-divider></v-divider>
      <v-textarea
        v-model="contents"
        label="내용을 입력해주세요"
        counter
        maxlength="120"
        full-width
        single-line
      ></v-textarea>
      <v-card-actions>
        <v-btn text @click="back">Cancel</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="submit">Submit</v-btn>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'AskQuestion',
    data () {
      return {
        contents: null,
        title: null,
        lang: null
      }
    },
    methods: {
      back() {
        this.$router.push('/bookmark')
      },
      submit() {
        let config = {
          headers: {
            token: this.$store.state.token
          }
        }
        let body = {
          contents: this.contents,
          title: this.title,
          lang: this.lang,
        }
        axios.post(this.$store.state.base_url + '/question/write', body, config)
        .then((response) => {
          alert('글이 성공적으로 작성 되었습니다.')
          this.$router.push('/list')
        })
        .catch((error) => {
          console.log(error)
        })
      }
    },
    computed: {
      progress () {
        return Math.min(100, this.value.length * 10)
      },
      color () {
        return ['error', 'warning', 'success'][Math.floor(this.progress / 40)]
      },
    },
  }
</script>