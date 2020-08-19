<template>
  <v-app>
    <Header v-if="!isCheckedMain"/>
    <header-main v-if="isCheckedMain"/>
    <Navigation class="side" v-if="!isCheckedMain && !isCheckedLogin && !isCheckedJoin "/>
    <div>
      <router-view/>
    </div>
  </v-app>
</template>
<script>
import Header from './components/common/Header.vue'
import Navigation from './components/common/Navigation.vue'
import HeaderMain from './components/common/HeaderMain.vue'
export default {
  name: 'App',
  components:{
    Header,
    Navigation,
    HeaderMain
  },
  data() {
    return {

    }
  },
  created() {
    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
  },
  methods: {
    logout() {
      this.$cookies.remove('auth-token')
      this.$store.commit('checkToken',this.$cookies.get('auth-token'))
      this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
      swal('', '로그아웃 되었습니다.', 'success')
      this.$router.push("/list")
    }
  },
  computed: {
      isCheckedMain(){
      return this.$route.name == 'Main' 
      },
      isCheckedLogin(){
        return this.$route.name == 'Login'
      },
      isCheckedJoin(){
        return this.$route.name == 'Join'
      }
  }, 
}
</script>
<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

  .container {
     padding-top: 100px;
     padding-left: 170px;
     padding-right: 170px;
  }

  .v-application code {
    all: unset;
    color: black;
    background: none;
    text-shadow: 0 1px white;
    font-family: Consolas, Monaco, 'Andale Mono', 'Ubuntu Mono', monospace;
    font-size: 1em;
    text-align: left;
    white-space: pre;
    word-spacing: normal;
    word-break: normal;
    word-wrap: normal;
    line-height: 1.5;
    -moz-tab-size: 4;
    tab-size: 4;
    -webkit-hyphens: none;
    -ms-hyphens: none;
    hyphens: none;
}

pre {
    background-color: #f0f0f0;
    border: 1px solid rgb(138, 138, 138);
    border-radius: 3%;
    padding: 16px;

}

@media screen and (max-width: 500px){
  .side{
    display: none;
  }
  .container {
     padding-top: 100px;
  }
}
</style>

