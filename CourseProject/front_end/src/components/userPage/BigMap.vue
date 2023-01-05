<template>
  <div class="Main">
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px">
      </div>
    </div>
    <div class="Head" style="margin-top: 30px">
      <span style="font-size: 25px; font-weight: bold;">点击以查询当前城市所有酒店分店</span>
      <el-input style="position: absolute; left: 1102px; width: 250px; margin-right: 0px" placeholder="输入查询城市" v-model="city">
        <el-button slot="append" icon="el-icon-search" @click="search" onmouseover="this.style.backgroundColor='#4c8df4'" onmouseout="this.style.backgroundColor='#b3d4fc'"></el-button>
      </el-input>
    </div>
    <div class="BigMap" id="gdMap" @click="show"></div>
    <el-button size="mini" style="float: right; margin-right: 183px" @click="initMap">重置</el-button>
  </div>
</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader'

export default {
  name: 'BigMap',
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      hotels: [],
      isChanged: false,
      mapCenter: [100.675927, 32.168572],
      city: ''
    }
  },
  created () {
    this.initMap()
  },
  mounted () {
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
  },
  methods: {
    initMap () {
      let this_ = this
      AMapLoader.load({
        key: '812f0a2e5932119449b4cb59f3474742', // 申请好的Web端开发者Key，首次调用 load 时必填
        version: '2.0', // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [] // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        this.map = new AMap.Map('gdMap', {
          center: this_.mapCenter,
          zoom: 5,
          draggable: true
        })
        let marker1 = new AMap.Marker({
          position: new AMap.LngLat(116.322056, 39.89491), // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          title: '北京'
        })
        let marker2 = new AMap.Marker({
          position: new AMap.LngLat(121.455708, 31.249574), // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          title: '上海'
        })
        let marker3 = new AMap.Marker({
          position: new AMap.LngLat(114.057939, 22.543527), // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          title: '深圳'
        })
        let markerList = [marker1, marker2, marker3]
        this.map.add(markerList)
      }).catch(e => {
        console.log(e)
      })
    },
    LoadImg () {
      if (this.HaveLogin === 'true') {
        this.loginImg = require('../../assets/svgs/login1.svg')
      } else {
        this.loginImg = require('../../assets/svgs/login0.svg')
      }
    },
    Home () {
      this.$router.push({name: 'MainPage'})
    },
    ContactUs () {
      this.$router.push({name: 'ContactUs'})
    },
    LoginMethods () {
      if (this.HaveLogin === 'true') {
        this.$router.push({
          name: 'Personal'
        })
      } else {
        this.$router.push({name: 'Login'})
      }
    },
    show () {
      // console.log(this)
      let this_ = this
      let q = document.getElementById('gdMap')
      q.onclick = function (e) {
        let X = e.x
        let Y = e.y
        console.log('x: ', X, 'y: ', Y)
        if (X > 1142 && X < 1172 && Y > 180 && Y < 220) {
          // console.log('beijing')
          this_.searchBJ()
        }
        if (X > 1262 && X < 1284 && Y > 425 && Y < 465) {
          // console.log('shanghai')
          this_.searchSH()
        }
        if (X > 1090 && X < 1120 && Y > 645 && Y < 685) {
          // console.log('shenzen')
          this_.searchSZ()
        }
      }
    },
    search () {
      if (this.city === '北京') {
        this.searchBJ()
        this.city = ''
      } else if (this.city === '上海') {
        this.searchSH()
        this.city = ''
      } else if (this.city === '深圳') {
        this.searchSZ()
        this.city = ''
      } else {
        this.$message.error('您所搜索的城市没有我们的分店')
        this.city = ''
      }
    },
    searchBJ () {
      let url = 'http://10.24.3.53:8181/findHotelsByCity?city=BeiJing'
      let xmlHttp = new XMLHttpRequest()
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      let myData = xmlHttp.responseText
      // console.log('bigMap', JSON.parse(myData))
      if (myData.length !== 0) {
        this.hotels = JSON.parse(myData)
        // console.log('first', this.hotels)
        window.sessionStorage.setItem('hotels', myData)
        this.isChanged = true
        if (this.isChanged) {
          // console.log('4', JSON.parse(window.sessionStorage.getItem('hotels')))
          if (window.sessionStorage.getItem('hotels') !== null) {
            this.$router.push({name: 'Reservation', params: {from: 'bigMap'}})
          }
        }
      }
    },
    searchSH () {
      let url1 = 'http://10.24.3.53:8181/findHotelsByCity?city=Shanghai'
      let xmlHttp1 = new XMLHttpRequest()
      xmlHttp1.open('POST', url1, false)
      xmlHttp1.send()
      let myData1 = xmlHttp1.responseText
      if (myData1.length !== 0) {
        this.hotels = JSON.parse(myData1)
        // console.log('second', this.hotels)
        window.sessionStorage.setItem('hotels', myData1)
        this.isChanged = true
        if (this.isChanged) {
          // console.log('4', JSON.parse(window.sessionStorage.getItem('hotels')))
          if (window.sessionStorage.getItem('hotels') !== null) {
            this.$router.push({name: 'Reservation', params: {from: 'bigMap'}})
            this.$router.go(0)
          }
        }
      }
    },
    searchSZ () {
      let url2 = 'http://10.24.3.53:8181/findHotelsByCity?city=ShenZen'
      let xmlHttp2 = new XMLHttpRequest()
      xmlHttp2.open('POST', url2, false)
      xmlHttp2.send()
      let myData2 = xmlHttp2.responseText
      if (myData2.length !== 0) {
        this.hotels = JSON.parse(myData2)
        // console.log('3', this.hotels)
        window.sessionStorage.setItem('hotels', myData2)
        this.isChanged = true
        if (this.isChanged) {
          // console.log('4', JSON.parse(window.sessionStorage.getItem('hotels')))
          if (window.sessionStorage.getItem('hotels') !== null) {
            this.$router.push({name: 'Reservation', params: {from: 'bigMap', date: `${new Date().getTime()}`}})
            this.$router.go(0)
          }
        }
      }
    }
  }
}
</script>

<style scoped>
.BigMap {
  margin-top: 20px;
  margin-left: 250px;
  height: 600px;
  width: 1100px;
  border: 1px solid cornflowerblue;
  background-color: #4c8df4;
}
</style>
