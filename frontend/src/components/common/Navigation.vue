<template>
    <div class="sidenav">
    <ul class="menu">
      <li ><router-link to="/fame" style="font-family: 'NEXON Lv2 Gothic Bold'" >명예의 전당</router-link> </li> 
      <li style="font-family: 'NEXON Lv2 Gothic Bold'" id="que">Qusetion
          <ul class="langs">
            <li v-for="item in items" :key="item"><a style="font-family: 'NEXON Lv2 Gothic Bold'" @click="moveList(item)">{{item}}</a> </li>
          </ul>
      </li>
      <template v-if="this.$store.state.islogin">
      <li><router-link to="/askquestion" style="font-family: 'NEXON Lv2 Gothic Bold'">질문</router-link> </li> 
      <li><router-link to="/notice" style="font-family: 'NEXON Lv2 Gothic Bold'">공지사항</router-link> </li>       
      <li><router-link to="/bookmark" style="font-family: 'NEXON Lv2 Gothic Bold'">찜하기</router-link> </li> 
      <li><router-link to="/record" style="font-family: 'NEXON Lv2 Gothic Bold'">내기록</router-link></li>
      </template>
    </ul>
       
    </div>
 
</template>


<script type="text/javascript" src="./js/sticky-sidebar.js"></script>
<script >
  export default {
    name: 'Navigation',
    data: () => ({
      item: '',
      items: [
        'c', 'cpp', 'java' ,'python','others','all'
      ],
      path: '/list'
    }),
    created() {
    this.$store.commit('checkToken',this.$cookies.get('auth-token'))
    this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
    console.log(this.$store.state.token)
    console.log(this.$store.state.islogin)
  },
  methods: {
    moveList(lang){
      console.log(lang)
      this.$router.push('/list/'+lang)
    }
  }

  }
</script>
<style scoped>

            @font-face {
    font-family: 'NEXON Lv2 Gothic Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

  * {
    font-family: 'NEXON Lv2 Gothic Bold';
  }
  
 .menu {
    list-style-type: none;
    margin: 0;
    padding: 0 0 0 40px;
    width: 200px;
    /* background-color: ; */
    text-align: left;
      border-right:  thin solid rgb(220, 231, 235);
      height: 100%;

}

li a {
    display: block;
    color: #000;
    padding: 8px 0px 8px 0px;
    text-decoration: none;
    font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman';

}

/* Change the link color on hover */
li a:hover {
    /* background-color: rgb(220, 231, 235); */
    color: lightgray;
}
.sidenav{
  width: 200px;
  position: fixed;
  /* border-right:  thin solid rgb(220, 231, 235); */
  /* z-index: 1; */
  /* top: 20px; */
  /* left: 10px; *
  /* background: rgb(224, 216, 216); */
  /* overflow-x: hidden; */
  padding: 140px 70px;
  height: 100%;
  /* background-color: #D0EEFF; */
  
 
}
#que {
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  color: black;
  padding: 0 30px 0 0;
  
}
.langs {
  list-style-type: none;
  padding: 0 0 0 30px;
  text-align: left;
  
}

/* .sidenav a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 25px;
  color: #3d4042;
  display: block;
  font-family: 'Alegreya';font-size: 22px;
  font-family: 'Arimo';font-size: 22px;
  font-family: 'Basic';font-size: 18px;
}

.sidenav a:hover {
  color: #6894b8;
  
  background-color: rgb(220, 231, 235);
  color: white;

} */

 
</style>