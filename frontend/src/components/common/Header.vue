<template>
  <div>
    <v-navigation-drawer v-model="drawer" app clipped  dark temporary >
        <v-list>
      <v-list-group
        v-for="item in nav_items"
        :key="item.title"
        v-model="item.active"
        :prepend-icon="item.action"
        no-action
      >
        <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
        </template>

        <v-list-item
          v-for="subItem in item.items"
          :key="subItem.title"
          @click="none"
        >
          <v-list-item-content>
            <v-list-item-title v-text="subItem.title"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
    </v-list>
    </v-navigation-drawer>

    <v-app-bar flat="flat" height="103%" >
      
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
            drawer: false,
            nav_items: [
          {
            action: 'local_activity',
            title: 'Attractions',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'restaurant',
            title: 'Dining',
            active: true,
            items: [
              { title: 'Breakfast & brunch' },
              { title: 'New American' },
              { title: 'Sushi' },
            ],
          },
          {
            action: 'school',
            title: 'Education',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'directions_run',
            title: 'Family',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'healing',
            title: 'Health',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'content_cut',
            title: 'Office',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'local_offer',
            title: 'Promotions',
            items: [
              { title: 'List Item' },
            ],
          },
        ],
        nav_item: 1,
        })
    }
</script>
<style >

    .v-toolbar__content,.v-navigation-drawer__content{
      background-color: #e9e3b0fd;
      
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
    }
</style>