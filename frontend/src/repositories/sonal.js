import * as url from '@/common/js/sonar-interfaces'
import { post } from '@/common/js/httpHelper'

const getProjectIdAndName = async (data) => {
  const result = await post(`${url.PROJECTS}`, data)
  return result.data
}
const initSonarProject = async (data) => {
  const result = await post(`${url.INIT_SONAR_PROJECT}`, data)
  return result.data
}
// const getProjectIdAndName = async (host) => {
//   const result = await getByParams(`${url.PROJECTS}${host}`)
//   return result
// }
const changeSonarSetting = async (account, choicesSonal) => {
  const result = await post(`${url.CHANGESETTING}${account}`, choicesSonal)
  return result
}
export default {
  getProjectIdAndName: getProjectIdAndName,
  changeSonarSetting: changeSonarSetting,
  initSonarProject: initSonarProject
}
