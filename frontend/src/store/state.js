const state = {
  windowclientwidth: window.innerWidth,
  priority: '0',
  account: ' ',
  sonarTrelloMapping: [],
  sentryTrelloMapping: [],
  sentryGitLabMapping: [],
  clientWidth: window.innerWidth >= 992 ? window.innerWidth / 2 : window.innerWidth,
  JobSliceNum: 0,
  CISliceNum: 0,
  CICardWidth: 0,
  SentrySliceNum: 0,
  SentryWidth: 0,
  TrelloWidth: 0,
  boradsForShow: [],
  allBoards:[],
  //subaccount
  authority:0,
  boardChoice:[]
}

export default state
