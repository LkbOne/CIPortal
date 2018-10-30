<template>
  <div style="height: 100%">
    <!--<div style="text-align: center">-->
      <!--<span style="font-weight: bold;font-size: 30px;">Event Logs</span>-->
    <!--</div>-->
    <!--<div style="height: 95%">-->
      <event-logs class="event-logs"></event-logs>
    <!--</div>-->
  </div>
</template>

<script>
import EventLogs from '@/pages/Flurry/EventLogs'
import LineEcharts from '@/pages/Flurry/LineEcharts'

export default {
  name: 'enlarge-flurry-board',
  components: {
    EventLogs,
    LineEcharts
  },
  created(){
    console.log('-----------------------------',this.$route.path)
  }
}
</script>

<style scoped>
  .event-logs {
    /*width: 50%;*/
    /*height: 100%;*/
    /*float: left;*/
    background-color: #fff;
  }

  .line-echarts {
    float: right;
  }
</style>
