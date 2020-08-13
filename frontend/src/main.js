import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import './plugins/bootstrap-vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import store from './store'
import VueCookies from 'vue-cookies'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
/* eslint-disable no-new */
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';
import { Viewer } from '@toast-ui/vue-editor';
import VueCarousel from 'vue-carousel';
import VuePrism from 'vue-prism'
import VueFullPage from 'vue-fullpage.js'

Vue.use(VueFullPage)

Vue.use(VueCookies)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VueCarousel)
Vue.use(VuePrism)

Vue.component('editor', Editor)
Vue.component('viewer',Viewer)

new Vue({
  el: '#app',
  router,
  components: { App },
  vuetify,
  store,
  template: '<App/>'
})
 