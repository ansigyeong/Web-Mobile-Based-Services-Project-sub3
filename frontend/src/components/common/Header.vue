<template>
    <div id="header" v-if="isHeader">
        <v-app-bar
        absolute
        color="white"
        elevate-on-scroll
        scroll-target="#scrolling-techniques-7"
        >

        <v-toolbar-title><router-link to="/main"><img class="logo" src="../../assets/img/favicon.png"></router-link></v-toolbar-title>
        <v-spacer></v-spacer>
                <!-- <v-select
                    :items="items"
                    label="언어 선택"
                ></v-select> -->
                <select class="langSelect" v-model="lang">
                            <option disabled value="">언어</option>
                            <option>c</option>
                            <option>cpp</option>
                            <option>java</option>
                            <option>python</option>
                </select>

        <div class="right">
            <div class="search-input">
                <i class="fas fa-search"></i>
                <input v-model="search" type="text"/>
                <button @click="moveList">search</button>
            </div> 
            
            <router-link to="/login" class="login-btn">
                로그인 
            </router-link>  
            <router-link to="/join" class="join-btn">
                회원가입
            </router-link>
        </div>  
        </v-app-bar>
        
    </div>
</template>   

<script> 

    export default {
        name: 'Header',
        
        components: { 

        },
        props: ['isHeader'],
        computed:{
        },
        watch: {
        },
        created() {
        },
        methods : {
          moveList(){
            $router.push({path: '/list',
              query:{lang: this.lang , search: this.search}
            });
          }
        },
        data: function() {
           return {
               constants,
               search : '',
               lang:'',

           }
        },

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

.icon {
  height: 45px;
  width: 50px;
}

.langSelect{
  -webkit-appearance: listbox;
  border-style: groove;
}
</style>
