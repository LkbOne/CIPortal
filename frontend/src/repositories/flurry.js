import { post } from '@/common/js/httpHelper'
import * as url from '@/common/js/flurry-interfaces'

const saveFlurrySetting = async (flurryChoice, account) => {
  const result = await post(`${url.SAVE_FLURRY_SETTING}${account}`, flurryChoice)
  return result.data
}
const getFlurryProject = async (data) => {
  const result = await post(`${url.FLURRY_PROJECT}`, data)
  return result.data
}
const initFlurryProject = async (data) => {
  const result = await post(`${url.INIT_FLURRY_PROJECT}`, data)
  return result.data
}
export default {
  getFlurryProject: getFlurryProject,
  saveFlurrySetting: saveFlurrySetting,
  initFlurryProject: initFlurryProject
}
