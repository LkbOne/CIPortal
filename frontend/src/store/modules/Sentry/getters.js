export const sentryCarouselItem = (state, getters, rootState) => {
  let sentryCarouselItem = []
  state.sentryRefreshState = false
  for (var i = 0; i < state.allSentryProjectAndIssue.length; i = i + rootState.SentrySliceNum) {
    let temp = []
    temp = state.allSentryProjectAndIssue.slice(i, i + rootState.SentrySliceNum)
    if (rootState.SentrySliceNum === 8) {
      let temptest = []
      let tempCol = []
      for (let j = 0; j < temp.length; j = j + 2) {
        tempCol = temp.slice(j, j + 2)
        temptest.push(tempCol)
      }
      sentryCarouselItem.push(temptest)
    } else {
      let temptest = []
      let tempCol = []
      for (let j = 0; j < temp.length; j = j + 1) {
        tempCol = temp.slice(j, j + 1)
        temptest.push(tempCol)
      }
      sentryCarouselItem.push(temptest)
    }
  }
  return sentryCarouselItem
}
