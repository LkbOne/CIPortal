<template>
  <div>
    <!--<p>linedata:{{this.lineData[0].deviceNumber[0]}}</p>-->
    <!--<p v-for="(linedata,index) in lineData" :key="index">lindata:{{this.linedata}}</p>-->
    <div :id="'line'+projectId"></div>
  </div>
</template>

<script>
// import echarts from 'echarts'
import echarts from 'echarts/lib/echarts'
import 'echarts/lib/chart/line'
import 'echarts/lib/component/tooltip'
// import { mapState } from 'vuex'
// import 'echarts/lib/component/xAxis'
// import 'echarts/lib/component/yAxis'
export default {
  name: 'e-c-h-a-r-t',
  props: {
    projectName: {
      type: String
    },
    projectId: {
      type: String
    },
    lineData: {
      type: Array
    }
  },
  data() {
    return {
      linetestdata: {},
      desctest: {
        app: 722085,
        region: 1,
        metric: 'newDevices',
        appLabel: 'Big Schedules - Production',
        regionLabel: 'Asia'
      },
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
            fontSize: 12
          },
          padding: 10, // [5, 10, 15, 20]
          // position : function(p) {
          //   // 位置回调
          //   // console.log && console.log(p);
          //   return [p[0] + 10, p[1] - 10];
          // },
          formatter: function (params, ticket, callback) {
            // console.log('length:'+params.length)
            var res = '<Strong><font color="#000"> ' + params[0].axisValue + '</font></Strong><br/>'
            for (let i = 0; i < params.length; i++) {
              res += '<Strong><font color="' + params[i].color + '"> ' + params[i].data.value + '</font></Strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
              res += '<font color="#a0a5a8"> ' + params[i].data.desc.regionLabel + ' - '
              res += params[i].data.desc.appLabel + '</font><br/>'
            }
            // res += '<br/>' + params[0].data + '         ' + params[0].desc.regionLable + params[0].desc.appLable
            callback(ticket, res)
            // console.log(res)
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
        calculable: true,
        xAxis: {
          data: [
            // this.lineData[0].date[0], this.lineData[0].date[1], this.lineData[0].date[2], this.lineData[0].date[3], this.lineData[0].date[4], this.lineData[0].date[5], this.lineData[0].date[6], this.lineData[0].date[7], this.lineData[0].date[8], this.lineData[0].date[9], this.lineData[0].date[10]
            // , this.lineData[0].date[11], this.lineData[0].date[12], this.lineData[0].date[13], this.lineData[0].date[14], this.lineData[0].date[15], this.lineData[0].date[16], this.lineData[0].date[17], this.lineData[0].date[18], this.lineData[0].date[19], this.lineData[0].date[20]
            // , this.lineData[0].date[21], this.lineData[0].date[22], this.lineData[0].date[23], this.lineData[0].date[24], this.lineData[0].date[25], this.lineData[0].date[26], this.lineData[0].date[27], this.lineData[0].date[28], this.lineData[0].date[29]
          ]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          // {
          //   name: '坐标轴触发1',
          //   type: 'line',
          //   data: [
          //     // {value: '', desc: ''}
          //     // {value: this.lineData[0].deviceNumber[0], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[1], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[2], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[3], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[4], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[5], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[5], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[5], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[5], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[5], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[5], desc: this.lineData[0].desc},
          //     // {value: this.lineData[0].deviceNumber[6], desc: this.lineData[0].desc}
          //     // 332, 301, 334, 390, 330, 320
          //   ]
          // }
        ]
      }
    }
  },
  created(){
    console.log('lineecharts data:',this.lineData)
  },
  mounted() {
    console.log('1234567:')
    console.log(this.lineData)
    setTimeout(() => {
      var board = document.getElementById('board')
      // var board = document.body
      var echartTest = document.getElementById('line' + this.projectId)
      echartTest.style.width = board.offsetWidth - 20 + 'px'
      echartTest.style.height = board.offsetHeight - 60 + 'px'
      this.option.xAxis.data = this.lineData[0].date
      for (let i = 0; i < this.lineData.length; i++) {
        this.option.series.push({name: i, type: 'line', data: [], symbol: 'none'})
        let seriresData = this.option.series[i].data
        for (let j = 0; j < this.lineData[i].deviceNumber.length; j++) {
          seriresData.push({value: this.lineData[i].deviceNumber[j], desc: this.lineData[i].desc})
        }
      }
      var ec = echarts.init(echartTest)
      ec.setOption(this.option)
    }, 100)
  }
}
</script>

<style scoped>
  .story-pie {
    background-color: #fff;
    /*width: 690px;*/
    /*height: 400px;*/
    margin: auto;
  }
</style>
