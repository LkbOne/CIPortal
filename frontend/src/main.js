// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from '@/App'
import Vuex from 'vuex'
import store from '@/store/index.js'
import router from '@/router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'

import VueAgile from 'vue-agile'
import '../src/common/scss/agile.scss'
import DrawerLayout from 'vue-drawer-layout'

Vue.use(DrawerLayout)
Vue.use(VueAgile)
Vue.config.productionTip = false
Vue.use(iView)
Vue.use(Vuex)
/* eslint-disable no-new */
var EventBus = new Vue()
Object.defineProperties(Vue.prototype, {
  $eventBus: {
    get: function () {
      return EventBus
    }
  }
})
new Vue({
  store,
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})
