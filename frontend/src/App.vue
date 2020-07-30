<template>
  <v-app>
    <v-app-bar
      app
      flat
    >
    <Header/>
    
    </v-app-bar>
  
    <v-content>
      <v-container fluid>
    <router-view/>
      </v-container>
    </v-content>
    <v-footer app></v-footer>
  </v-app>
</template>

<script src="https://use.fontawesome.com/releases/v5.2.0/js/all.js"></script>  
<script> 
// import './assets/css/style.scss' 
import Header from './components/common/Header.vue'
import constants from './lib/constants' 

export default {
  name: 'App',
  components: { 
    Header
  },
  created() {
     
  },
  watch: {
     
  },
  methods : {
      

    
  },
  data: function () {
        return {
            isHeader: true,
            constants
        } 
    }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale; 
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>