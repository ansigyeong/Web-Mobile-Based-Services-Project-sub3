<template>
  <div id="app">
    
    <v-card class="overflow-hidden">
      <v-app-bar
        absolute
        color="white"
        elevate-on-scroll
        scroll-target="#scrolling-techniques-7"
      >
        <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->
        <v-toolbar-title><router-link to="/main"><img class="logo" src="./assets/img/favicon.png"></router-link></v-toolbar-title>
        <v-spacer></v-spacer>

        <!-- <img :src="'./assets/img/' + hello_logo + 'PNG'" alt=""> -->



        <v-btn text color="deep-purple accent-4">
          <img class="icon" src="./assets/img/crab.png">
          <router-link to="/askquestion">Question</router-link>
        </v-btn>

        <v-btn text color="deep-purple accent-4">
          <img class="icon" src="./assets/img/starfish.png">
          <router-link to="/list">List</router-link>
        </v-btn>

        <v-btn text color="deep-purple accent-4">
          <img class="icon" src="./assets/img/fish.png">
          <router-link to="/bookmark">bookmark</router-link>
          <v-icon>mdi-history</v-icon>
        </v-btn>

        <v-btn text color="deep-purple accent-4">
          <router-link to="/fame">Fame</router-link>
          <v-icon>mdi-history</v-icon>
        </v-btn>

        <v-btn text color="deep-purple accent-4">
          <router-link to="/login">Login</router-link>
          <v-icon>mdi-history</v-icon>
        </v-btn>

        <v-btn text color="deep-purple accent-4">
           <router-link to="/join">Join</router-link>
          <v-icon>mdi-history</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>

        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </v-app-bar>

          <div id="nav">
          <router-link to="/askquestion">Ask Question</router-link> |
          <router-link to="/list">List</router-link> |
          <router-link to="/bookmark">bookmark</router-link> |
          <router-link to="/fame">Fame</router-link> |
          <router-link to="/login">Login</router-link> |
          <router-link to="/join">Join</router-link>
        </div>
    <router-view/>

      <v-sheet
        id="scrolling-techniques-7"
        class="overflow-y-auto"
        max-height="600"
      >
        <v-container style="height: 1500px;">

        </v-container>
      </v-sheet>
    </v-card>

  <!-- 본문 -->

  </div>
</template>


<style lang="scss">

  * {
      background-color: #cbe7f7;
  }
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

.logo {
  padding-top: 10px;
  padding-left: 20px;
  height: 70px;
  width: 80px;
}

.icon {
  height: 45px;
  width: 50px;
}
</style>
