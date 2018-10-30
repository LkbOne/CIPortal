<template>
  <div v-if="this.barData!==undefined&&this.barData.length!==0">
    <bar-echarts :projectName="this.barData[currentPage-1].projectName"
                  :barData="this.barData[currentPage-1].series" :projectId="this.barData[currentPage-1].id"
                  v-if="show&&this.barData[currentPage-1].series!==undefined&&this.barData[currentPage-1].series.length!==0&&this.barData[currentPage-1].series[0].data.length!==0"/>
    <div v-if="this.barData[currentPage-1].series.length===0||!show||this.barData[currentPage-1].series[0].data.length===0" style="height: 92%;width: 100%;text-align: center;font-size: 26px;">
      <span style="position: relative;top:140px">nothing to show</span>
    </div>
    <div style="text-align: center;display: flex;
    justify-content:center;
    align-items:Center;margin: 10px 0">
      <!--<span style="font-size: 15px;color: #3058b6;">{{this.barData[currentPage-1].projectName}}</span>-->
      <iViewPage :current="currentPage" :total="barData.length" :page-size="1" simple @on-change="dealCurrentPage" :barData="this.barData"
            style="text-align: center;display: inline-block;"/>

    </div>
  </div>
</template>

<script>
import BarEcharts from '@/pages/Flurry/BarEcharts'
import iViewPage from '@/library/src/src/components/page/Page'
import { mapState } from 'vuex'

export default {
  name: 'technical',
  components: {
    BarEcharts,
    iViewPage
  },
  data() {
    return {
      tableHeight: 0,
      currentPage: 1,
      show: true
    }
  },
  methods: {
    dealCurrentPage(page) {
      this.currentPage = page
      this.show = false
      setTimeout(() => {
        this.show = true
      }, 100)
    }
  },
  computed: {
    ...mapState('Flurry', {
      barData: 'barData'
    })
  },
  created() {
    console.log('barData:', this.barData)
  }
}
</script>

<style scoped>

</style>
