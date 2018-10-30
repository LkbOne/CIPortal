import flurryRepository from '@/repositories/flurry'

const getFlurryProject = async (data) => {
  return await flurryRepository.getFlurryProject(data)
}
const initFlurryProject = async (data) => {
  return await flurryRepository.initFlurryProject(data)
}
const saveFlurrySetting = async (account, setting) => {
  return await flurryRepository.saveFlurrySetting(setting, account)
}
export default {
  getFlurryProject: getFlurryProject,
  saveFlurrySetting: saveFlurrySetting,
  initFlurryProject: initFlurryProject
}
