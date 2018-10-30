import * as types from './mutation-types'

export const saveTrelloSetting = ({commit}, setting) => {
  commit(types.SAVE_TRELLO_SETTING, setting)
}
export const setTrelloData = ({commit}, trelloProjects) => {
  commit(types.SET_TRELLO_DATA, trelloProjects)
}
export const setTrelloRefreshState = ({commit}, flag) => {
  commit(types.SET_TRELLO_REFRESH_STATE, flag)
}
