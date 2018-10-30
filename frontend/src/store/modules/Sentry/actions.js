import * as types from './mutation-types'

export const saveSentrySetting = ({commit, state}, setting) => {
  commit(types.SAVE_SENTRY_SETTING, setting)
}
export const setSentryData = ({commit}, sentryProjects) => {
  commit(types.SET_SENTRY_DATA, sentryProjects)
}
export const setSentryRefreshState = ({commit}, flag) => {
  commit(types.SET_SENTRY_REFRESH_STATE, flag)
}
