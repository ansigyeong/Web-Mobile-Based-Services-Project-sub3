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