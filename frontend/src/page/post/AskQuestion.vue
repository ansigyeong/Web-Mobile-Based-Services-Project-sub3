<template>
  <div class="container">
    <h1>질문하기</h1>
    <v-form>
      <v-text-field v-model="title"
        label="제목을 입력해주세요"
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-divider></v-divider>
      <editor
      ref ="editor"
      :options="editorOptions"
      height="500px"
      initialEditType="wysiwyg"
      previewStyle="vertical"
      />
      <b-form-select v-model="lang" :options="options"></b-form-select>
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
        lang: null,
        options: [
        { value: null, text: '언어를 입력해 주세요' },
        { value: 'c', text: 'C' },
        { value: 'cpp', text: 'C++' },
        { value: 'java', text: 'Java' },
        { value: 'python', text: 'Python' }],
        editorText: 'This is initialValue.',
        editorOptions: {
          hideModeSwitch: true
        }
      }
    },
    methods: {
      back() {
        this.$router.push('/bookmark')
      },
      submit() {
        let config = {
          headers: {
            "ACCESS-TOKEN": this.$store.state.token
          }
        }
        let body = {
          contents: this.$refs.editor.invoke("getMarkdown"),
          title: this.title,
          lang: this.lang,
        }
        axios.post(this.$store.state.base_url + '/question', body, config)
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