<template>
  <v-app>
    <Header v-if="!isCheckedMain"/>
    <header-main v-if="isCheckedMain"/>
    <!-- <Navigation v-if="!isCheckedMain && !isCheckedLogin && !isCheckedJoin "/> -->
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
    console.log(this.$store.state.token)
    console.log(this.$store.state.islogin)
  },
  methods: {
    logout() {
      this.$cookies.remove('auth-token')
      this.$store.commit('checkToken',this.$cookies.get('auth-token'))
      this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
      alert('로그아웃 되었습니다.')
      this.$router.push("/list")
      console.log(this.$store.state.token)
      console.log(this.$store.state.islogin)
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

// #nav {
//   padding: 30px;

//   a {
//     font-weight: bold;
//     color: #2c3e50;

//     &.router-link-exact-active {
//       color: #42b983;
//     }
//   }
// }
  .container {
     padding-top: 100px;
     padding-left: 100px;
     padding-right: 100px;
  }
</style>

