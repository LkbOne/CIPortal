import * as url from '@/common/js/sentry-interfaces'
import { post } from '@/common/js/httpHelper'

// const getSentryId = async (Authorization) => {
//   return await getByParams(`${url.ALLPROJECTSSENTRY}${Authorization}`)
// }
const getSentryId = async (data) => {
  const result = await post(`${url.ALLPROJECTSSENTRY}`, data)
  return result.data
}
const initSentryProject = async (data) => {
  const result = await post(`${url.INIT_SENTRY_PROJECT}`, data)
  return result.data
}
const deleteBug = async (data) => {
  const result = await post(`${url.DELETE_BUG}`, data)
  return result.data
}
const changeSentrySetting = async (choicesSentry, account) => {
  const result = await post(`${url.CHANGESETTING}${account}`, choicesSentry)
  return result.data
}
export default {
  getSentryId: getSentryId,
  changeSentrySetting: changeSentrySetting,
  initSentryProject: initSentryProject,
  deleteBug:deleteBug
}
