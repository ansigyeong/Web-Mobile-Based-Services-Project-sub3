import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  // data의 집합(중앙 관리할 모든 데이터===상태)
  state: {
    base_url: 'http://3.34.42.229:8080',
    token: null,
    islogin: false,
    updatebox: null
  },
  // state 를 (가공해서)가져올 함수들. ===computed  
  getters: {},
  // state를 변경하는 함수들
  // mutation에 작성되지 않은 state 변경 코드는 모두 동작하지 않음
  // 모든 mutation 함수들은 동기적으로 동작하는 코드
  // commit 함수 통해 실행함.
  // this.$store.commit('')
  mutations: {
    checkToken: function(state, token) {
      state.token = token;
    },
    checklogin: function(state, flag){
      state.islogin = flag;
    },
    updateinfo: function(state, box){
      state.updatebox = box
    }
  },
  // 범용적인 함수들. mutations에 정의한 함수를 actions에서 실행 가능.
  // 비동기 로직은 actions에서 정의.
  // dispatch 를 통해 실행함.
  actions: {
  },
  modules: {
  }
})
