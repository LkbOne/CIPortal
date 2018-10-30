import { post } from '@/common/js/httpHelper'
import * as url from '@/common/js/trello-interfaces'

// const getBroadsId = async (token, appkey) => {
//   const result = await getByParams(`${url.PROJECTS}${token}&key=${appkey}`)
//   return result.data
// }
const getBroadsId = async (data) => {
  const result = await post(`${url.PROJECTS}`, data)
  return result.data
}
const initTrelloProject = async (data) => {
  const result = await post(`${url.INIT_TRELLO_PROJECT}`, data)
  return result.data
}
const getTodoCardByBoardId = async (setting, account) => {
  const result = await post(`${url.CHANGESETTING}${account}`, setting)
  return result.data
}
export default {
  getBroadsId: getBroadsId,
  getTodoCardByBoardId: getTodoCardByBoardId,
  initTrelloProject: initTrelloProject
}
