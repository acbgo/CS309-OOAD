<template>
  <div id="rooms">
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;" @mouseover="hotelOver" @mouseout="hotelOut">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px" @mouseover="contactOver"
             @mouseout="contactOut">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px" @mouseover="loginImgOver"
             @mouseout="loginImgOut">
      </div>
    </div>
    <div class="container">
      <div class="section-header">
        <div>
          <h2>{{ this.hotelName }}</h2>
          <p>地址:{{ this.hotelPosition }}</p>
        </div>
        <div style="display: flex; margin-left: 1100px">
<!--          <el-icon :name="iconName"></el-icon>-->
          <el-button :icon="iconName" @click="favor">收藏</el-button>
          <el-button @click="floor">平面图</el-button>
          <el-button @click="review">查看评价</el-button>
          <el-button @click="admin">管理员</el-button>
        </div>
      </div>
      <div class="row" v-for="(key,index) in data" :key="index">
        <div class="col-md-12 room" v-for="(key1,index1) in data[index]" :key="index1" style="height: 400px;">
          <div class="row">
            <div class="col-md-6">
              <div class="room-img">
                <img :src="_img(index)" alt=""/>
              </div>
            </div>
            <div class="col-md-6">
              <div class="room-des">
                <h3>{{ key1.room_type }}</h3>
                <span style="font-size: 20px; display: block">原价￥{{key1.price}}<span>/ 晚</span></span>
                <span style="font-size: 30px; display: block">现价￥{{key1.price * key1.discount}}<span>/ 晚</span></span>
                <ul class="room-size" style="margin-top: 5px">
                  <li><i class="fa fa-arrow-right"></i>面积: {{ key1.area }} 平方米</li>
                  <li><i class="fa fa-arrow-right"></i>床位: {{ key1.bed_number }} 床</li>
                </ul>
                <ul class="room-icon">
                  <li class="icon-1" v-show="key1.kind/10000000"></li>
                  <li class="icon-2" v-show="key1.kind/1000000%10"></li>
                  <li class="icon-3" v-show="key1.kind/100000%10"></li>
                  <li class="icon-4" v-show="key1.kind/10000%10"></li>
                  <li class="icon-5" v-show="key1.kind/1000%10"></li>
                  <li class="icon-6" v-show="key1.kind/100%10"></li>
                  <li class="icon-7" v-show="key1.kind/10%10"></li>
                  <li class="icon-8" v-show="key1.kind%10"></li>
                </ul>
                <div class="room-link">
                  <a @click="book(key1.price * key1.discount, key1.room_type)">Book Now</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'RoomShow',
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      data: [],
      h_id: '',
      hotelName: '',
      hotelPosition: '',
      dateRange: '',
      iconName: 'el-icon-star-off'
    }
  },
  activated () {
    if (this.$route.params.from === 'reservation' || this.$route.params.from === 'favor') {
      if (sessionStorage.getItem('haveLogin') === 'true') {
        this.HaveLogin = sessionStorage.getItem('haveLogin')
        this.loginUser = sessionStorage.getItem('loginUser')
      }
      this.LoadImg()
      this.h_id = this.$route.params.id
      this.hotelName = this.$route.params.name
      this.hotelPosition = this.$route.params.position
      this.dateRange = this.$route.params.dateRange
      // console.log('date', this.dateRange)
      this.receive()
      this.isFavor()
    }
  },
  mounted () {
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
    this.h_id = this.$route.params.id
    this.hotelName = this.$route.params.name
    this.hotelPosition = this.$route.params.position
    if (this.$route.params.from === 'reservation') {
      this.dateRange = this.$route.params.dateRange
    }
    // console.log('date', this.dateRange)
    this.receive()
    this.isFavor()
  },
  methods: {
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
    _img (index) {
      return require('../../assets/room/' + this.data[index].graphPath)
    },
    receive () {
      // axios.get('http://10.24.168.173:8181/findRooms?h_id=' + this.h_id).then(res => {
      //   this.data = res.data
      //   // console.log(this.data[0].graphPath)
      //   for (let i = 0; i < res.data.length; i++) {
      //     const url = 'http://10.24.168.173:8181/findallroomtypebyR?room_type=' + res.data[i].room_type
      //     axios.get(url).then(res1 => {
      //       this.data[i] = res1.data
      //     })
      //   }
      // })
      // yzd:10.24.168.173 ; wy: 10.24.3.53
      const url = 'http://10.24.3.53:8181/findRooms?h_id=' + this.h_id
      const xmlHttp = new XMLHttpRequest()
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      const myData = xmlHttp.responseText
      this.data = JSON.parse(myData)
      // console.log('1', this.data)
      for (let i = 0; i < JSON.parse(myData).length; i++) {
        const url1 = 'http://10.24.3.53:8181/findallroomtypebyR?room_type=' + this.data[i].room_type
        // console.log(url1)
        const xmlHttp1 = new XMLHttpRequest()
        xmlHttp1.open('POST', url1, false)
        xmlHttp1.send()
        const myData1 = xmlHttp1.responseText
        this.data[i] = JSON.parse(myData1)
        // console.log('2', JSON.parse(myData1))
        this.data[i].graphPath = String(JSON.parse(myData)[i].graphPath)
      }
      // console.log(this.data)
    },
    book (price, roomType) {
      // this.$message.info('进入book界面')
      // console.log('price', price)
      this.$router.push({
        name: 'Book',
        params: {
          hotelName: this.hotelName,
          position: this.hotelPosition,
          dateRange: this.dateRange,
          roomType: roomType,
          price: price,
          h_id: this.h_id,
          from: 'roomShow'
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
    floor () {
      this.$router.push({
        name: 'Floor',
        params: {
          h_id: this.h_id,
          hotelName: this.hotelName,
          hotelPosition: this.hotelPosition,
          dateRange: this.dateRange
        }
      })
    },
    admin () {
      let this_ = this
      this.$axios({
        url: `http://10.24.3.53:8181/isAdmin?username=${this.loginUser}`,
        method: 'POST'
      }).then(function (resp) {
        if (resp.data === 1) {
          // console.log('11111', this_.hotelName)
          sessionStorage.setItem('managerHotelName', this_.hotelName)
          sessionStorage.setItem('managerH_id', this_.h_id)
          this_.$router.push({
            name: 'Manager'
          })
          this_.$message.success('登录成功')
        } else {
          this_.$message.error('您没有管理员权限')
        }
      })
    },
    favor () {
      if (this.iconName === 'el-icon-star-off') {
        this.iconName = 'el-icon-star-on'
        let url = 'http://10.24.3.53:8181/insertFav?username=' + this.loginUser + '&h_id=' + this.h_id
        let xmlHttp = new XMLHttpRequest()
        xmlHttp.open('POST', url, false)
        xmlHttp.send()
        if (xmlHttp.response === '200') {
          this.$message.success('收藏成功')
        } else {
          this.$message.error('未知错误')
          this.iconName = 'el-icon-star-off'
        }
      } else {
        this.iconName = 'el-icon-star-off'
        let url = 'http://10.24.3.53:8181/deleteFav?username=' + this.loginUser + '&h_id=' + this.h_id
        let xmlHttp = new XMLHttpRequest()
        xmlHttp.open('POST', url, false)
        xmlHttp.send()
        if (xmlHttp.response === '200') {
          this.$message.success('取消收藏')
        } else {
          this.$message.error('未知错误')
          this.iconName = 'el-icon-star-on'
        }
      }
    },
    isFavor () {
      let url = 'http://10.24.3.53:8181/isFav?username=' + this.loginUser + '&h_id=' + this.h_id
      let xmlHttp = new XMLHttpRequest()
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      if (xmlHttp.response === '1') {
        // console.log('已收藏')
        this.iconName = 'el-icon-star-on'
      } else {
        // console.log('未收藏')
        this.iconName = 'el-icon-star-off'
      }
    },
    review () {
      this.$router.push({
        name: 'HotelReview',
        params: {
          h_id: this.h_id,
          hotelName: this.hotelName,
          position: this.hotelPosition
        }
      })
    }
  }
}
</script>

<style scoped>
#rooms {
  position: relative;
  padding: 0px 0 0 0;
  width: 100%;
  height: 1%;
}

#rooms .room {
  margin-bottom: 60px;
}

#rooms .room-des {
  position: relative;
  top: 50%;
  transform: translate(20%, -130%);
  width: 800px;
  margin-left: 550px;
  text-align: center;
}

#rooms .room-img {
  position: relative;
  top: 50%;
  transform: translate(-20%, 10%);
  border-radius: 30px;
  overflow: hidden;
}

#rooms .room-img img {
  border-radius: 30px;
  transition: 0.2s;
}

#rooms .room-img:hover img {
  -ms-transform: scale(1.2);
  -webkit-transform: scale(1.2);
  transform: scale(1.1);
}

#rooms .room-des h3 {
  font-size: 24px;
  margin-bottom: 15px;
}

@media (max-width: 768px) {
  #rooms .room-des h3 {
    margin-top: 15px;
  }
}

#rooms .room-des h1 {
  font-size: 45px;
  margin-bottom: 15px;
}

#rooms .room-des h1 span {
  margin-left: 5px;
  font-size: 14px;
  font-weight: 400;
}

#rooms .room-size {
  margin: 0 0 25px 0;
  padding: 0;
}

.section-header {
  position: relative;
  text-align: center;
  margin: 0 auto 45px;
  padding-bottom: 15px;
}

.section-header h2 {
  font-size: 45px;
  font-weight: 700;
  letter-spacing: 1px;
  margin-bottom: 15px;
}

.section-header p {
  margin: 0;
  font-size: 18px;
  font-weight: 300;
  line-height: 25px;
}

#rooms .room-size li {
  display: inline-block;
  font-size: 14px;
  margin-right: 15px;
  list-style-type: none;
}

#rooms .room-size li i {
  margin-right: 6px;
}

#rooms .room-icon {
  margin: 0;
  padding: 0;
  position: relative;
  width: 100%;
}

#rooms .room-icon li {
  list-style-type: none;
  display: inline-block;
  width: 30px;
  height: 30px;
  margin-right: 15px;
  margin-bottom: 15px;
  cursor: pointer;
  background-repeat: no-repeat;
  background-size: contain;
  transition: 0.5s;
}

@media (max-width: 576px) {
  #rooms .room-icon li {
    width: 20px;
    height: 20px;
    margin-right: 10px;
    margin-bottom: 10px;
  }
}

#rooms .room-icon li.icon-1 {
  background-image: url(../../assets/icon/shield.png);
}

#rooms .room-icon li.icon-1:hover {
  background-image: url(../../assets/icon/shield-hover.png);
}

#rooms .room-icon li.icon-2 {
  background-image: url(../../assets/icon/air-conditioner.png);
}

#rooms .room-icon li.icon-2:hover {
  background-image: url(../../assets/icon/air-conditioner-hover.png);
}

#rooms .room-icon li.icon-3 {
  background-image: url(../../assets/icon/bathtub.png);
}

#rooms .room-icon li.icon-3:hover {
  background-image: url(../../assets/icon/bathtub-hover.png);
}

#rooms .room-icon li.icon-4 {
  background-image: url(../../assets/icon/shower.png);
}

#rooms .room-icon li.icon-4:hover {
  background-image: url(../../assets/icon/shower-hover.png);
}

#rooms .room-icon li.icon-5 {
  background-image: url(../../assets/icon/towel.png);
}

#rooms .room-icon li.icon-5:hover {
  background-image: url(../../assets/icon/towel-hover.png);
}

#rooms .room-icon li.icon-6 {
  background-image: url(../../assets/icon/television.png);
}

#rooms .room-icon li.icon-6:hover {
  background-image: url(../../assets/icon/television-hover.png);
}

#rooms .room-icon li.icon-7 {
  background-image: url(../../assets/icon/wifi.png);
}

#rooms .room-icon li.icon-7:hover {
  background-image: url(../../assets/icon/wifi-hover.png);
}

#rooms .room-icon li.icon-8 {
  background-image: url(../../assets/icon/call.png);
}

#rooms .room-icon li.icon-8:hover {
  background-image: url(../../assets/icon/call-hover.png);
}

#rooms .room-link {
  position: relative;
  width: 100%;
  margin-top: 5px;
}

#rooms .room-link a {
  display: inline-block;
  padding: 7px 30px;
  color: #00CED1;
  border-radius: 30px;
  background: #222222;
}

#rooms .room-link a:first-child {
  margin-right: 15px;
}

#rooms .room-link a:hover {
  color: #222222;
  background: #00CED1;
}
</style>
