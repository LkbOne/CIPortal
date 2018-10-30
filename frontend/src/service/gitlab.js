import gitlabRepository from '@/repositories/gitlab'

// const getGitlabId = async (ip, privateToken) => {
//   return await gitlabRepository.getGitlabId(ip, privateToken)
// }
const getGitlabId = async (data) => {
  let response = await gitlabRepository.getGitlabId(data)
  return response
}
const initGitlabProject = async (data) => {
  let response = await gitlabRepository.initGitlabProject(data)
  return response
}
const changeGitSetting = async (account, setting) => {
  return await gitlabRepository.changeGitSetting(setting, account)
}
export default {
  getGitlabId: getGitlabId,
  getAllGitLabHook: changeGitSetting,
  initGitlabProject: initGitlabProject
}
