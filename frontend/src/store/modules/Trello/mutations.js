import * as types from './mutation-types'

const mutations = {
  [types.SAVE_TRELLO_SETTING](state, setting) {
    state.name = setting.name
    state.trello.privateToken = setting.privateToken
    state.trello.appKey = setting.appKey
    state.trello.interval = setting.interval
    state.trello.allProjects = setting.allProjects
    state.trello.chosenProjects = setting.chosenProjects
  },
  [types.SET_TRELLO_REFRESH_STATE](state, flag) {
    state.trelloRefreshState = flag
  },
  [types.SET_TRELLO_DATA](state, trelloProjects) {
    state.storysAllCard = trelloProjects
    state.trelloRefreshState = false
  }
}

export default mutations
