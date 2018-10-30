import userRepository from '@/repositories/user'

// 这里之后要加token机制
const login = async (userForm) => {
  let response = await userRepository.login(userForm)
  return response
}
const regis = async (userForm) => {
  let response = await userRepository.regis(userForm)
  return response
}
const getAllData = async (account) => {
  console.log('service account:', account)
  let response = await userRepository.getAllData(account)
  return response
}
const resetPassword = async (userForm) => {
  let response = await userRepository.resetPassword(userForm)
  return response
}
const sendBoardChoice = async (boardChoice) => {
  let response = await userRepository.sendBoardChoice(boardChoice)
  return response
}
const selectBoard = async () => {
  let response = await userRepository.selectBoard()
  return response
}
const showSonar = async (trelloId, chosenMapping) => {
  let response = await userRepository.showSonar(trelloId, chosenMapping)
  return response
}
const saveBoardsSetting = async (account, boardsSetting) => {
  let response = await userRepository.saveBoardsSetting(account, boardsSetting)
  return response
}
export default {
  login: login,
  regis: regis,
  getAllData: getAllData,
  selectBoard: selectBoard,
  sendBoardChoice: sendBoardChoice,
  showSonar: showSonar,
  resetPassword: resetPassword,
  saveBoardsSetting: saveBoardsSetting
}
