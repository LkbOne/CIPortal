<template>
  <div v-if="this.lineData!==undefined&&this.lineData.length!==0">
    <!--<p>{{this.lineData[currentPage-1].series}}</p>-->
    <line-echarts :projectName="this.lineData[currentPage-1].projectName"
                  :lineData="this.lineData[currentPage-1].series" :projectId="this.lineData[currentPage-1].id"
                  v-if="show&&this.lineData[currentPage-1].series!==undefined&&this.lineData[currentPage-1].series.length!==0"/>
    <div v-if="this.lineData[currentPage-1].series.length===0||!show" style="height: 92%;width: 100%;text-align: center;font-size: 26px;">
      <span style="position: relative;top:140px">nothing to show</span>
    </div>
    <div style="text-align: center;display: flex;
    justify-content:center;
    align-items:Center;margin: 10px 0">
      <!--<span style="font-size: 15px;color: #3058b6;">{{this.lineData[currentPage-1].projectName}}</span>-->
      <Page :current="currentPage" :total="lineData.length" :page-size="1" simple @on-change="dealCurrentPage"
            style="text-align: center;display: inline-block;"/>
    </div>
  </div>
</template>

<script>
import LineEcharts from '@/pages/Flurry/LineEcharts'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'geography',
  components: {
    LineEcharts
  },
  data() {
    return {
      tableHeight: 0,
      currentPage: 1,
      show: true
    }
  },
  methods: {
    ...mapActions('Flurry', [
      'setGeographyDashBoardRefreshState'
    ]),
    dealCurrentPage(page) {
      this.currentPage = page
      this.show = false
      setTimeout(() => {
        this.show = true
      }, 50)
    }
  },
  computed: {
    ...mapState('Flurry', {
      lineData: 'lineData',
      geographyDashBoardRefreshState: 'geographyDashBoardRefreshState'
    }),
    ...mapState({
      clientWidth: state => state.clientWidth
    }),
  },
  created() {
    console.log('geography data:', this.lineData)
  },
  watch: {
    clientWidth: function () {
      this.setGeographyDashBoardRefreshState(false)
    },
    geographyDashBoardRefreshState: function () {
      if (this.geographyDashBoardRefreshState === false) {
        let that = this
        this.$nextTick(function () {
          that.setGeographyDashBoardRefreshState(true)
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
