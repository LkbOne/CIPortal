<template>
  <div>
    <Header class="header">
      <Button @click="show" style="float: right;margin-top: 20px;margin-left: 10px">Board</Button>
      <Button @click="saveBoardsSetting" style="float: right;margin-top: 20px;">Confirm</Button>
    </Header>
    <vue-drawer-layout
      style="overflow: auto!important;"
      id="drawerLayout"
      ref="drawer"
      :content-drawable="true"
      :enable="false"
      :backdrop="false"
      :drawer-width="350">
      <div class="drawer-content" slot="drawer"
           style="background-color: #f0f0f0;height: 100%;width: 350px;opacity:1;overflow: auto" id="drawer">
        <span
          style="text-align: center;display: block;padding: 20px 0;font-size: 18px;border-bottom: 1px solid #cccccc;">Board</span>
        <div class="Options" v-for="board in boardsForSelect" :key="board" :id="board"
             style="width: 90%;height:200px;margin: 10px auto;text-align: center;margin-bottom: 10px"
             draggable='true' @dragstart='dragBoard($event,board)'>
          <div style="border: 1px solid #cccccc;background-color: white;padding: 10px 0">
            <img id="testtesttest" :src="'./static/'+board+'.png'" width="300px" height="150px"/><span
            style="text-align: center;font-size: 14px;font-weight: bold;color: #495060">{{board}}</span>
          </div>
        </div>
      </div>
      <div slot="content" id="content" style="overflow: auto!important;">
        <grid-layout
          :layout="boradsForShowTemp"
          :col-num="12"
          :row-height="30"
          :is-draggable="true"
          :is-resizable="false"
          :is-mirrored="false"
          :vertical-compact="true"
          :margin="[10, 10]"
          :use-css-transforms="true"
          style="margin-top: 64px"
        >
          <grid-item v-for="item in boradsForShowTemp"
                     :key="item.i"
                     :x="item.x"
                     :y="item.y"
                     :w="item.w"
                     :h="item.h"
                     :i="item.i"
                     class="wuwuwuwu">
            <div ref="testitem" style="width: 100%;height: 100%;background-color: white;padding: 10px"
                 @drop='drop(item.i)'
                 @dragover='allowDrop($event)' class="Boards">
              <base-content style="height: 100%;" :contentName="item.i[0]==='0'?'':item.i" :type="'boardsSetting'">
                <div slot="showBoard" style="height: 95%;margin: 10px;">
                  <Sonar style="height: 100%;" v-if="item.i==='Sonar'"></Sonar>
                  <CI style="height: 100%;" v-if="item.i==='CI Testing'"></CI>
                  <Sentry style="height: 100%;" v-show="item.i==='Sentry'"></Sentry>
                  <Trello style="height: 100%;" v-if="item.i==='Trello'"></Trello>
                  <Job style="height: 100%;" v-if="item.i==='Merge Request'"></Job>
                  <EventLogs style="height: 100%;" v-if="item.i==='Event Logs'"></EventLogs>
                  <Geography style="height: 100%;" v-if="item.i==='Flurry Geography'"></Geography>
                  <Technical style="height: 100%;" v-if="item.i==='Flurry Technical'"></Technical>
                </div>
              </base-content>
            </div>
          </grid-item>
        </grid-layout>
      </div>
    </vue-drawer-layout>
  </div>
</template>

<script>
import VueGridLayout from '@/library/src/index'
import BaseContent from '@/components/BaseContent'
import CI from '@/pages/CI/CI'
import Sentry from '@/pages/Sentry/Sentry'
import Job from '@/pages/Job/Job'
import Trello from '@/pages/Trello/Storys'
import Sonar from '@/pages/Sonar/Sonar'
import EventLogs from '@/pages/Flurry/EventLogs'
import Geography from '@/pages/Flurry/Geography'
import Technical from '@/pages/Flurry/Technical'
import { mapState } from 'vuex'
import user from '@/service/user'

var GridLayout = VueGridLayout.GridLayout
var GridItem = VueGridLayout.GridItem
// window.onbeforeunload = function () {
//   console.log('console')
//   return 'alert'
// }
export default {
  name: 'setting-boards',
  components: {
    GridLayout,
    GridItem,
    // Header,
    CI,
    Sentry,
    Job,
    Trello,
    Sonar,
    EventLogs,
    Geography,
    Technical,
    BaseContent
  },
  data() {
    return {
      // account: 'admin',
      flag: false,
      // boradsForShowTemp: [{'x': 0, 'y': 0, 'w': 6, 'h': 12, 'i': 'CI Testing'},{'x': 6, 'y': 0, 'w': 6, 'h': 12, 'i': 'Trello'},{'x': 0, 'y': 6, 'w': 6, 'h': 12, 'i': 'Sentry'}, {'x': 6, 'y': 12, 'w': 6, 'h': 12, 'i': 'Merge Request'}],
      boradsForShowTemp: [],
      cardWidth: 0,
      cardHeight: 0,
      clientWidth: window.innerWidth,
      // allBoards: ['CI Testing', 'Trello', 'Sentry', 'Merge Request', 'Sonar', 'Event Logs', 'Geography DashBoard', 'Technical DashBoard'],
      // boardsForSelect: ['Sonar'],
      boardsForSelect: [],
      nowDrag: {},
      toggle: false
      // boradsForSave: []
    }
  },
  computed: {
    ...mapState({
      allBoards: state => state.allBoards,
      account: state => state.account,
      priority: state => state.priority,
      SentrySliceNum: state => state.SentrySliceNum,
      JobSliceNum: state => state.JobSliceNum,
      CISliceNum: state => state.CISliceNum,
      boradsForShow: state => state.boradsForShow
    })
  },
  created() {
    if (this.priority === '0') {
      alert('请先登录')
      this.$router.push({
        name: 'login'
      })
    }
    for (let i = 0; i < this.boradsForShow.length; i++) {
      this.boradsForShowTemp.push(this.boradsForShow[i])
      console.log('---------------------')
      console.log(this.boradsForShow)
    }
    for (let i = 0; i < this.allBoards.length; i++) {
      let index = this.boradsForShow.findIndex(list => list.i === this.allBoards[i])
      if (index === -1) {
        console.log('push')
        console.log(this.allBoards[i])
        this.boardsForSelect.push(this.allBoards[i])
      }
    }
    console.log('created')
    console.log(document.body.clientWidth)
    if (this.clientWidth > 1200) {
      this.boradsForShowTemp = []
      this.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 6, 'h': 12, 'i': this.boradsForShow[0].i})
      this.boradsForShowTemp.push({'x': 6, 'y': 0, 'w': 6, 'h': 12, 'i': this.boradsForShow[1].i})
      this.boradsForShowTemp.push({'x': 0, 'y': 6, 'w': 6, 'h': 12, 'i': this.boradsForShow[2].i})
      this.boradsForShowTemp.push({'x': 6, 'y': 12, 'w': 6, 'h': 12, 'i': this.boradsForShow[3].i})
    } else if (this.clientWidth < 992) {
      this.boradsForShowTemp = []
      this.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 12, 'h': 12, 'i': this.boradsForShow[0].i})
      this.boradsForShowTemp.push({'x': 0, 'y': 12, 'w': 12, 'h': 12, 'i': this.boradsForShow[1].i})
      this.boradsForShowTemp.push({'x': 0, 'y': 24, 'w': 12, 'h': 12, 'i': this.boradsForShow[2].i})
      this.boradsForShowTemp.push({'x': 0, 'y': 36, 'w': 12, 'h': 12, 'i': this.boradsForShow[3].i})
    }
    // this.getAllData()
  },
  mounted() {
    this.$eventBus.$on('show', this.show)
    this.$eventBus.$on('deleteBoard', this.deleteBoard)
    setTimeout(() => {
      this.cardWidth = this.$refs.testitem[1].clientWidth
      this.cardHeight = this.$refs.testitem[1].clientHeight
      console.log(this.cardWidth)
      console.log(this.cardHeight)
      if (this.clientWidth > 1200) {
        this.boradsForShowTemp = []
        this.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 6, 'h': 12, 'i': this.boradsForShow[0].i})
        this.boradsForShowTemp.push({'x': 6, 'y': 0, 'w': 6, 'h': 12, 'i': this.boradsForShow[1].i})
        this.boradsForShowTemp.push({'x': 0, 'y': 6, 'w': 6, 'h': 12, 'i': this.boradsForShow[2].i})
        this.boradsForShowTemp.push({'x': 6, 'y': 12, 'w': 6, 'h': 12, 'i': this.boradsForShow[3].i})
      } else if (this.clientWidth < 992) {
        this.boradsForShowTemp = []
        this.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 12, 'h': 12, 'i': this.boradsForShow[0].i})
        this.boradsForShowTemp.push({'x': 0, 'y': 12, 'w': 12, 'h': 12, 'i': this.boradsForShow[1].i})
        this.boradsForShowTemp.push({'x': 0, 'y': 24, 'w': 12, 'h': 12, 'i': this.boradsForShow[2].i})
        this.boradsForShowTemp.push({'x': 0, 'y': 36, 'w': 12, 'h': 12, 'i': this.boradsForShow[3].i})
      }
      this.$eventBus.$emit('showTrello')
      this.$eventBus.$emit('showSentry')
      this.$eventBus.$emit('showCI')
      this.$eventBus.$emit('showMergeRequest')
      this.$eventBus.$emit('showEventLogs')
    }, 100)
    let that = this
    onresize = () => {
      console.log('onresize')
      return (() => {
        console.log('change')
        console.log(document.body.clientWidth)
        console.log(this.$refs.testitem[1].clientWidth)
        if ((document.body.clientWidth < 1200 && document.body.clientWidth > 992) || document.body.clientWidth > 1200) {
          this.boradsForShowTemp = []
          this.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 6, 'h': 12, 'i': this.boradsForShow[0].i})
          this.boradsForShowTemp.push({'x': 6, 'y': 0, 'w': 6, 'h': 12, 'i': this.boradsForShow[1].i})
          this.boradsForShowTemp.push({'x': 0, 'y': 6, 'w': 6, 'h': 12, 'i': this.boradsForShow[2].i})
          this.boradsForShowTemp.push({'x': 6, 'y': 12, 'w': 6, 'h': 12, 'i': this.boradsForShow[3].i})
        } else if (document.body.clientWidth < 992) {
          this.boradsForShowTemp = []
          this.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 12, 'h': 12, 'i': this.boradsForShow[0].i})
          this.boradsForShowTemp.push({'x': 0, 'y': 12, 'w': 12, 'h': 12, 'i': this.boradsForShow[1].i})
          this.boradsForShowTemp.push({'x': 0, 'y': 24, 'w': 12, 'h': 12, 'i': this.boradsForShow[2].i})
          this.boradsForShowTemp.push({'x': 0, 'y': 36, 'w': 12, 'h': 12, 'i': this.boradsForShow[3].i})
        }
        that.$store.dispatch('setClientWidth', {
          clientWidth: that.$refs.testitem[1].clientWidth,
          windowclientwidth: document.body.clientWidth
        })
        var wuwuwu = document.getElementById('content')
        wuwuwu.style.width = this.toggle ? document.body.clientWidth - document.getElementById('drawer').clientWidth + 'px' : document.body.clientWidth + 'px'
      })()
    }
    this.flag = true
  },
  methods: {
    dragBoard(event, board) {
      event.dataTransfer.setData('Text', board)
      this.nowDrag = event.currentTarget
    },
    drag(event, i) {
      event.dataTransfer.setData('Text', i)
      this.nowDrag = event.currentTarget
    },
    drop(i) {
      console.log(this.nowDrag.className)
      if (this.nowDrag.className === 'Options') {
        let data = event.dataTransfer.getData('Text')
        let index1 = this.boardsForSelect.findIndex(list => list == data)
        let index2 = this.boradsForShowTemp.findIndex(list => list.i == i)
        if (i[0] === '0') {
          this.boradsForShowTemp[index2].i = data
          this.boardsForSelect.splice(index1, 1)
        } else {
          this.boardsForSelect.push(this.boradsForShowTemp[index2].i)
          this.boradsForShowTemp[index2].i = data
          this.boardsForSelect.splice(index1, 1)
        }
        switch (data) {
          case 'CI Testing':
            setTimeout(() => {
              this.$eventBus.$emit('showCI')
            }, 100)
            break
          case 'Merge Request':
            setTimeout(() => {
              this.$eventBus.$emit('showMergeRequest')
            }, 100)
            console.log('showMergeRequest')
            break
          case 'Trello':
            setTimeout(() => {
              this.$eventBus.$emit('showTrello')
            }, 100)
            console.log('showTrello')
            break
          case 'Sentry':
            setTimeout(() => {
              this.$eventBus.$emit('showSentry')
            }, 100)
            console.log('showSentry')
            break
        }
      }
    },
    allowDrop(event) {
      event.preventDefault()
    },
    deleteBoard(event) {
      console.log(this.nowDrag.className)
      let data = event
      if (data !== 'undefined') {
        let index = this.boradsForShowTemp.findIndex(list => list.i == data)
        this.boardsForSelect.push(this.boradsForShowTemp[index].i)
        this.boradsForShowTemp[index].i = '0' + data
      }
    },
    show() {
      console.log('show')
      this.$refs.drawer.toggle()
      this.toggle = !this.toggle
      console.log(this.toggle)
      var wuwuwu = document.getElementById('content')
      wuwuwu.style.width = this.toggle ? document.body.clientWidth - document.getElementById('drawer').clientWidth + 'px' : document.body.clientWidth + 'px'
    },
    async saveBoardsSetting() {
      this.$refs.drawer.toggle(false)
      this.toggle = false
      console.log(this.toggle)
      var wuwuwu = document.getElementById('content')
      wuwuwu.style.width = this.toggle ? document.body.clientWidth - document.getElementById('drawer').clientWidth + 'px' : document.body.clientWidth + 'px'
      let resposed = await user.saveBoardsSetting(this.account, this.boradsForShowTemp)
      this.$store.dispatch('setBoardsSetting', this.boradsForShowTemp)
      console.log(this.boradsForShowTemp)
      console.log('调用saveBoards')
      console.log('status:', resposed.status)
      if (resposed.status === 200) {
        this.$router.push({
          name: 'ContentShow'
        })
      }
      // setTimeout(()=>{
      //   this.$router.push({
      //     name: 'ContentShow'
      //   })
      // },1000)
    }
  }
}
</script>
<style>
  .content-wrap {
    overflow: auto !important;
  }

  .header {
    z-index: 100;
    position: fixed;
    width: 100%
  }
</style>
