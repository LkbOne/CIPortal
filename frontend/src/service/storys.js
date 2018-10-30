import storysRepository from '@/repositories/storys'

// const getBroadsId = async (token, appkey) => {
//   let response = await storysRepository.getBroadsId(token, appkey)
//   return response
// }
const getBroadsId = async (data) => {
  let response = await storysRepository.getBroadsId(data)
  return response
}
const initTrelloProject = async (data) => {
  let response = await storysRepository.initTrelloProject(data)
  return response
}
const changeTrelloSetting = async (account, setting) => {
  return await storysRepository.getTodoCardByBoardId(setting, account)
}

export default {
  getBroadsId: getBroadsId,
  getTodoCardByBoardId: changeTrelloSetting,
  initTrelloProject: initTrelloProject
}
