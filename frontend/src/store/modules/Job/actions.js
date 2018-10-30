import * as types from './mutation-types'

// export const saveGitSetting = ({commit, state}, gitSetting) => {
//   commit(types.SAVE_GIT_SETTING, gitSetting)
// }
export const saveCITestingSetting = ({commit, state}, setting) => {
  commit(types.SAVE_CI_SETTING, setting)
}
export const saveMergeRequestSetting = ({commit, state}, setting) => {
  commit(types.SAVE_MERGE_REQUEST_SETTING, setting)
}
export const setJobData = ({commit}, jobProjects) => {
  commit(types.SET_JOB_DATA, jobProjects)
}
export const setCIData = ({commit}, CIProjects) => {
  commit(types.SET_CI_DATA, CIProjects)
}
export const setGitRefreshState = ({commit}, flag) => {
  commit(types.SET_GIT_REFRESH_STATE, flag)
}
