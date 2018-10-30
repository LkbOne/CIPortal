import * as types from './mutation-types'

const mutations = {
  // [types.SAVE_SONAR_SETTING](state, setting) {
  //   state.sonarHost = setting.sonarHost
  //   state.chosenSonarProjects = setting.chosenSonarProjects
  //   state.sonarInterval = setting.sonarInterval
  //   state.allSonarProjects = setting.allSonarProjects
  // },
  [types.SAVE_SONAR_SETTING](state, setting) {
    state.sonar.name = setting.name
    state.sonar.chosenProjects = setting.chosenProjects
    state.sonar.interval = setting.interval
    state.sonar.allProjects = setting.allProjects
    state.sonar.host = setting.host
    state.sonar.sonarTrelloMapping = setting.sonarTrelloMapping
  },
  [types.SET_SONAR_DATA](state, sonarProjects) {
    state.sonarData = sonarProjects
  }
}

export default mutations
