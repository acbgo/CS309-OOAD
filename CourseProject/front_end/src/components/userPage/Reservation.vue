<template>
  <div class="Main">
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;" @mouseover="hotelOver" @mouseout="hotelOut">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px" @mouseover="contactOver"
             @mouseout="contactOut">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px" @mouseover="loginImgOver"
             @mouseout="loginImgOut">
      </div>
    </div>
    <div class="Select" style="margin-top: 0px">
      <div class="Box" style="display: flex; justify-content: center;">
        <div class="Destination">
          <label>目的地</label>
          <div>
            <el-cascader v-model="destination"
                         placeholder="destination"
                         :options="destinations"
                         filterable
            >
            </el-cascader>
          </div>
        </div>
        <div class="DateRange" style="margin-left: 20px; display: block">
          <span style="display: block">入住时间</span>
          <el-date-picker
            value-format="yyyy-MM-dd HH:mm:ss"
            v-model="dateRange"
            :picker-options="setRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </div>
        <div class="Number" style="margin-left: 20px">
          <span style="display: block">入住人数</span>
          <div>
            <el-select v-model="number" placeholder="Number of occupancy">
              <el-option v-for="item in numbers"
                         :key="item.label"
                         :value="item.label"/>
            </el-select>
          </div>
        </div>
        <div class="KeyWord" style="margin-left: 20px">
          <span style="display: block">关键词</span>
          <div>
            <input v-model="keyWord"
                   style="height: 36px; width: 180px; border: #666666; border-radius: 4px; margin-top: 1px"
                   placeholder="    酒店名/地标">
          </div>
        </div>
      </div>
      <div class="Check" style="margin-top: 20px">
        <div class="Grand" style="display: flex">
          <span style="margin-left: 450px">星级</span>
          <el-checkbox-group v-model="grade" style="margin-left: 20px" :max="1">
            <el-checkbox v-for="grade in grades" :label="grade.value" :key="grade.value">{{ grade.label }}</el-checkbox>
          </el-checkbox-group>
        </div>
        <hr width="680px" color="black" size="1px">
        <div class="price">
          <span style="margin-left: -5px">价格</span>
          <el-radio-group v-model="price" style="margin-left: 16px">
            <el-radio v-for="price in prices" :label="price.label" :key="price.value">{{ price.value }}</el-radio>
          </el-radio-group>
        </div>
      </div>
      <div class="Button" style="margin-left: 919px; margin-top: -50px" @mouseenter="searchBtnOver"
           @mouseleave="searchBtnOut">
        <el-button :style="searchBtnStyle"
                   @click="search">搜索
        </el-button>
      </div>
    </div>
    <div class="Show" style="display: flex">
      <div class="Left" style="margin-left: 249px">
        <el-row v-for="hotel in hotels" :key="hotel.id" style="margin-top: 10px">
          <ItemPage style="background-color: #F0FFFF; display: flex">
            <div class="HotelImg">
              <img :src="imgLoad(hotel.index)" style="width: 200px">
            </div>
            <div class="Detail" style="display: block;width: 370px; margin-left: 30px" @click="setMap(hotel)">
              <div class="name" style="margin-top: 20px; height: 40px">
                <span style="float: left; font-size: 30px; font-weight: bold;">{{ hotel.name }}</span>
              </div>
              <div class="position" style="height: 40px; margin-top: 20px">
                <span style="display: block; text-align: left">地址:{{ hotel.position }}</span>
                <br>
                <span style="display: block; text-align: left">详情: {{ hotel.msg }}</span>
              </div>
            </div>
            <div class="ItemRight">
              <el-rate v-model="hotel.rate" disabled show-score text-color="#ff9900"
                       style="margin-top: 20px; margin-left: 40px"></el-rate>
              <div class="price" style="margin-top: 70px; margin-left: 90px; ">
                <span style="display: inline; color: red; font-size: 13px">￥</span>
                <span style="display: inline; color: red; font-size: 25px">{{hotel.price}}</span>
                <span style="display: inline">起</span>
              </div>
              <el-button
                style="display: block; margin-left: 100px; margin-top: 5px; color: #4c8df4; border: 1px solid #4c8df4; border-radius: 5px;"
                @click="roomShow(hotel.id, hotel.name, hotel.position)">
                查看详情
              </el-button>
            </div>
          </ItemPage>
        </el-row>
      </div>
      <div class="Right">
        <div class="Map" id="gdMap" style="margin-left: 20px; margin-top: 20px"></div>
        <div @mouseenter="mapBtnOver" @mouseleave="mapBtnOut">
          <el-button :style="mapBtnStyle" @click="showBigMap">查看全国分店</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ItemPage from '../items/ItemPage.vue'
import AMapLoader from '@amap/amap-jsapi-loader'

export default {
  name: 'Reservation',
  activated () {
    if (this.$route.params.from === 'bigMap') {
      if (window.sessionStorage.getItem('hotels') !== null) {
        let hotels = JSON.parse(window.sessionStorage.getItem('hotels'))
        console.log('from map, res', hotels)
        this.hotels = []
        for (let i = 0; i < hotels.length; i++) {
          let temp = {
            id: 0,
            name: '',
            position: '',
            msg: '',
            rate: 0,
            price: 0,
            lngLat: null,
            index: ''
          }
          temp.id = hotels[i].id
          temp.name = hotels[i].name
          temp.position = hotels[i].location
          temp.msg = hotels[i].remark
          temp.rate = hotels[i].score
          let img = hotels[i].graphPath
          let index = img.charAt(5)
          temp.index = index
          this.$axios({
            method: 'POST',
            url: 'http://10.24.3.53:8181/findLow',
            params: {h_id: temp.id}
          }).then(function (resp) {
            temp.price = resp.data
          })
          console.log('activaed', temp.price)
          // console.log(temp.imgUrl)
          let lngLatTemp = hotels[i].lngLat
          lngLatTemp = `${lngLatTemp}`.split(',')
          let lngLat = [parseFloat(lngLatTemp[0]), parseFloat(lngLatTemp[1])]
          temp.lngLat = lngLat
          console.log(temp)
          this.hotels.push(temp)
        }
      }
    } else if (this.$route.params.from === 'main') {
      this.loadData()
    }
    this.initMap()
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
  },
  mounted () {
    this.init = true
    if (this.$route.params.from === 'main') {
      this.loadData()
    } else {
      if (window.sessionStorage.getItem('hotels') !== null) {
        let hotels = JSON.parse(window.sessionStorage.getItem('hotels'))
        console.log('from map, res', hotels)
        this.hotels = []
        for (let i = 0; i < hotels.length; i++) {
          let temp = {
            id: 0,
            name: '',
            position: '',
            msg: '',
            rate: 0,
            price: 0,
            lngLat: null,
            index: 0
          }
          temp.id = hotels[i].id
          temp.name = hotels[i].name
          temp.position = hotels[i].location
          temp.msg = hotels[i].remark
          temp.rate = hotels[i].score
          let img = hotels[i].graphPath
          let index = img.charAt(5)
          temp.index = index
          this.imgLoad(index)
          this.$axios({
            method: 'POST',
            url: 'http://10.24.3.53:8181/findLow',
            params: {h_id: temp.id}
          }).then(function (resp) {
            temp.price = resp.data
          })
          let lngLatTemp = hotels[i].lngLat
          lngLatTemp = `${lngLatTemp}`.split(',')
          let lngLat = [parseFloat(lngLatTemp[0]), parseFloat(lngLatTemp[1])]
          temp.lngLat = lngLat
          console.log(temp)
          this.hotels.push(temp)
        }
      }
    }
    this.initMap()
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
  },
  components: {
    ItemPage
  },
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      searchBtnStyle: 'width: 120px; border-radius: 10px; background-color: #AFEEEE; color: #222222; font-size: 15px',
      mapBtnStyle: 'margin-top: 20px; background-color: #AFEEEE; color: #222222; border-radius: 10px;',
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      isRouterAlive: true,
      destination: '',
      dateRange: new Date(),
      number: null,
      keyWord: '',
      grade: [],
      grades: [
        {label: '不限', value: 0},
        {label: '经济型', value: 1},
        {label: '舒适型', value: 2},
        {label: '高档型', value: 3},
        {label: '豪华型', value: 4},
        {label: '别墅型', value: 5}
      ],
      price: '',
      prices: [
        {label: '0-1000000', value: '不限'},
        {label: '0-200', value: '200以下'},
        {label: '200-400', value: '200-400'},
        {label: '400-600', value: '400-600'},
        {label: '600-800', value: '600-800'},
        {label: '800-2000', value: '800以上'}
      ],
      destinations: [
        {
          label: '深圳',
          value: 'ShenZen',
          children: [
            {label: '南山', value: 'NanShan'},
            {label: '宝安', value: 'BaoAn'},
            {label: '龙岗', value: 'LongGang'},
            {label: '龙华', value: 'LongHua'}
          ]
        },
        {
          label: '北京',
          value: 'BeiJing',
          children: [
            {label: '东城', value: 'DongCheng'},
            {label: '西城', value: 'XiCheng'},
            {label: '崇文', value: 'ChongWen'},
            {label: '朝阳', value: 'ChaoYang'}
          ]
        },
        {
          label: '上海',
          value: 'Shanghai',
          children: [
            {label: '黄浦', value: 'HuangPu'},
            {label: '浦东', value: 'PuDong'},
            {label: '杨浦', value: 'YangPu'},
            {label: '虹口', value: 'HongKou'},
            {label: '松江', value: 'SongJiang'}
          ]
        }
      ],
      numbers: [
        {label: 1, value: 1},
        {label: 2, value: 2},
        {label: 3, value: 3},
        {label: 4, value: 4},
        {label: 5, value: 5}
      ],
      hotels: [],
      map: null,
      setRange: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        }
      }
    }
  },
  methods: {
    loadData () {
      let grade = this.$route.params.grade * 1
      let destination = this.$route.params.destination
      let dateRange = this.$route.params.dateRange
      this.destination = destination
      this.dateRange = dateRange
      // this.$message.info(this.destination)
      this.loadGrade(grade)
      let url = 'http://10.24.3.53:8181/findHotels?grade=' + grade + '&city=' + destination[0] + '&district=' + destination[1]
      let xmlHttp = new XMLHttpRequest()
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      let data = xmlHttp.responseText
      this.hotels = []
      // console.log(data)
      if (data !== '[]') {
        // this.$message.info(data.length)
        data = JSON.parse(data)
        console.log(data)
        this.hotels = []
        for (let i = 0; i < data.length; i++) {
          let temp = {
            id: 0,
            name: '',
            position: '',
            msg: '',
            rate: 0,
            lngLat: [],
            price: 0,
            index: ''
          }
          temp.id = data[i].id
          temp.name = data[i].name
          temp.position = `${data[i].location}`
          temp.msg = data[i].remark
          temp.rate = data[i].score
          let img = data[i].graphPath
          let index = img.charAt(5)
          temp.index = index
          console.log(temp.imgUrl)
          this.$axios({
            method: 'POST',
            url: 'http://10.24.3.53:8181/findLow',
            params: {h_id: temp.id}
          }).then(function (resp) {
            temp.price = resp.data
          })
          let lngLatTemp = data[i].lngLat
          lngLatTemp = `${lngLatTemp}`.split(',')
          let lngLat = [parseFloat(lngLatTemp[0]), parseFloat(lngLatTemp[1])]
          temp.lngLat = lngLat
          this.hotels.push(temp)
        }
      } else {
        this.$message.error('不存在符合条件的酒店')
      }
    },
    loadGrade (grade) {
      if (grade === '0') {
        this.grade = this.grades[0]
      } else if (grade === '1') {
        this.grade = this.grades[1]
      } else if (grade === '2') {
        this.grade = this.grades[2]
      } else if (grade === '3') {
        this.grade = this.grades[3]
      } else if (grade === '4') {
        this.grade = this.grades[4]
      } else if (grade === '5') {
        this.grade = this.grades[5]
      }
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
    initMap () {
      let this_ = this
      AMapLoader.load({
        key: '812f0a2e5932119449b4cb59f3474742', // 申请好的Web端开发者Key，首次调用 load 时必填
        version: '2.0', // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [] // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        this.map = new AMap.Map('gdMap', {
          center: this_.hotels[0].lngLat,
          zoom: 15,
          draggable: true
        })
        let marker = new AMap.Marker({
          position: this_.hotels[0].lngLat, // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          title: '南科大'
        })
        this.map.add(marker)
      }).catch(e => {
        console.log(e)
      })
    },
    setMap (hotel) {
      AMapLoader.load({
        key: '812f0a2e5932119449b4cb59f3474742', // 申请好的Web端开发者Key，首次调用 load 时必填
        version: '2.0', // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [] // 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        this.map = new AMap.Map('gdMap', {
          center: hotel.lngLat,
          zoom: 15,
          draggable: true
        })
        let marker = new AMap.Marker({
          position: new AMap.LngLat(hotel.lngLat[0], hotel.lngLat[1]), // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          title: '南科大'
        })
        this.map.add(marker)
      }).catch(e => {
        console.log(e)
      })
    },
    showBigMap () {
      this.$router.push({name: 'BigMap'})
    },
    search () {
      // this.$message.info('点击了搜索')
      let priceRange = this.price.split('-')
      // console.log(priceRange)
      let xmlHttp = new XMLHttpRequest()
      let url = `http://10.24.3.53:8181/findByPrice?grade=${this.grade[0]}&city=${this.destination[0]}&district=${this.destination[1]}&low=${priceRange[0]}&high=${priceRange[1]}`
      console.log(url)
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      let data = JSON.parse(xmlHttp.response)
      console.log(data)
      this.hotels = []
      if (data.length !== 0) {
        for (let i = 0; i < data.length; i++) {
          let dataTemp = data[i]
          let temp = {
            id: 0,
            name: '',
            position: '',
            msg: '',
            rate: 0,
            lngLat: [],
            price: 0,
            index: ''
          }
          temp.id = dataTemp.id
          temp.name = dataTemp.name
          temp.position = `${dataTemp.location}`
          temp.msg = dataTemp.remark
          temp.rate = dataTemp.score
          let img = dataTemp.graphPath
          let index = img.charAt(5)
          temp.index = index
          this.imgLoad(index)
          let lngLatTemp = dataTemp.lngLat.split(',')
          temp.lngLat = [parseFloat(lngLatTemp[0]), parseFloat(lngLatTemp[1])]
          temp.price = dataTemp.price
          this.hotels.push(temp)
        }
        console.log(this.hotels)
        this.setMap(this.hotels[0])
      } else {
        this.$message.error('不存在符合条件的酒店')
      }
    },
    roomShow (id, name, position) {
      this.$router.push({
        name: 'roomShow',
        params: {
          id: id,
          name: name,
          position: position,
          dateRange: this.dateRange,
          from: 'reservation'
        }
      })
    },
    hotelOver () {
      this.hotel = require('../../assets/svgs/hotel-hover.svg')
    },
    hotelOut () {
      this.hotel = require('../../assets/svgs/hotel.svg')
    },
    contactOver () {
      this.contact = require('../../assets/svgs/contact-hover.svg')
    },
    contactOut () {
      this.contact = require('../../assets/svgs/contact.svg')
    },
    loginImgOver () {
      if (this.HaveLogin) {
        this.loginImg = require('../../assets/svgs/login1-hover.svg')
      } else {
        this.loginImg = require('../../assets/svgs/login0-hover.svg')
      }
    },
    loginImgOut () {
      if (this.HaveLogin) {
        this.loginImg = require('../../assets/svgs/login1.svg')
      } else {
        this.loginImg = require('../../assets/svgs/login0.svg')
      }
    },
    searchBtnOver () {
      this.searchBtnStyle = 'width: 120px; border-radius: 10px; background-color: #7AC5CD; color: #222222; font-size: 15px'
    },
    searchBtnOut () {
      this.searchBtnStyle = 'width: 120px; border-radius: 10px; background-color: #AFEEEE; color: #222222; font-size: 15px'
    },
    mapBtnOver () {
      this.mapBtnStyle = 'margin-top: 20px; background-color: #00CED1; color: #222222; border-radius: 10px;'
    },
    mapBtnOut () {
      this.mapBtnStyle = 'margin-top: 20px; background-color: #AFEEEE; color: #222222; border-radius: 10px;'
    },
    imgLoad (index) {
      return require('../../assets/hotels/img' + index + '.png')
    }
  }
}
</script>

<style scoped>
.Select {
  background: rgba(131, 139, 139, 0.3);
  margin-top: 21px;
  padding-top: 30px;
  height: 158px;
}

.Map {
  height: 400px;
  width: 340px;
  border: 1px solid cornflowerblue;
  background-color: #4c8df4;
}
</style>
