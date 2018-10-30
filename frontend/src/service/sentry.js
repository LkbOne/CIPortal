import sentryRepository from '@/repositories/sentry'

const getSentryId = async (data) => {
  let response = await sentryRepository.getSentryId(data)
  return response
}
const initSentryProject = async (data) => {
  let response = await sentryRepository.initSentryProject(data)
  return response
}
const deleteBug = async (data) => {
  let response = await sentryRepository.deleteBug(data)
  console.log('delete bug')
  return response
}
const changeSentrySetting = async (account, setting) => {
  let projects = sentryRepository.changeSentrySetting(setting, account)
  return projects
}
export default {
  getSentryId: getSentryId,
  getAllSentryIssue: changeSentrySetting,
  initSentryProject: initSentryProject,
  deleteBug:deleteBug
}
