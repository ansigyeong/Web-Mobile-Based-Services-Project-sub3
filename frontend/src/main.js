import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import vSelect from 'vue-select'

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App, vSelect },
  vuetify,
  template: '<App/>'
})
 