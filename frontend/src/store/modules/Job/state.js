const state = {
  // gitIp: '',
  // gitPrivateToken: '',
  // gitInterval: 5,
  // gitchioce: 2,
  // chosenGitProjects: [],
  // allGitProjects: [],

  // allGitlabProjectData: [],
  JobCarouseItem: [],
  CICardData: [],
  gitRefreshState: true,
  pipelineData: [],
  mergeData: [],
  CITesting: {
    name: 'CI Testing',
    ip: '',
    privateToken: '',
    chosenProjects: [],
    allProjects: [],
    interval: 0
  },
  mergeRequest: {
    name: 'Merge Request',
    ip: '',
    privateToken: '',
    chosenProjects: [],
    allProjects: [],
    interval: 0
  }
}

export default state
