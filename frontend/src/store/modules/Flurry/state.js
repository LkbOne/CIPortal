const state = {
  eventLogsRefreshState: true,
  geographyDashBoardRefreshState: true,
  technicalDashBoardRefreshState: true,
  eventLogs: {
    name: 'Event Logs',
    privateToken: '',
    interval: 5,
    chosenProjects: [],
    allProjects: []
  },
  geographyDashBoard: {
    name: 'Geography DashBoard',
    privateToken: '',
    interval: 5,
    chosenProjects: [],
    allProjects: []
  },
  technicalDashBoard: {
    name: 'Technical DashBoard',
    privateToken: '',
    interval: 5,
    chosenProjects: [],
    allProjects: []
  },
  // flurryPrivateToken: 'eyJhbGciOiJIUzI1NiIsImtpZCI6ImZsdXJyeS56dXVsLnByb2Qua2V5c3RvcmUua2V5LjIifQ.eyJpc3MiOiJodHRwczovL3p1dWwuZmx1cnJ5LmNvbTo0NDMvdG9rZW4iLCJpYXQiOjE1MzY4MjI1NDgsImV4cCI6MzMwOTM3MzEzNDgsInN1YiI6IjQyODA4NCIsImF1ZCI6IjQiLCJ0eXBlIjo0LCJqdGkiOiI2NTUzIn0.gRcCgdhXZPApIgZrKLF1tary_jlpyG_CP5qBPanFOlA',
  // flurryInterval: 5,
  // flurrychioce: 16,
  // chosenFlurryProjects: [],
  flurryData: [],
  lineData: [],
  barData: []

  // allGitlabProjectData: [],
  // JobCarouseItem: [],
  // CICardData: [],
  // gitRefreshState: true,
  // pipelineData: [],
  // mergeData: []
}

export default state
