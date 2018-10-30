import GitLabConfig from '@/components/GitLabConfig'
import { shallow, createLocalVue, mount } from 'vue-test-utils'
import Vuex from 'vuex'

import Vue from 'vue'

const localVue = createLocalVue()
describe('GitLabConfig.vue', () => {
  // it('should render correct contents', () => {
  //   const wrapper = mount(JobConfig)
  //   console.log('000')
  //   console.log('111')
  //   const gitIp = wrapper.vm.changedSetting.gitIp
  //   const inputGit = wrapper.findAll('.git-input')
  //   console.log(inputGit)
  //   console.log(gitIp)
  //   expect(inputGit[0]).to.equal(gitIp)
  // })
  let state
  let store
  beforeEach(() => {
    state = {
      gitIp: '146.222.94.208',
      gitInterval: 5,
      gitPrivateToken: state => state.gitPrivateToken,
      chosenGitProjects: state => state.chosenGitProjects,
      allGitProjects: state => state.allGitProjects
    }
    store = new Vuex.Store({
      modules: {
        Git: {
          state
        }
      }
    })
  })
  it('should render correct contents', (done) => {
    const wrapper = shallow(GitLabConfig, {store, localVue})
    // const wrapper = shallow(Header, {store, localVue})
    // expect(state.priority).to.equal(0)
    // const buttonOfShowDropDownItem = wrapper.find('.drop-down')
    // // 触发事件
    // buttonOfShowDropDownItem.trigger('click')
    setTimeout(() => {
      const gitIp = wrapper.vm.changedSetting.gitIp
      const inputGit = wrapper.findAll('.git-input')
      console.log('1:' + inputGit)
      console.log('2:' + gitIp)
      expect(inputGit[0]).to.equal(gitIp)
      done()
    }, 1000)
  })
})
