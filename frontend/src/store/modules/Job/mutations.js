import * as types from './mutation-types'

const mutations = {
  // [types.SAVE_GIT_SETTING](state, setting) {
  //   state.gitIp = setting.gitIp
  //   state.gitPrivateToken = setting.gitPrivateToken
  //   state.chosenGitProjects = setting.chosenGitProjects
  //   state.gitInterval = setting.gitInterval
  //   state.allGitProjects = setting.allGitProjects
  //   state.mergeData = setting.allGitProjects.mergeData
  //   state.pipelineData = setting.allGitProjects.pipelineData
  // },
  [types.SAVE_CI_SETTING](state, setting) {
    state.CITesting.ip = setting.ip
    state.CITesting.name = setting.name
    state.CITesting.privateToken = setting.privateToken
    state.CITesting.chosenProjects = setting.chosenProjects
    state.CITesting.interval = setting.interval
    state.CITesting.allProjects = setting.allProjects
  },
  [types.SAVE_MERGE_REQUEST_SETTING](state, setting) {
    state.mergeRequest.ip = setting.ip
    state.mergeRequest.name = setting.name
    state.mergeRequest.privateToken = setting.privateToken
    state.mergeRequest.chosenProjects = setting.chosenProjects
    state.mergeRequest.interval = setting.interval
    state.mergeRequest.allProjects = setting.allProjects
  },
  [types.SET_GIT_REFRESH_STATE](state, flag) {
    state.gitRefreshState = flag
  },
  [types.SET_JOB_DATA](state, jobProjects) {
    state.gitRefreshState = false
    state.mergeData = jobProjects
  },
  [types.SET_CI_DATA](state, CIProjects) {
    state.pipelineData = CIProjects
  }
}

export default mutations
