import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
// import Header from '../components/common/Header.vue'
import Join from '../page/user/Join.vue'
import Login from '../page/user/Login.vue'
import List from '../page/post/List.vue'
import Fame from '../page/post/Fame.vue'
import Bookmark from '../page/post/Bookmark.vue'
import AskQuestion from '../page/post/AskQuestion.vue'

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
    path: '/list',
    name: 'List',
    component: List
  },
  {
    path: '/fame',
    name: 'Fame',
    component: Fame
  },
  {
    path: '/bookmark',
    name: 'Bookmark',
    component: Bookmark
  },
  {
    path: '/askquestion',
    name: 'AskQuestion',
    component: AskQuestion
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
