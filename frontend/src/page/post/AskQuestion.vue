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
      
     <div>
        <v-row rows="1">
          <v-col cols="4" >
           <b-form-select v-model="lang" :options="options" ></b-form-select>
          </v-col>
          <v-col cols="8" >
            <v-combobox
              v-model="tags"
              :items="tagList"
              item-text="name"
              label="태그 입력"
              multiple
              hint="최대 3개의 태그를 입력하세요"
              chips
              class="p-0 m-0"
            ></v-combobox>
          </v-col>
        </v-row>
      </div>
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
        }" v-model="contents"  />
    </div>
      <v-card-actions style="float:right">
        <v-btn text @click="back">Cancel</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="submit">Submit</v-btn>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>
import axios from 'axios'
import Editor from '@tinymce/tinymce-vue'
  export default {
    name: 'AskQuestion',
    components: {
        'editor': Editor
    },
    data () {
      return {
        contents: '',
        title: null,
        lang: null,
        options: [
        { value: null, text: '언어를 입력해 주세요' },
        { value: 'c', text: 'C' },
        { value: 'cpp', text: 'C++' },
        { value: 'java', text: 'Java' },
        { value: 'python', text: 'Python' },
        { value: 'etc', text: 'Etc'}],
        editorText: 'This is initialValue.',
        editorOptions: {
          hideModeSwitch: true
        },
        tags: [],
        tagList: []
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
        let i = 0
        let fT = '~'
        let sT = '~'
        let tT = '~'
        
        for(let tag of this.tags){
          console.log(tag)
          if(i==0)  fT = tag
          if(i==1)  sT = tag
          if(i==2)  tT = tag
          i++
        }
        let body = {}
        if(fT=='~'){
          body = {
            contents: this.contents,
            title: this.title,
            lang: this.lang,
          }
        } else if(sT=='~'){
          body = {
            contents: this.contents,
            title: this.title,
            lang: this.lang,
            firstTag : fT
          }
        } else if(tT=='~'){
          body = {
            contents: this.contents,
            title: this.title,
            lang: this.lang,
            firstTag : fT,
            secondTag : sT
          }
        } else {
          body = {
            contents: this.contents,
            title: this.title,
            lang: this.lang,
            firstTag : fT,
            secondTag : sT,
            thirdTag : tT
          }
        }
        axios.post(this.$store.state.base_url + '/question', body, config)
        .then((response) => {
          swal('', '글이 성공적으로 작성 되었습니다.', 'success')
          this.$router.push('/list/'+this.lang)
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
    computed: {
      progress () {
        return Math.min(100, this.value.length * 10)
      },
      color () {
        return ['error', 'warning', 'success'][Math.floor(this.progress / 40)]
      },
    },
    watch: {
      tags (val) {
        if (val.length > 3) {
          this.$nextTick(() => this.tags.pop())
        }
      },
    },
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