<template>
  <div>
    <v-navigation-drawer v-model="drawer" app clipped  dark temporary >
      <v-list>
      <v-list-tile v-for="item in items" :key="item.title" >
        <v-list-tile-action>
          <v-icon>{{ item }}</v-icon>
        </v-list-tile-action>
        <v-list-tile-content>
          <v-list-tile-title>{{ item }}</v-list-tile-title>
        </v-list-tile-content>
      </v-list-tile>
    </v-list>
    </v-navigation-drawer>
    <v-app-bar flat="flat" height="103%">

        <v-app-bar-nav-icon @click="drawer = !drawer" />
      
            <router-link to="/main">
                <img class="logo" src="../../assets/img/favicon.png"></router-link>
    
            <v-spacer/>

                <v-select
                    v-model="item"
                    :items="items"
                    filled="filled"
                    label="언어 선택"
                    dense="dense"></v-select>

                <v-text-field label="" single-line="single-line" solo="solo"></v-text-field>

                <v-btn text="text">
                    <router-link to="/login">로그인</router-link>
                </v-btn>

                <v-btn c="c" text="text">
                    <router-link to="">로그아웃</router-link>
                </v-btn>

                <v-btn text="text">
                    <router-link to="/join">회원가입</router-link>
                </v-btn>
    
        </v-app-bar>
  </div>
</template>

<script>

    export default {
        name: 'Header',

        components: {},
        computed: {},
        watch: {},
        created() {},
        methods: {},
        data: () => ({
            items: [
                'c', 'cpp', 'java', 'python'
            ],
            item: '',
            search: '',
            drawer: false

        })
    }
</script>
<style >
    .v-toolbar__content,.v-navigation-drawer__content{
      background-color: #F0F8FF;
    }
  .v-list-tile{
    color:red;
  }
    .logo {
        /* padding-top: 10px; */
        padding-left: 20px;
        height: 70px;
        width: 80px;
    }
</style>