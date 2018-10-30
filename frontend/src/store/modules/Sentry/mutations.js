import * as types from './mutation-types'

const mutations = {
  // [types.SAVE_SENTRY_SETTING](state, setting) {
  //   state.sentryToken = setting.sentryToken
  //   state.chosenSentryProjects = setting.chosenSentryProjects
  //   state.sentryInterval = setting.sentryInterval
  //   state.allSentryProjects = setting.allSentryProjects
  // },
  [types.SAVE_SENTRY_SETTING](state, setting) {
    state.sentry.name = setting.name
    state.sentry.privateToken = setting.privateToken
    state.sentry.chosenProjects = setting.chosenProjects
    state.sentry.interval = setting.interval
    state.sentry.allProjects = setting.allProjects
  },
  [types.SET_SENTRY_REFRESH_STATE](state, flag) {
    state.sentryRefreshState = flag
  },
  [types.SET_SENTRY_DATA](state, sentryProjects) {
    console.log('state.SentryData:', sentryProjects)
    state.allSentryProjectAndIssue = sentryProjects
    // state.sentry.allProjects=sentryProjects
  }
}

export default mutations
