import * as types from './mutation-types'

const mutations = {
  // [types.SAVE_EVENTLOGS_SETTING](state, setting) {
  //   console.log('setting:')
  //   console.log(setting)
  //   state.flurryPrivateToken = setting.flurryPrivateToken
  //   state.chosenFlurryProjects = setting.chosenFlurryProjects
  //   state.flurryInterval = setting.flurryInterval
  //   state.flurryData = setting.flurryData
  // },
  [types.SAVE_EVENTLOGS_SETTING](state, setting) {
    state.eventLogs.privateToken = setting.privateToken
    state.eventLogs.chosenProjects = setting.chosenProjects
    state.eventLogs.interval = setting.interval
    state.eventLogs.name = setting.name
    state.eventLogs.allProjects = setting.allProjects
  },
  [types.SAVE_GEOGRAPHY_DASHBOARD_SETTING](state, setting) {
    state.geographyDashBoard.privateToken = setting.privateToken
    state.geographyDashBoard.chosenProjects = setting.chosenProjects
    state.geographyDashBoard.interval = setting.interval
    state.geographyDashBoard.name = setting.name
    state.geographyDashBoard.allProjects = setting.allProjects
  },
  [types.SAVE_TECHNICAL_DASHBOARD_SETTING](state, setting) {
    state.technicalDashBoard.privateToken = setting.privateToken
    state.technicalDashBoard.chosenProjects = setting.chosenProjects
    state.technicalDashBoard.interval = setting.interval
    state.technicalDashBoard.name = setting.name
    state.technicalDashBoard.allProjects = setting.allProjects
  },
  [types.SET_FLURRY_DATA](state, flurryProjects) {
    state.flurryData = flurryProjects
    // state.eventLogsRefreshState = false
  },
  [types.SET_LINE_DATA](state, lineProjects) {
    state.lineData = lineProjects
    state.geographyDashBoardRefreshState = false
  },
  [types.SET_BAR_DATA](state, barProjects) {
    state.barData = barProjects
    state.technicalDashBoardRefreshState = false
  },
  [types.SET_GEOGRAPHY_DASHBOARD_REFRESH_STATE](state, flag) {
    state.geographyDashBoardRefreshState = flag
  },
  [types.SET_TECHNICAL_DASHBOARD_REFRESH_STATE](state, flag) {
    state.technicalDashBoardRefreshState = flag
  },
  [types.SET_EVENTLOGS_STATE](state, flag) {
    state.eventLogsRefreshState = flag
  }
  // [types.SET_GIT_REFRESH_STATE](state, flag) {
  //   state.gitRefreshState = flag
  // },
  // [types.SET_JOB_DATA](state, jobProjects) {
  //   state.mergeData = jobProjects
  // },
  // [types.SET_CI_DATA](state, CIProjects) {
  //   state.pipelineData = CIProjects
  // }
}

export default mutations
