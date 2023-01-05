<template>
  <div class="Type" id="type" style="display: flex">
    <div class="Order" id="order"></div>
    <div class="Turnover" id="turnover"></div>
  </div>
</template>

<script>
export default {
  name: 'TypeAnalysis',
  data () {
    return {
      h_id: 0,
      orders: [],
      turnover: []
    }
  },
  mounted () {
    this.h_id = sessionStorage.getItem('managerH_id')
    this.setOrder()
    this.setTurnover()
  },
  methods: {
    getData (url) {
      let xmlHttp = new XMLHttpRequest()
      xmlHttp.open('GET', url, false)
      xmlHttp.send()
      return xmlHttp.response
    },
    setOrder () {
      let order = this.$echarts.init(document.getElementById('order'))
      // prettier-ignore
      let dataAxis = ['点', '击', '柱', '子', '或', '者', '两', '指', '在', '触', '屏', '上', '滑', '动', '能', '够', '自', '动', '缩', '放']
      // prettier-ignore
      let data = [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220]
      let yMax = 500
      let dataShadow = []
      let respData = this.getData(`http://10.24.3.53:8181/numberByRoomType?h_id=${this.h_id}`)
      respData = JSON.parse(respData)
      console.log(respData)
      dataAxis = []
      data = []
      for (let i = 0; i < respData.length; i++) {
        dataAxis.push(respData[i].date)
        data.push(respData[i].number)
      }
      for (let i = 0; i < data.length; i++) {
        dataShadow.push(yMax)
      }
      let option = {
        title: {
          text: '订单数量'
        },
        xAxis: {
          data: dataAxis,
          axisLabel: {
            inside: true,
            color: '#fff'
          },
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          z: 10
        },
        yAxis: {
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            color: '#999'
          }
        },
        dataZoom: [
          {
            type: 'inside'
          }
        ],
        series: [
          {
            type: 'bar',
            showBackground: true,
            itemStyle: {
              color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: '#83bff6'},
                {offset: 0.5, color: '#188df0'},
                {offset: 1, color: '#188df0'}
              ])
            },
            emphasis: {
              itemStyle: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: '#2378f7'},
                  {offset: 0.7, color: '#2378f7'},
                  {offset: 1, color: '#83bff6'}
                ])
              }
            },
            data: data
          }
        ]
      }
      // Enable data zoom when user click bar.
      const zoomSize = 6
      order.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)])
        order.dispatchAction({
          type: 'dataZoom',
          startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
          endValue:
            dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        })
      })
      // option.xAxis.data = []
      // option.series[0].data = []
      order.setOption(option)
    },
    setTurnover () {
      let turnover = this.$echarts.init(document.getElementById('turnover'))
      let option = {
        title: {
          text: '营业额'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Email']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '营业额',
            type: 'line',
            stack: 'Total',
            data: [120, 132, 101, 134, 90, 230, 210]
          }
        ]
      }
      let respData = this.getData(`http://10.24.3.53:8181/priceByRoomType?h_id=${this.h_id}`)
      respData = JSON.parse(respData)
      console.log(respData)
      option.xAxis.data = []
      option.series[0].data = []
      for (let i = 0; i < respData.length; i++) {
        option.xAxis.data.push(respData[i].room_type)
        option.series[0].data.push(respData[i].price)
      }
      turnover.setOption(option)
    }
  }
}
</script>

<style scoped>
.Type {
  height: 100%;
  width: 100%;
}

.Order {
  height: 100%;
  width: 100%;
}

.Turnover {
  height: 100%;
  width: 100%;
}
</style>
