import Vue from 'vue'
import VueRouter from 'vue-router'
import AskQuestion from '../page/post/AskQuestion.vue'
import Detail from '../page/post/Detail.vue'
import List from '../page/post/List.vue'
import Login from '../page/user/Login.vue'
import Join from '../page/user/Join.vue'
import CommentCreate from '../components/comment/CommentCreate.vue'
import CommentList from '../components/comment/CommentList.vue'
import CommentListItem from '../components/comment/CommentListItem.vue'
// import { component } from 'vue/types/umd'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/askquestion',
    name: 'AskQuestion',
    component: AskQuestion
  },
  {
    path: '/detail',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/list',
    name: 'List',
    component: List
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/join',
    name: 'Join',
    component: Join
  },
  {
    path: '/commentcreate',
    name: 'CommentCreate',
    component: CommentCreate
  },
  {
    path: '/commentlist',
    name: 'CommentList',
    component: CommentList
  },
  {
    path: '/commentlistitem',
    name: 'CommentListItem',
    component: CommentListItem
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
