<template>
  <!--<keep-alive>-->
  <div style="overflow: auto;">
    <Header style="top: 0;"></Header>
    <grid-layout
      :layout="boradsForShowTemp"
      :col-num="12"
      :row-height="30"
      :is-draggable="false"
      :is-resizable="false"
      :is-mirrored="false"
      :vertical-compact="true"
      :margin="[10, 10]"
      :use-css-transforms="true"
      style="margin-top: 64px;"
    >
      <grid-item v-for="item in boradsForShowTemp"
                 :key="item.i"
                 :x="item.x"
                 :y="item.y"
                 :w="item.w"
                 :h="item.h"
                 :i="item.i">
        <div ref="testitem" style="width: 100%;height: 100%;background-color: white;padding: 10px" class="Boards">
          <base-content style="height: 100%;" :contentName="item.i[0]==='0'?'':item.i" :type="'homePage'">
            <div slot="showBoard" style="height: 95%;margin: 5px;">
              <Sonar style="height: 100%;" v-if="item.i==='Sonar'"></Sonar>
              <CI style="height: 100%;" v-if="item.i==='CI Testing'"></CI>
              <Sentry style="height: 100%;" v-show="item.i==='Sentry'"></Sentry>
              <Trello style="height: 100%;" v-if="item.i==='Trello'"></Trello>
              <Job style="height: 100%;" v-if="item.i==='Merge Request'"></Job>
              <!--<EventLogs style="height: 100%;" v-if="item.i==='Event Logs'&&eventlogstestflag"></EventLogs>-->
              <EventLogs style="height: 100%;" v-if="item.i==='Event Logs'"></EventLogs>
              <Geography style="height: 100%;" v-if="item.i==='Geography DashBoard'"></Geography>
              <Technical style="height: 100%;" v-if="item.i==='Technical DashBoard'"></Technical>
            </div>
          </base-content>
        </div>
      </grid-item>
    </grid-layout>
  </div>
  <!--</keep-alive>-->

</template>

<script>
import VueGridLayout from 'vue-grid-layout'
import Header from '@/components/Header'
import BaseContent from '@/components/BaseContent'
import CI from '@/pages/CI/CI'
import Sentry from '@/pages/Sentry/Sentry'
import Job from '@/pages/Job/Job'
import Trello from '@/pages/Trello/Storys'
import Sonar from '@/pages/Sonar/Sonar'
import EventLogs from '@/pages/Flurry/EventLogs.vue'
import Geography from '@/pages/Flurry/Geography.vue'
import Technical from '@/pages/Flurry/Technical.vue'
import { mapState, mapActions } from 'vuex'
import user from '@/service/user'

var GridLayout = VueGridLayout.GridLayout
var GridItem = VueGridLayout.GridItem
export default {
  name: 'gird-layout-demo',
  components: {
    GridLayout,
    GridItem,
    Header,
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
      // account: 'admin010',
      // getboardssetting: false,
      // boradsForShowTemp: [{'x': 0, 'y': 0, 'w': 6, 'h': 12, 'i': 'CI Testing'},{'x': 6, 'y': 0, 'w': 6, 'h': 12, 'i': 'Trello'},{'x': 0, 'y': 6, 'w': 6, 'h': 12, 'i': 'Sentry'}, {'x': 6, 'y': 12, 'w': 6, 'h': 12, 'i': 'Merge Request'}],
      boradsForShowTemp: [],
      clientWidth: window.innerWidth,
      eventlogstestflag: false
    }
  },
  computed: {
    ...mapState({
      SentrySliceNum: state => state.SentrySliceNum,
      JobSliceNum: state => state.JobSliceNum,
      CISliceNum: state => state.CISliceNum,
      boradsForShow: state => state.boradsForShow,
      account: state => state.account
    })
  },
  async created() {
    this.$store.dispatch('initAllSlice')
    console.log('this.account:', this.account)
    this.createSocket()
    // this.getAllData()
    // console.log('this.account:',this.account)
    // let receiveData = await this.getAllData()
    let receiveData = await user.getAllData(this.account)
    console.log('receiveData:', receiveData)
    if (receiveData.boardChoice.length === 0) {
      this.$router.push({
        name: 'login'
      })
    }
    this.boradsForShowTemp = receiveData.boardChoice
    this.$store.dispatch('setBoardsSetting', receiveData.boardChoice)
    let projects = receiveData.projects
    for (let i = 0; i < projects.length; i++) {
      switch (projects[i].boardName) {
        case 'Sentry':
          console.log('this.sentryData:', projects[i].boardData)
          this.setSentryData(projects[i].boardData)
          setTimeout(() => {
            this.$eventBus.$emit('showSentry')
          }, 1000)
          // this.$eventBus.$emit('showSentry')
          break
        case 'Sonar':
          this.setSonarData(projects[i].boardData)
          break
        case 'CI Testing':
          this.setCIData(projects[i].boardData)
          break
        case 'Merge Request':
          this.setJobData(projects[i].boardData)
          break
        case 'Event Logs':
          this.setFlurryData(projects[i].boardData)
          break
        case 'Geography DashBoard':
          console.log('Geography:', projects[i].boardData)
          this.setLineData(projects[i].boardData)
          break
        case 'Technical DashBoard':
          this.setBarData(projects[i].boardData)
          break
        case 'Trello':
          this.setTrelloData(projects[i].boardData)
          break
      }
    }
    // if (receiveData.chosenProjects.length === 0) {
    // }
  },
  mounted() {
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
      })()
    }
  },
  methods: {
    ...mapActions('Git', [
      'setCIData',
      'setJobData'
    ]),
    ...mapActions('Sentry', [
      'setSentryData'
    ]),
    ...mapActions('Trello', [
      'setTrelloData'
    ]),
    ...mapActions('Sonar', [
      'setSonarData'
    ]),
    ...mapActions('Flurry', [
      'setFlurryData',
      'setLineData',
      'setBarData'
    ]),
    // async getAllData() {
    //   let receive = await user.getAllData(this.account)
    //   console.log('receive:', receive)
    // },
    createSocket() {
      this.$store.dispatch('initAllSlice')
      let websocket = new WebSocket('ws://10.222.48.23:8070/api')
      let that = this
      websocket.onmessage = function (event) {
        console.log('new event')
        var jsonData = JSON.parse(event.data)
        switch (jsonData.name) {
          // case 'board':
          //   that.flag = true
          //   console.log('oooooooop')
          //   console.log(jsonData.boards)
          //   if (!that.getboardssetting) {
          //     that.getboardssetting = true
          //     that.$store.dispatch('setBoardsSetting', jsonData.boards)
          //     for (let i = 0; i < jsonData.boards.length; i++) {
          //       that.boradsForShowTemp.push(jsonData.boards[i])
          //     }
          //     if (that.clientWidth > 1200) {
          //       that.boradsForShowTemp = []
          //       that.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 6, 'h': 12, 'i': that.boradsForShow[0].i})
          //       that.boradsForShowTemp.push({'x': 6, 'y': 0, 'w': 6, 'h': 12, 'i': that.boradsForShow[1].i})
          //       that.boradsForShowTemp.push({'x': 0, 'y': 6, 'w': 6, 'h': 12, 'i': that.boradsForShow[2].i})
          //       that.boradsForShowTemp.push({'x': 6, 'y': 12, 'w': 6, 'h': 12, 'i': that.boradsForShow[3].i})
          //     } else if (that.clientWidth < 992) {
          //       that.boradsForShowTemp = []
          //       that.boradsForShowTemp.push({'x': 0, 'y': 0, 'w': 12, 'h': 12, 'i': that.boradsForShow[0].i})
          //       that.boradsForShowTemp.push({'x': 0, 'y': 12, 'w': 12, 'h': 12, 'i': that.boradsForShow[1].i})
          //       that.boradsForShowTemp.push({'x': 0, 'y': 24, 'w': 12, 'h': 12, 'i': that.boradsForShow[2].i})
          //       that.boradsForShowTemp.push({'x': 0, 'y': 36, 'w': 12, 'h': 12, 'i': that.boradsForShow[3].i})
          //     }
          //   }
          case 'Trello':
            that.setTrelloData(jsonData.projects)
            that.$eventBus.$emit('showTrello')
            break
          case 'Sentry':
            console.log(jsonData.status)
            if (jsonData.status === 'success') {
              that.setSentryData(jsonData.projects)
              document.getElementById('new-bug').style.visibility = 'hidden'
            } else if (jsonData.status === 'bug') {
              that.setSentryData(jsonData.projects)
              document.getElementById('new-bug').style.visibility = 'visible'
              let alertRadio = new Audio('../static/alert1.mp3')
              alertRadio.play()
              alertRadio.loop = false
            }
            that.$eventBus.$emit('showSentry')
            break
          case 'CI Testing':
            that.setCIData(jsonData.projects)
            that.$eventBus.$emit('showCI')
            break
          case 'Merge Request':
            that.setJobData(jsonData.projects)
            // console.log('merge request:',jsonData.projects)
            that.$eventBus.$emit('showMergeRequest')
            break
          case 'Sonar':
            that.setSonarData(jsonData.projects)
            // console.log(jsonData.projects)
            break
          case 'Event Logs':
            that.setFlurryData(jsonData.projects)
            that.$eventBus.$emit('showEventLogs')
            that.eventlogstestflag = true
            // console.log('flurryOfEventLog:')
            // console.log(jsonData.projects)
            break
          case 'Geography DashBoard':
            that.setLineData(jsonData.projects)
            // that.$eventBus.$emit('showEventLogs')
            // console.log('newDevicesOfRegion:')
            // console.log(jsonData.projects)
            break
          case 'Technical DashBoard':
            that.setBarData(jsonData.projects)
            // that.$eventBus.$emit('showEventLogs')
            // console.log('newDevicesOfTechnical:')
            // console.log(jsonData.projects)
            break
        }
      }
    }
  }
}
</script>
<style scoped>
  @media screen and (max-device-width: 1200px) {
    .column {
      float: none;
      width: auto;
    }

    #sidebar {
      display: none;
    }
  }
</style>
