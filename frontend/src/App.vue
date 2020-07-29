<template>
  <div id="app">
    <div id="nav">
      <router-link to="/askquestion">Ask Question</router-link> |
      <router-link to="/list">List</router-link> |
      <router-link to="/detail">Detail</router-link> |
      <router-link to="/bookmark">bookmark</router-link> |
      <router-link to="/fame">Fame</router-link> |
      <router-link to="/record">My Record</router-link> |
      <router-link to="/profile">My Profile</router-link> |
      <router-link to="/login">Login</router-link> |
      <router-link to="/join">Join</router-link>
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
