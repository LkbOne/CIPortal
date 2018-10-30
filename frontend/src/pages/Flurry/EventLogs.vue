<template>
  <div style="height: 100%;overflow: auto">
    <div class="table" v-if="flurryData===undefined||flurryData.length===0"></div>
    <div v-if="this.$route.path==='/EnlargeFlurryBoard'" style="margin: 10px">
      <div style="font-weight: bold;font-size: 30px;text-align: center">Event Logs</div>
      <Input v-model="searchWord" icon="search" @on-change="handleSearch" style="width: 200px;position: absolute;top:20px;right: 70px" />
    </div>
    <i-table v-if="devicesTemp!==undefined&&devicesTemp.length!=0" class="table" :columns="columns5" :data="devicesTemp[this.currentPage-1]" :height="this.tableHeight"
             size="large" style="overflow-y: scroll;webkit-overflow-scrolling: touch;overflow: auto;"></i-table>
    <div v-if="flurryData!==undefined&&flurryData.length!=0" style="text-align: center;display: flex;
    justify-content:center;
    align-items:Center;margin: 10px 0">
      <!--<Input v-model="searchWord" icon="search" @on-change="handleSearch" style="width: 200px" />-->
      <!--<span style="font-size: 15px;color: #3058b6;">{{this.flurryData[currentPage-1].projectName}}</span>-->
      <Page :current="currentPage" :total="flurryData.length" :page-size="1" simple @on-change="dealCurrentPage"
            style="text-align: center;display: inline-block;"/>
    </div>
  </div>
</template>

<script>
import EventLogsExpand from '@/pages/Flurry/EventLogsExpand.vue'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'event-logs',
  components: {
    EventLogsExpand
  },
  data() {
    return {
      loading: true,
      searchWord:'',
      tableHeight: 0,
      currentPage: 1,
      test:[],
      value1: 0,
      devicesTemp:[],
      columns5: [
        // {
        //   type: 'expand',
        //   width: 30,
        //   render: (h, params) => {
        //     console.log('params:',params)
        //     return h(EventLogsExpand, {
        //       props: {
        //         row: params.row
        //       }
        //     })
        //   }
        // },
        {
          title: 'Date&Time',
          key: 'time',
          minWidth: 200,
          align: 'center',
          sortable: true
        },
        {
          title: 'App Version',
          key: 'appVersion',
          minWidth: 140,
          align: 'center',
          sortable: true
        },
        {
          title: 'Device Model',
          key: 'deviceModel',
          minWidth: 200,
          align: 'center',
          sortable: true,
        },
        {
          title: 'Country',
          key: 'countryISO',
          minWidth: 110,
          align: 'center',
          sortable: true
        },
        {
          title: 'Event',
          key: 'eventsLength',
          minWidth: 100,
          align: 'center',
          sortable: true
        }
      ]
    }
  },
  methods: {
    ...mapActions('Flurry', [
      'setEventLogsRefreshState'
    ]),
    dealCurrentPage(page) {
      this.currentPage = page
    },
    showEventLogs(){
      this.loading=false
    },
    search (data, argumentObj) {
      let res = data;
      let dataClone = data;
      // console.log('data',data)
      // console.log('argumentObj',argumentObj)
      for (let argu in argumentObj) {
        // console.log('argumentObj[argu]:',argumentObj[argu])
        if (argumentObj[argu].length > 0) {
          res = dataClone.filter(d => {
            // console.log('argu:',argu)
            // console.log('d:',d)
            // console.log('d[argu]:',d[argu])
            return d[argu].indexOf(argumentObj[argu]) > -1;
          });
          dataClone = res;
        }
      }
      console.log('res',res)
      return res;
    },
    handleSearch () {
      // console.log('handleSearch')
      // console.log('flurryData',this.flurryData)
      // console.log('this.flurryData[this.current-1].devices',this.flurryData[this.currentPage-1].devices)
      // let devicesTemp=[]
      this.devicesTemp[this.currentPage-1]=[]
      for(let i=0;i<this.flurryData[this.currentPage-1].devices.length;i++){
        // this.devicesTemp[this.currentPage-1].push({appVersion:this.flurryData[this.currentPage-1].devices[i].appVersion,countryISO:this.flurryData[this.currentPage-1].devices[i].countryISO,time:this.flurryData[this.currentPage-1].devices[i].time,deviceModel:this.flurryData[this.currentPage-1].devices[i].deviceModel,eventsLength:this.flurryData[this.currentPage-1].devices[i].eventsLength})
        // this.flurryData[this.currentPage-1].devices.push(this.devicesTemp[i])
        this.devicesTemp[this.currentPage-1].push(this.flurryData[this.currentPage-1].devices[i])
      }
      console.log('this.devicesTemp:', this.devicesTemp[this.currentPage-1])
      // this.flurryData[this.currentPage-1].devices = this.search(this.flurryData[this.currentPage-1].devices, {name: this.searchWord});
      this.devicesTemp[this.currentPage-1] = this.search(this.devicesTemp[this.currentPage-1], {deviceModel: this.searchWord})
    }
  },
  mounted() {
    console.log('mounted')
    // this.$nextTick(() => {
    var tabledom = document.getElementsByClassName('table')
    console.log(tabledom.length)
    for (let i = 0; i < tabledom.length; i++) {
      setTimeout(() => {
        var tableParent = tabledom[i].parentNode
        this.tableHeight = this.$route.path==='/EnlargeFlurryBoard'?tableParent.offsetHeight - 120: tableParent.offsetHeight - 45
      }, 100)
    }
    if (this.eventLogsRefreshState === false) {
      let that = this
      this.$nextTick(function () {
        that.setEventLogsRefreshState(true)
      })
    }
    // this.test=this.devicesTemp
    // for()
    // this.columns5[3].filters.push({label: 1, value: 1})
  },
  computed: {
    ...mapState('Flurry', {
      flurryData: 'flurryData',
      eventLogsRefreshState: state => state.eventLogsRefreshState
    })
    // devicesTemp:function () {
    //   let devicesTemp=[]
    //   for(let i=0;i<this.flurryData[this.currentPage-1].devices.length;i++){
    //     devicesTemp.push({appVersion:this.flurryData[this.currentPage-1].devices[i].appVersion,countryISO:this.flurryData[this.currentPage-1].devices[i].countryISO,time:this.flurryData[this.currentPage-1].devices[i].time,deviceModel:this.flurryData[this.currentPage-1].devices[i].deviceModel,eventsLength:this.flurryData[this.currentPage-1].devices[i].eventsLength})
    //     // this.flurryData[this.currentPage-1].devices.push(this.devicesTemp[i])
    //   }
    //   console.log('devicesTemp:',devicesTemp)
    //   return devicesTemp
    // }
  },
  created() {
    this.$eventBus.$on('showEventLogs', this.showEventLogs)
    this.devicesTemp=[]
    for (let i = 0; i < this.flurryData.length; i++) {
      let temp = []
      for (let j = 0; j < this.flurryData[i].devices.length; j++) {
        // temp.push({appVersion:this.flurryData[i].devices[j].appVersion,countryISO:this.flurryData[i].devices[j].countryISO,time:this.flurryData[i].devices[j].time,deviceModel:this.flurryData[i].devices[j].deviceModel,eventsLength:this.flurryData[i].devices[j].eventsLength})
        temp.push(this.flurryData[i].devices[j])
        // console.log('tempData：', temp)
      }
      this.devicesTemp.push(temp)
    }
    // for(let i=0;i<this.flurryData[this.currentPage-1].devices.length;i++){
    //   this.devicesTemp.push({appVersion:this.flurryData[this.currentPage-1].devices[i].appVersion,countryISO:this.flurryData[this.currentPage-1].devices[i].countryISO,time:this.flurryData[this.currentPage-1].devices[i].time,deviceModel:this.flurryData[this.currentPage-1].devices[i].deviceModel,eventsLength:this.flurryData[this.currentPage-1].devices[i].eventsLength})
    //   // this.flurryData[this.currentPage-1].devices.push(this.devicesTemp[i])
    // }
  },
  destroyed() {
    this.$eventBus.$off('showEventLogs', this.showEventLogs)
  },
  watch: {
    // clientWidth: function () {
    //   this.$store.dispatch('initAllSlice')
    // },
    eventLogsRefreshState: function () {
      if (this.eventLogsRefreshState === false) {
        let that = this
        this.$nextTick(function () {
          this.devicesTemp=[]
          for (let i = 0; i < this.flurryData.length; i++) {
            let temp = []
            for (let j = 0; j < this.flurryData[i].devices.length; j++) {
              // temp.push({appVersion:this.flurryData[i].devices[j].appVersion,countryISO:this.flurryData[i].devices[j].countryISO,time:this.flurryData[i].devices[j].time,deviceModel:this.flurryData[i].devices[j].deviceModel,eventsLength:this.flurryData[i].devices[j].eventsLength})
              temp.push(this.flurryData[i].devices[j])
              console.log('tempData：', temp)
            }
            this.devicesTemp.push(temp)
          }
          that.setEventLogsRefreshState(true)
        })
      }
      // if(this.eventLogsRefreshState === true){
      //   var tabledom = document.getElementsByClassName('table')
      //   console.log('table', tabledom.length)
      //   for (let i = 0; i < tabledom.length; i++) {
      //     setTimeout(() => {
      //       var tableParent = tabledom[i].parentNode
      //       this.tableHeight = tableParent.offsetHeight - 45
      //     }, 100)
      //   }
      // }
    }
  }
}
</script>

<style scoped>

</style>
