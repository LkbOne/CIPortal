import * as url from '@/common/js/user-interfaces'
import { post, getByParams } from '@/common/js/httpHelper'

const login = async (userJson) => {
  const result = await post(`${url.LOGIN}`, userJson)
  return result.data
}
const regis = async (userJson) => {
  const result = await post(`${url.REGISTER}`, userJson)
  return result.data
}
const findByAccount = async (account) => {
  const result = await getByParams(`${url.FINDBYACCOUNT}${account}`)
  return result.data
}
const selectBoard = async () => {
  const result = await getByParams(`${url.SELECT_BOARD}`)
  return result.data
}
const sendBoardChoice = async (boardChoice) => {
  const result = await post(`${url.SEND_BOARD_CHOICE}`, boardChoice)
  return result.data
}
const getAllData = async (account) => {
  console.log('account:', account)
  console.log(`${url.FINDBYACCOUNT}`, account)
  const result = await post(`${url.FINDBYACCOUNT}`, account)
  return result.data
}
const resetPassword = async (userForm) => {
  const result = await post(`${url.RESET_PASSWORD}`, userForm)
  return result.data
}
const showSonar = async (trelloId, chosenMapping) => {
  const result = await post(`${url.SHOWSONAR}`, trelloId, chosenMapping)
  return result.data
}
const saveBoardsSetting = async (account, boardsSetting) => {
  const result = await post(`${url.INDIVIDUATION}${account}`, boardsSetting)
  return result.data
}
export default {
  login: login,
  regis: regis,
  findByAccount: findByAccount,
  getAllData: getAllData,
  sendBoardChoice: sendBoardChoice,
  showSonar: showSonar,
  selectBoard: selectBoard,
  resetPassword: resetPassword,
  saveBoardsSetting: saveBoardsSetting
}
