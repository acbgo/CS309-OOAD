<template>
  <div class="Daily" id="daily" style="display: flex">
    <div class="Order" id="order"></div>
    <div class="Turnover" id="turnover"></div>
  </div>

</template>

<script>
export default {
  name: 'DailyAnalysis',
  data () {
    return {
      h_id: 0,
      ordersX: [],
      ordersY: [],
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
      let option = {
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'line',
            smooth: true
          }
        ]
      }
      let data = this.getData(`http://10.24.3.53:8181/numberByDate?h_id=${this.h_id}`)
      data = JSON.parse(data)
      console.log(data)
      option.xAxis.data = []
      option.series[0].data = []
      for (let i = 0; i < data.length; i++) {
        option.xAxis.data.push(data[i].date)
        option.series[0].data.push(parseInt(data[i].number))
      }
      order.setOption(option)
    },
    setTurnover () {
      let turnover = this.$echarts.init(document.getElementById('turnover'))
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: 'Direct',
            type: 'bar',
            barWidth: '60%',
            data: [10, 52, 200, 334, 390, 330, 220]
          }
        ]
      }
      let data = this.getData(`http://10.24.3.53:8181/priceByDate?h_id=${this.h_id}`)
      data = JSON.parse(data)
      option.xAxis[0].data = []
      option.series[0].data = []
      for (let i = 0; i < data.length; i++) {
        option.xAxis[0].data.push(data[i].date)
        option.series[0].data.push(parseInt(data[i].price))
      }
      turnover.setOption(option)
    }
  }
}
</script>

<style scoped>
.Daily {
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
