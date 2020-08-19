import Vue from 'vue'
import VueRouter from 'vue-router'
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
import Main from '../page/post/main.vue'
import TagList from '../page/post/TagList.vue'
import Notice from '../page/post/Notice.vue'
import NoticeDetail from '../page/post/NoticeDetail.vue'
import NoticeCreate from '../page/post/NoticeCreate.vue'
import NoticeModify from '../page/post/NoticeModify.vue'
import MainFooter from '../page/post/MainFooter.vue'
import ThirdMain from '../page/post/ThirdMain'
import UpdateReply from '../page/post/UpdateReply.vue'
import FloatingIcon from '../page/post/FloatingIcon.vue'
import Quiz from '../page/post/Quiz.vue'
import Userupdate from '../page/user/UpdateUser.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/updatereply/:queNo/:lang/:rpNo',
    name: 'updatereply',
    component: UpdateReply
  },
  {
    path: '/updateuser/:userNo/:kakao?',
    name: 'updateuser',
    component: Userupdate
  },
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
    path: '/profile/:userNo',
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
    path: '/detail/:queNo/:lang?',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/list/:lang/:keyword?',
    name: 'List',
    component: List
  },
  {
    path: '/bookmark/:lang?',
    name: 'Bookmark',
    component: Bookmark
  },
  {
    path: '/updatequestion/:queNo',
    name: 'UpdateQuestion',
    component: UpdateQuestion
  },
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/taglist/:tag',
    name: 'TagList',
    component: TagList
  },
  {
    path: '/notice',
    name: 'Notice',
    component: Notice
  },
  {
    path: '/noticedetail/:noticeNo',
    name: 'NoticeDetail',
    component: NoticeDetail
  },
  {
    path: '/noticecreate',
    name: 'NoticeCreate',
    component: NoticeCreate
  },
  {
    path: '/noticemodify/:noticeNo',
    name: 'NoticeModify',
    component: NoticeModify
  },
  {
    path: '/mainfooter',
    name: 'MainFooter',
    component: MainFooter
  },
  {
    path: '/thirdmain',
    name: 'ThirdMain',
    component: ThirdMain
  },
  {
    path: '/floatingicon',
    name: 'FloatingIcon',
    component: FloatingIcon
  },
  {
    path: '/quiz',
    name: 'Quiz',
    component: Quiz
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
