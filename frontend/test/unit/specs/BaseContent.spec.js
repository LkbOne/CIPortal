import BaseContent from '@/components/BaseContent'
import {mount} from 'vue-test-utils'
describe('BaseContent.vue', () => {
  it('should render correct contents', () => {
    const wrapper = mount(BaseContent)
    const contentName = wrapper.vm.contentName
    expect(wrapper.find('content-layout_header_title_text').textContent)
      .to.equal(contentName)
  })
  it('deleteBoards', () => {
    const wrapper = mount(BaseContent, {
      propsData: {
        type: 'boardsSetting'
      }
    })
    const type = wrapper.vm.type
    expect(type).to.equal('boardsSetting')
    const buttonOfDeleteBoard = wrapper.find('.delete')
    // 触发事件
    buttonOfDeleteBoard.trigger('click')
  })
})
