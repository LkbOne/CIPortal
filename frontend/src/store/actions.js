import * as types from './mutation-types'

export const setClientWidth = ({commit}, {clientWidth, windowclientwidth}) => {
  commit(types.SET_CLIENT_WIDTH, {clientWidth, windowclientwidth})
}
export const setTrelloAndSentry = ({commit}, TrelloAndSentry) => {
  commit(types.SET_TRELLO_AND_SENTRY, TrelloAndSentry)
}
export const setGitLabAndSentry = ({commit}, GitLabAndSentry) => {
  commit(types.SET_GITLAB_AND_SENTRY, GitLabAndSentry)
}
export const setTrelloAndSonal = ({commit}, TrelloAndSonal) => {
  commit(types.SET_TRELLO_AND_SONAL, TrelloAndSonal)
}
export const setPriority = ({commit}, priority) => {
  commit(types.SET_PRIORITY, priority)
}
export const setAuthority = ({commit}, authority) => {
  commit(types.SET_AUTHORITY, authority)
}
export const setUser = ({commit}, user) => {
  commit(types.SET_USER, user)
}
export const initAllSlice = ({commit}) => {
  commit(types.INIT_ALL_SLICENUM)
}
export const setBoardsSetting = ({commit}, boradsForShow) => {
  commit(types.SET_BOARDS_SETTING, boradsForShow)
}
export const setAccount = ({commit}, account) => {
  commit(types.SET_ACCOUNT, account)
}
export const setAllBoards = ({commit}, allBoards) => {
  commit(types.SET_ALL_BOARDS, allBoards)
}
