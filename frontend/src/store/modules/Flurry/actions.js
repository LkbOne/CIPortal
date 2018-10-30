import * as types from './mutation-types'

export const saveEventLogsSetting = ({commit, state}, eventLogsSetting) => {
  commit(types.SAVE_EVENTLOGS_SETTING, eventLogsSetting)
}
export const saveGeographyDashBoardSetting = ({commit, state}, geographyDashBoardSetting) => {
  commit(types.SAVE_GEOGRAPHY_DASHBOARD_SETTING, geographyDashBoardSetting)
}
export const saveTechnicalDashBoardSetting = ({commit, state}, technicalDashBoardSetting) => {
  commit(types.SAVE_TECHNICAL_DASHBOARD_SETTING, technicalDashBoardSetting)
}
export const setFlurryData = ({commit}, flurryProjects) => {
  commit(types.SET_FLURRY_DATA, flurryProjects)
}
export const setLineData = ({commit}, lineProjects) => {
  commit(types.SET_LINE_DATA, lineProjects)
}
export const setBarData = ({commit}, barProjects) => {
  commit(types.SET_BAR_DATA, barProjects)
}
export const setGeographyDashBoardRefreshState = ({commit}, flag) => {
  commit(types.SET_GEOGRAPHY_DASHBOARD_REFRESH_STATE, flag)
}
export const setTechnicalDashBoardRefreshState = ({commit}, flag) => {
  commit(types.SET_TECHNICAL_DASHBOARD_REFRESH_STATE, flag)
}
export const setEventLogsRefreshState = ({commit}, flag) => {
  commit(types.SET_EVENTLOGS_STATE, flag)
}
// export const setJobData = ({commit}, jobProjects) => {
//   commit(types.SET_JOB_DATA, jobProjects)
// }
// export const setCIData = ({commit}, CIProjects) => {
//   commit(types.SET_CI_DATA, CIProjects)
// }
// export const setGitRefreshState = ({commit}, flag) => {
//   commit(types.SET_GIT_REFRESH_STATE, flag)
// }
