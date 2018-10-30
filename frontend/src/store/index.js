import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'
import state from './state'
import mutations from './mutations'
import GitModule from './modules/Job'
import SentryModule from './modules/Sentry'
import TrelloModule from './modules/Trello'
import SonarModule from './modules/Sonar'
import FlurryModule from './modules/Flurry'
import {
  Git,
  Sonar,
  Sentry,
  Trello,
  Flurry
} from './namespaces'

Vue.use(Vuex)
const modules = {
  [Git]: GitModule,
  [Sentry]: SentryModule,
  [Trello]: TrelloModule,
  [Sonar]: SonarModule,
  [Flurry]: FlurryModule
}
export default new Vuex.Store({
  actions,
  getters,
  state,
  mutations,
  modules
})
