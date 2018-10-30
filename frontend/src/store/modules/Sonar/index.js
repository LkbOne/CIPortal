import mutations from './mutations'
import * as actions from './actions'
import * as getters from './getters'
import state from './state'

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
