import * as types from './mutation-types'

export const saveSonarSetting = ({commit}, setting) => {
  commit(types.SAVE_SONAR_SETTING, setting)
}
export const setSonarData = ({commit}, sonarProjects) => {
  commit(types.SET_SONAR_DATA, sonarProjects)
}
