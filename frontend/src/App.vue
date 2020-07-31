<template>
  <div id="app">
    <div id="nav">
      <router-link to="/list">List</router-link> |
      <router-link to="/fame">Fame</router-link> |
      <span v-if="this.$store.state.islogin">  
        <router-link to="/askquestion">Ask Question</router-link> |
        <router-link to="/bookmark">Bookmark</router-link> |
        <router-link to="/record">My Record</router-link> |
        <router-link to="/profile">My Profile</router-link> |
        <router-link to="/login" @click.native="logout">Logout</router-link>
      </span>
      <span v-else> 
        <router-link to="/login">Login</router-link> |
        <router-link to="/join">Join</router-link>
      </span>

    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>

<script>
export default {
  name: 'App',
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
  }
}
</script>