export const CICardData = (state, getters, rootState) => {
  let CICardlItem = []
  for (var i = 0; i < state.pipelineData.length; i = i + rootState.CISliceNum) {
    let temp = []
    temp = state.pipelineData.slice(i, i + rootState.CISliceNum)
    CICardlItem.push(temp)
  }
  return CICardlItem
}
export const JobCarouseItem = (state, getters, rootState) => {
  let jobCarouseItem = []
  for (var i = 0; i < state.mergeData.length; i = i + rootState.JobSliceNum) {
    let temp = []
    temp = state.mergeData.slice(i, i + rootState.JobSliceNum)
    jobCarouseItem.push(temp)
  }
  // state.gitRefreshState = false
  return jobCarouseItem
}
