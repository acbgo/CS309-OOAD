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
    <div class="Show" style="display: block; margin-top: 50px">
      <div>
        <h2>{{ this.hotelName }}</h2>
        <p>地址:{{ this.hotelPosition }}</p>
      </div>
      <el-row v-for="review in reviews" :key="review.id" style="margin-top: 10px;">
        <ItemPage style="background-color: #F5F5F5; display: block; width: 800px; height: 200px; margin-left: 400px">
          <div style="display: flex;">
            <span style="margin-top: 5px; margin-left: 10px; font-size: 18px">{{review.userName}}</span>
            <el-rate v-model="review.overallRate" disabled show-score text-color="#ff9900"
                     style="margin-top: 5px; margin-left: 550px"></el-rate>
          </div>
          <div style="display: block; background-color: #D3D3D3; height: 166px; text-align: left; margin-top: 10px">
            <div class="remark" style="height: 86px">
              <span style="font-size: 25px">{{review.remark}}</span>
            </div>
            <div class="img" style="display: inline">
              <img :src="review.imgUrl" style="height: 80px" v-if="review.isImg">
              <video height="75" controls v-if="review.isMp4">
                <source :src="review.imgUrl" type="video/mp4">
              </video>
<!--              <video :src="review.imgUrl" style="height: 75px" v-if="review.isMp4"></video>-->
<!--              <el-col v-for="img in review.imgs" :key="img">-->
<!--                <img :src="img">-->
<!--              </el-col>-->
            </div>
          </div>
        </ItemPage>
      </el-row>
    </div>
  </div>
</template>

<script>
import ItemPage from '../items/ItemPage.vue'

export default {
  name: 'hotelReview',
  components: {
    ItemPage
  },
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      h_id: 0,
      hotelName: '',
      hotelPosition: '',
      reviews: [
        {
          id: 0,
          userName: 'aaa',
          overallRate: 5,
          remark: 'good!',
          imgUrl: '',
          isMp4: false,
          isImg: true
        },
        {
          id: 1,
          userName: 'bbb',
          overallRate: 4,
          remark: 'bad!',
          imgUrl: '',
          isMp4: false,
          isImg: true
        }
      ]
    }
  },
  mounted () {
    this.h_id = this.$route.params.h_id
    this.hotelName = this.$route.params.hotelName
    this.hotelPosition = this.$route.params.position
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
    this.loadData()
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
    loadData () {
      let url = `http://10.24.3.53:8181/findByHotel?h_id=${this.h_id}`
      let xmlHttp = new XMLHttpRequest()
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      let data = JSON.parse(xmlHttp.response)
      console.log(data)
      this.reviews = []
      for (let i = 0; i < data.length; i++) {
        let temp = {
          id: 0,
          userName: '',
          overallRate: 0,
          remark: '',
          imgUrl: '',
          isMp4: false,
          isImg: true
        }
        temp.id = data[i].id
        temp.userName = data[i].username
        temp.overallRate = data[i].overallRate
        temp.remark = data[i].remark
        if (data[i].url !== null) {
          if (data[i].url.endsWith('mp4')) {
            temp.isMp4 = true
            temp.isImg = false
          } else {
            temp.isMp4 = false
            temp.isImg = true
          }
        } else {
          temp.isMp4 = false
          temp.isImg = false
        }
        let url = encodeURI(data[i].url)
        temp.imgUrl = `http://10.24.3.53:8181/getEva?url=${url}`
        this.reviews.push(temp)
      }
    }
  }
}
</script>

<style scoped>

</style>
