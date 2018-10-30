import { post } from '@/common/js/httpHelper'
import * as url from '@/common/js/gitlab-interfaces'

// const getGitlabId = async (ip, privateToken) => {
//   const result = await getByParams(`${url.JOBLABFRONT}${ip}&gitPrivateToken=${privateToken}`)
//   return result.data
// }
const getGitlabId = async (data) => {
  const result = await post(`${url.JOBLABFRONT}`, data)
  console.log('post data:',result)
  return result.data
}
const initGitlabProject = async (data) => {
  const result = await post(`${url.INIT_GITLAB_PROJECT}`, data)
  return result.data
}
const changeGitSetting = async (gitlabChoice, account) => {
  const result = await post(`${url.CHANGESETTING}${account}`, gitlabChoice)
  return result.data
}
export default {
  getGitlabId: getGitlabId,
  changeGitSetting: changeGitSetting,
  initGitlabProject: initGitlabProject
}
