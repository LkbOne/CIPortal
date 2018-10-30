<template>
  <div>
    <div :id="'bar'+projectId"></div>
  </div>
</template>

<script>
import echarts from 'echarts/lib/echarts'
import 'echarts/lib/chart/bar'
import 'echarts/lib/component/tooltip'

export default {
  name: 'bar-echarts',
  props: {
    projectName: {
      type: String
    },
    projectId: {
      type: String
    },
    barData: {
      type: Array
    }
  },
  data() {
    return {
      option: {
        tooltip: {
          trigger: 'axis',
          showDelay: 0,
          hideDelay: 50,
          transitionDuration: 0,
          backgroundColor: '#fff',
          borderColor: '#C9C9C9',
          borderWidth: 1,
          textStyle: {
            color: 'black',
            decoration: 'none',
            fontFamily: 'Verdana, sans-serif',
            fontSize: 13
            // fontWeight: 'bold'
          },
          padding: 10,
          formatter: function (params, ticket, callback) {
            var res = '<Strong><font color="#000"> ' + params[0].axisValue + '</font></Strong><br/>'
            res += '<Strong><font color="' + params[0].color + '"> ' + params[0].data.value + '</font></Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
            res += '<font color="#a0a5a8"> ' + params[0].data.projectName + '</font><br/>'
            callback(ticket, res)
            return res
          }
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        calculable: true,
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          data: []
        },
        series: []
      }
    }
  },
  mounted() {
    console.log('1234567:', this.barData)
    setTimeout(() => {
      var board = document.getElementById('board')
      var echartTest = document.getElementById('bar' + this.projectId)
      echartTest.style.width = board.offsetWidth + 'px'
      echartTest.style.height = board.offsetHeight - 60 + 'px'
      for (let i = 0; i < this.barData[0].data.length; i++) {
        this.option.yAxis.data.push(this.barData[0].data[i][0])
      }
      this.option.series.push({name: 'bar', type: 'bar', data: [], barWidth: 10})
      let seriresData = this.option.series[0].data
      for (let i = 0; i < this.barData[0].data.length; i++) {
        seriresData.push({value: this.barData[0].data[i][1], projectName: this.projectName})
      }
      var ec = echarts.init(echartTest)
      ec.setOption(this.option)
    }, 100)
  }
}
</script>

<style scoped>

</style>
