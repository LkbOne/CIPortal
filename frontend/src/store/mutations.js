import * as types from './mutation-types'

const mutations = {
  [types.SET_ALL_BOARDS](state, allBoards) {
    state.allBoards = allBoards
  },
  [types.SET_ACCOUNT](state, account) {
    state.account = account
  },
  [types.SET_USER](state, user) {
    state.account = user.account
    state.sonarTrelloMapping = user.sonarTrelloMapping
    state.sentryTrelloMapping = user.sentryTrelloMapping
  },
  [types.SET_PRIORITY](state, priority) {
    state.priority = priority
  },
  [types.SET_AUTHORITY](state, authority) {
    state.authority = authority
  },
  [types.SET_TRELLO_AND_SONAL](state, TrelloAndSonal) {
    state.sonarTrelloMapping = TrelloAndSonal
  },
  [types.SET_TRELLO_AND_SENTRY](state, TrelloAndSentry) {
    state.sentryTrelloMapping = TrelloAndSentry
    console.log('state.sentryTrelloMapping:',state.sentryTrelloMapping)
  },
  [types.SET_GITLAB_AND_SENTRY](state, GitLabAndSentry) {
    state.sentryGitLabMapping = GitLabAndSentry
    console.log('state.sentryGitLabMapping:',state.sentryGitLabMapping)
  },
  [types.SET_CLIENT_WIDTH](state, {clientWidth, windowclientwidth}) {
    state.clientWidth = clientWidth
    state.windowclientwidth = windowclientwidth
  },
  [types.SET_BOARDS_SETTING](state, boradsForShow) {
    state.boradsForShow = boradsForShow
    console.log('boradsForShow', boradsForShow)
    console.log('state.boradsForShow', state.boradsForShow)
  },
  [types.INIT_ALL_SLICENUM](state) {
    if (state.clientWidth < 550 && state.clientWidth > 400) {
      state.CISliceNum = 2
      state.CICardWidth = 12
    } else if (state.clientWidth < 400) {
      state.CISliceNum = 1
      state.CICardWidth = 24
    } else {
      state.CISliceNum = 3
      state.CICardWidth = 8
    }

    if (state.clientWidth < 600) {
      state.TrelloWidth = 24
    } else {
      state.TrelloWidth = 13
    }

    if (state.clientWidth < 550) {
      state.SentrySliceNum = 4
      state.SentryWidth = 24
    } else {
      state.SentrySliceNum = 8
      state.SentryWidth = 12
    }
    state.JobSliceNum = 4
  }
}

export default mutations
