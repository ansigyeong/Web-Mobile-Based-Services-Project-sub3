<template>
  <div class="sidena">
    <ul class="menu">
      <li ><router-link to="/fame" style="font-family: 'NEXON Lv2 Gothic Bold'" >명예의 전당</router-link> </li> 
      <li style="font-family: 'NEXON Lv2 Gothic Bold'" id="que">질문목록
          <ul class="langs">
            <li v-for="item in items" :key="item"><a style="font-family: 'NEXON Lv2 Gothic Bold'" @click="moveList('/list/', item,'')">{{item}}</a> </li>
          </ul>
      </li>
      <template v-if="this.$store.state.islogin">
      <li><router-link to="/askquestion" style="font-family: 'NEXON Lv2 Gothic Bold'">질문하기</router-link> </li> 
      <li><router-link to="/notice" style="font-family: 'NEXON Lv2 Gothic Bold'">공지사항</router-link> </li>       
      <li><router-link to="/bookmark" style="font-family: 'NEXON Lv2 Gothic Bold'">찜목록</router-link> </li> 
      <li><router-link to="/record" style="font-family: 'NEXON Lv2 Gothic Bold'">내기록</router-link></li>
      </template>
      <li><router-link to="/quiz" style="font-family: 'NEXON Lv2 Gothic Bold'">오늘의 퀴즈</router-link></li>

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
  },
  methods: {
    moveList(path, lang, keyword){
            this.$router.push(path+lang+keyword); 
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
    width: 150px;
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
.sidena{
  width: 200px;
  position: fixed;
  padding: 140px 10px ;
  height: 100%;
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
.lang:hover{
  color: lightgray;
}

 
</style>