<template>
    <div style="margin-top : 100px">
        <editor v-model="editorText"/>
        {{editorText}}
    </div>

</template>
<script>
import 'tui-editor/dist/tui-editor.css'
import 'tui-editor/dist/tui-editor-contents.css'
import 'codemirror/lib/codemirror.css'
import { Editor, Viewer } from '@toast-ui/vue-editor'
export default {
  components: {
    'editor': Editor,
    'viewer': Viewer
  },
  data () {
    return {
      editorText: ''
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