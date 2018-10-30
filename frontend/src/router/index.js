import Vue from 'vue'
import Router from 'vue-router'
import Storys from '@/pages/Trello/Storys'
import CI from '@/pages/CI/CI'
import Exception from '@/pages/Sentry/Sentry.vue'
import Design from '@/pages/Job/Job.vue'
import ContentShow from '@/pages/GirdLayoutDemo.vue'
import SettingBoards from '@/pages/SettingBoards.vue'
import Login from '@/pages/Login.vue'
import register from '@/pages/CreateSubAccount.vue'
import ResetPassword from '@/pages/ResetPassword.vue'
import EnlargeFlurryBoard from '@/pages/EnlargeFlurryBoard.vue'
import AllSetting from '@/pages/AllSetting.vue'
Vue.use(Router)

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   // name: 'bFramework',
    //   name: 'login',
    //   component: Login
    // },
    {
      path: '/Register',
      // name: 'bFramework',
      name: 'register',
      component: register
    },
    {
      path: '/EnlargeFlurryBoard',
      // name: 'bFramework',
      name: 'EnlargeFlurryBoard',
      component: EnlargeFlurryBoard
    },
    {
      path: '/',
      // name: 'bFramework',
      name: 'ContentShow',
      component: ContentShow
    },
    {
      path: '/SettingBoards',
      // name: 'bFramework',
      name: 'setting-boards',
      component: SettingBoards
    },
    {
      path: '/CI',
      name: 'CI',
      component: CI
    },
    {
      path: '/Story',
      name: 'Story',
      component: Storys
    },
    {
      path: '/Exception',
      name: 'Exception',
      component: Exception
    },
    {
      path: '/Design',
      name: 'Design',
      component: Design
    },
    {
      path: '/AllSetting',
      name: 'AllSetting',
      component: AllSetting
    },
    // {
    //   path: '/home',
    //   name: 'bFramework',
    //   component: ContentShow
    // },
    {
      path: '/Login',
      // name: 'bFramework',
      name: 'login',
      component: Login
    },
    {
      path: '/ResetPassword',
      // name: 'bFramework',
      name: 'ResetPassword',
      component: ResetPassword
    },
  ]
})
