<template>
  <div>
    
    <v-navigation-drawer v-model="drawer" app clipped  light temporary>
     <v-list  flat  class="primary--text" >
             <v-subheader>Menu</v-subheader>   
        <v-list-item 
          @click="movePage(fame.path)" 
        >
        <v-list-item-icon><v-icon v-text="fame.icon"></v-icon></v-list-item-icon>
        <v-list-item-title v-text="fame.title"></v-list-item-title>
        </v-list-item>

        <template v-if="this.$store.state.islogin"> 
          <v-list-item 
          @click="movePage(bookmark.path)"
        >
        <v-list-item-icon><v-icon id="bookmarkIcon" v-text="bookmark.icon"></v-icon></v-list-item-icon>
        <v-list-item-title v-text="bookmark.title"></v-list-item-title>
        </v-list-item>

        <v-list-item 
          @click="movePage(notice.path)"
        >
        <v-list-item-icon><v-icon v-text="notice.icon"></v-icon></v-list-item-icon>
        <v-list-item-title v-text="notice.title"></v-list-item-title>
        </v-list-item>

          <v-list-item 
          @click="movePage(ask.path)"
        >
        <v-list-item-icon><v-icon v-text="ask.icon"></v-icon></v-list-item-icon>
        <v-list-item-title v-text="ask.title"></v-list-item-title>
        </v-list-item>

        </template>
    
          <v-list-group 
            no-action prepend-icon="fas fa-question-circle"  red--text>
          
          <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title v-text="que.title"></v-list-item-title>
              </v-list-item-content>
            </template>
            
            <v-list-item v-for="subitem in que.lang" :key="subitem.title"  @click="moveList('/list/', subitem.title,'')">
                <v-list-item-title v-text="subitem.title"></v-list-item-title>
                <v-list-item-icon><v-icon ></v-icon></v-list-item-icon>
            </v-list-item>


          </v-list-group>

        <v-list-item 
          @click="movePage(quiz.path)"
        >
        <v-list-item-icon><v-icon v-text="quiz.icon"></v-icon></v-list-item-icon>
        <v-list-item-title v-text="quiz.title"></v-list-item-title>
        </v-list-item>

    </v-list>
    
    </v-navigation-drawer>

    <v-app-bar flat="flat" height="103%" class="headerbar" fixed="">
      
        <v-app-bar-nav-icon @click="drawer = !drawer"  v-if="isChecked" />
      
            <router-link to="/">
                <img class="logo" src="../../assets/img/favicon.png"></router-link>
            <v-spacer/>
               
                <div id="search" fluid style=" padding-top: 30px;" >
                  <v-row >
                    <v-col cols="3" sm="3">
                      <v-select
                          v-model="item"
                          :items="items"
                          filled="filled"
                          label="언어 선택"
                          dense="dense"
                          style="width: 300px;"
                          >
                      </v-select>
                    </v-col>
                    <v-col cols="7">
                      <v-text-field v-model="keyword" label="검색어 입력" clear-icon light hide-details flat height=50 single-line="single-line" @keyup.enter="moveList2('/list/', item+'/',keyword)"></v-text-field>
                    </v-col>
                    <div style="padding-top:10px">
                    <v-btn icon large height=60 width=60 @click="moveList2('/list/', item+'/', keyword)"><v-icon>mdi-magnify</v-icon></v-btn>
                    </div>
                  </v-row>
                </div>

              <template v-if="this.$store.state.islogin"> 
                <v-btn text="text" >
                    <router-link to="/profile/0" style="color:rgb(76, 75, 71);">내 정보</router-link>
                </v-btn>
                <v-btn  text="text" >
                    <router-link to="/login"  @click.native="logout" style="color:rgb(76, 75, 71);">로그아웃</router-link>
                </v-btn>
              </template>
              <template v-else>
                <v-btn text="text" >
                    <router-link to="/login" style="color:rgb(76, 75, 71); font-family: 'NEXON Lv2 Gothic Bold';" >로그인</router-link>
                </v-btn>
                <v-btn text="text" >
                    <router-link to="/join" style="color:rgb(76, 75, 71);" >회원가입</router-link>
                </v-btn>
              </template>
    
        </v-app-bar>
  </div>
</template>

<script>

    export default {
        name: 'Header',

        components: {},
        computed: {
            isChecked(){
            return this.$route.name == 'Main'
          }          
        },
        watch: {},
        created() {
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))      
        },
        methods: {
          movePage(path){
            this.$router.push(path)
          },
          moveList(path, lang, keyword){
            this.$router.push(path+lang+keyword); 
          },
          moveList2(path, lang, keyword){
            if(this.item==''){
              swal('', '언어를 선택하세요', 'warning')
            } else if(this.keyword==null || this.keyword== ''){
              swal('', '검색어를 입력하세요', 'warning')
            } else this.$router.push(path+lang+keyword); 
          },
          logout() {
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            
            Kakao.API.request({
              url: '/v1/user/unlink',
              success: function(response) {
 
              },
              fail: function(error) {
              },
            });
            
            swal('', '로그아웃 되었습니다.', 'success')
            this.$router.push("/")
          }
        },
        data: () => ({
            items: [
                'c', 'cpp', 'java', 'python', 'others', 'all', 
            ],
            item: '',
            search: '',
            drawer: false,
            keyword: null,
              fame:   { path: '/fame', title: '명예의 전당', icon: 'fas fa-trophy' },
              bookmark:    { path: '/bookmark', title: '찜목록', icon: 'far fa-bookmark' },
              notice: {path: '/notice', title: '공지사항', icon: 'fas fa-bullhorn'},
              ask: {path: '/askquestion', title: '질문하기', icon:'ar fa-gem'},
              que: {  title: 'Question', icon: 'mdi-account'  , lang: [{title : 'c'} ,{title : 'cpp'},{title : 'java'},{title : 'python'},{title : 'others'} ,{title : 'all'}] },
              quiz : {path : '/quiz' , title : '오늘의 퀴즈' , icon : 'far fa-bookmark'},
        })
    }
</script>
<style >
            @font-face {
    font-family: 'NEXON Lv2 Gothic Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
            

    .v-toolbar__content,.v-navigation-drawer__content{
      /* background-color: #D0EEFF; */
      /* opacity: 0.5; */
      
    }
    .v-navigation-drawer{
      color: black;
      font-family: 'NEXON Lv2 Gothic Bold';
    }
    .headerbar {
      position: sticky;
      font-family: 'NEXON Lv2 Gothic Bold';
    }
  
/* 

  .theme--dark.v-list-item:not(.v-list-item--active):not(.v-list-item--disabled){
    color : black;
  } */
  .logo {
        /* padding-top: 10px; */
        padding-left: 20px;
        height: 70px;
        width: 80px;
        font-family: 'NEXON Lv2 Gothic Bold';
    }
  .list-nav {
      color : black;
      font-family: 'NEXON Lv2 Gothic Bold';
  }

  a:hover{
    text-decoration: none;
    text-decoration-color: rgb(179, 175, 164);
    color:rgb(76, 75, 71);
    font-family: 'NEXON Lv2 Gothic Bold';
  }

  

</style>
