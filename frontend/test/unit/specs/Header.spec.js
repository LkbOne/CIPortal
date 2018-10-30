import Header from '@/components/Header'
import { shallow, createLocalVue, mount } from 'vue-test-utils'
import Vuex from 'vuex'

const localVue = createLocalVue()
/* eslint-disable */
localVue.use(Vuex)
describe('Header.vue', () => {
  let state
  let store
  beforeEach(() => {
    state = {
      priority: 0
    }
    store = new Vuex.Store({
      modules: {
        myModule: {
          state
        }
      }
    })
  })
  it('without login', () => {
    const wrapper = mount(Header, {store, localVue})
    // const wrapper = shallow(Header, {store, localVue})
    // expect(state.priority).to.equal(0)
    // const buttonOfShowDropDownItem = wrapper.find('.drop-down')
    // // 触发事件
    // buttonOfShowDropDownItem.trigger('click')
    setTimeout(() => {
      const aa = wrapper.find('.setting-drop-down-item')
      console.log(aa)
      console.log(wrapper.vm)
    }, 1000)

      const login = wrapper.find('.login')
      const logout = wrapper.find('.setting-drop-down-item logout')
      const register = wrapper.find('.setting-drop-down-item register')
      const setting = wrapper.find('.setting-drop-down-item setting')
      // expect(login).to.exist
      // expect(logout).to.not.exist
      // expect(register).to.exist
      // expect(setting).to.exist
  })
  it('to login', () => {
    const wrapper = shallow(Header, {store, localVue})
    expect(state.priority).to.equal(0)
    const buttonOfShowDropDownItem = wrapper.find('.drop-down')
    // 触发事件
    buttonOfShowDropDownItem.trigger('click')
  })
})
// describe('Header.vue', () => {
//   let state
//   let store
//   beforeEach(() => {
//     state = {
//       priority: 1
//     }
//     store = new Vuex.Store({
//       modules: {
//         myModule: {
//           state
//         }
//       }
//     })
//   })
//   it('login', () => {
//     const wrapper = shallow(Header, {store, localVue})
//     expect(state.priority).to.equal(1)
//     const buttonOfShowDropDownItem = wrapper.find('.drop-down')
//     // 触发事件
//     buttonOfShowDropDownItem.trigger('click')
//     setTimeout(() => {
//       const login = wrapper.find('.setting-drop-down-item login')
//       const logout = wrapper.find('.setting-drop-down-item logout')
//       const register = wrapper.find('.setting-drop-down-item register')
//       const setting = wrapper.find('.setting-drop-down-item setting')
//       expect(login).to.not.exist
//       expect(logout).to.exist
//       expect(register).to.not.exist
//       expect(setting).to.exist
//     }, 100)
//   })
// })
// const $route = {
//   path: '/some'
//   // ...其他属性
// }
//
// const $router = {
//   push: jest.fn()
//   // ... 其他属性
// }
//
// const wrapper = mount(Login, {
//   mocks: {
//     $route,
//     $router
//   }
// })
