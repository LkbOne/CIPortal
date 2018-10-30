import sonalRepository from '@/repositories/sonal'

const getProjectIdAndName = async (data) => {
  let response = await sonalRepository.getProjectIdAndName(data)
  return response
}
const initSonarProject = async (data) => {
  let response = await sonalRepository.initSonarProject(data)
  return response
}
const changeSonarSetting = async (account, changedSetting) => {
  const result = await sonalRepository.changeSonarSetting(account, changedSetting)
  return result.data
}
export default {
  getProjectIdAndName: getProjectIdAndName,
  changeSonarSetting: changeSonarSetting,
  initSonarProject: initSonarProject
}
