<template>
    <v-container>
        <v-toolbar flat>

       <v-toolbar-title><router-link to="/main"><img class="logo" src="../../assets/img/favicon.png"></router-link></v-toolbar-title>

      <v-spacer></v-spacer>

      <v-toolbar-items>
      
      
      <v-select v-model="item"
       :items="items"
       filled
       label="언어 선택"
       dense
      > </v-select>
     
      <v-text-field
        label=""
        single-line
        solo
      >
      </v-text-field>

      <v-btn text >
        <router-link to="/login">로그인</router-link>
      </v-btn>

      <v-btn c text >
        <router-link to="">로그아웃</router-link>
        </v-btn>

      <v-btn text >
        <router-link to="/join">회원가입</router-link>
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
    </v-container>
</template>   

<script> 

    export default {
        name: 'Header',
        
        components: { 

        },
        computed:{
        },
        watch: {
        },
        created() {
        },
        methods : {
        
        },
        data: () => ({
            items: ['c','cpp','java','python'],
            item : '',
            search: '',

        }),

}
</script>
<style >
  * {
      background-color: #cbe7f7;
  }

  .logo {
  padding-top: 10px;
  padding-left: 20px;
  height: 70px;
  width: 80px;
}



 
</style>
