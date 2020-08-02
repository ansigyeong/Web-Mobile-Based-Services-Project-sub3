import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
// import Header from '../components/common/Header.vue'
import Join from '../page/user/Join.vue'
import Login from '../page/user/Login.vue'
import Profile from '../page/user/Profile.vue'
import Record from '../page/user/Record.vue'
import Fame from '../page/post/Fame.vue'
import AskQuestion from '../page/post/AskQuestion.vue'
import Detail from '../page/post/Detail.vue'
import List from '../page/post/List.vue'
import Bookmark from '../page/post/Bookmark.vue'
import UpdateQuestion from '../page/post/UpdateQuestion.vue'
// import { component } from 'vue/types/umd'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/join',
    name: 'Join',
    component: Join
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile
  },
  {
    path: '/record',
    name: 'Record',
    component: Record
  },
  {
    path: '/fame',
    name: 'Fame',
    component: Fame
  },
  {
    path: '/askquestion',
    name: 'AskQuestion',
    component: AskQuestion
  },
  {
    path: '/detail/:queNo',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/list',
    name: 'List',
    component: List
  },
  {
    path: '/bookmark',
    name: 'Bookmark',
    component: Bookmark
  },
  {
    path: '/updatequestion/:queNo',
    name: 'UpdateQuestion',
    component: UpdateQuestion
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
