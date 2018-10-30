<template>
  <div>
    <div class="story-pie" :id="'pie-'+projectName"></div>
  </div>
</template>

<script>
import echarts from 'echarts/lib/echarts'
import 'echarts/lib/chart/pie'

export default {
  props: {
    project: {
      type: Object
    },
    projectName: {
      type: String
    }
  },
  data() {
    return {
      option: {
        series: [
          {
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [
              {
                value: this.project.todoCardsNum,
                name: 'TODO: ' + this.project.todoCardsNum,
                itemStyle: {color: '#abd6f2'}
              },
              {
                value: this.project.testCardsNum,
                name: 'Test: ' + this.project.testCardsNum,
                itemStyle: {color: '#9bd598'}
              },
              {
                value: this.project.doneCardsNum,
                name: 'Done: ' + this.project.doneCardsNum,
                itemStyle: {color: '#ffd68e'}
              }
            ],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
    }
  },
  mounted() {
    // this.$eventBus.$on('initAnalsisiPie', this.initData)
    this.initData()
  },
  methods: {
    initData() {
      let todoCardsNum = this.project.todoCardsNum
      let testCardsNum = this.project.testCardsNum
      let doneCardsNum = this.project.doneCardsNum
      if (todoCardsNum !== 0 || testCardsNum !== 0 || doneCardsNum !== 0) {
        echarts.init(document.getElementById('pie-' + this.project.projectName)).setOption(this.option)
      }
    }
  }
}
</script>

<style scoped>
  .story-pie {
    height: 200px;
    width: 280px;
    margin: auto;
  }
</style>
<!--https://jingyan.baidu.com/article/915fc414a783b151394b209b.html-->
