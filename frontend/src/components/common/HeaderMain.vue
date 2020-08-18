<template>
 <div  style="height:123px; position:absolute; z-index:100; " fixed="">
        <v-navigation-drawer v-model="drawer" app clipped  light temporary >
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


    </v-list>
    
    </v-navigation-drawer> 
    <div class="main_top">
        <v-row >
        <v-col cols="2">            
        <v-app-bar-nav-icon @click="drawer = !drawer" />
        <router-link to="/">
        <img class="logo" src="../../assets/img/favicon.png"></router-link>
        </v-col>
                <!-- <div id="search" fluid style="width:500px" > -->
            <v-col cols="1">
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
                <v-col cols="3" >
                    <v-text-field v-model="keyword" label="검색어 입력"   single-line="single-line" @keyup.enter="moveList2('/list/', item+'/',keyword)"></v-text-field>
                    </v-col>
                    <div style="">
                    <v-btn icon large height=70 @click="moveList2('/list/', item+'/', keyword)"><v-icon>mdi-magnify</v-icon></v-btn>
                    </div>
                <!-- </div>  -->

                <v-col cols="3" >                
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
                    <router-link to="/login" style="color:rgb(76, 75, 71);">로그인</router-link>
                </v-btn>
                <v-btn text="text" >
                    <router-link to="/join" style="color:rgb(76, 75, 71);" >회원가입</router-link>
                </v-btn>
              </template>
                </v-col>
                  </v-row>
    </div>

 </div> 
</template>

<script>
export default {
    name: 'HeaderMain',
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
                  console.log(response);
 
              },
              fail: function(error) {
                   console.log(error);
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
              bookmark:    { path: '/bookmark', title: '찜하기', icon: 'fas fa-bookmark' },
              notice: {path: '/notice', title: '공지사항', icon: 'fas fa-bullhorn'},
              ask: {path: '/askquestion', title: '질문하기', icon:'ar fa-gem'},
              que: {  title: 'Question', icon: 'mdi-account'  , lang: [{title : 'c'} ,{title : 'cpp'},{title : 'java'},{title : 'python'},{title : 'others'} ,{title : 'all'}] },
              // myrecord : {path : '/record' , title : '내기록' , icon : 'mdi-account'},
           
        })
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

.main_top{
    width: 2000px;
}


            
</style>