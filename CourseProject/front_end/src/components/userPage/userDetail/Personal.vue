<template>
  <div class="PersonSum">
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;" @mouseover="hotelOver" @mouseout="hotelOut">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px" @mouseover="contactOver"
             @mouseout="contactOut">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px" @mouseover="loginImgOver"
             @mouseout="loginImgOut">
      </div>
    </div>
    <div class="PersonTop">
      <div class="PersonTop_img">
        <img class="img" :src="imageUrl"  alt=""/>
      </div>
      <div class="PersonTop_text">
        <div class="user_text">
          <div class="user_name" style="width: 170px">
            <span style="float: top"> {{ tableData.name }} </span>
          </div>
          <div class="user-v">
            <img src="../../../assets/logo/logo1.png" class="user-v-img"  alt=""/>
            <span class="user-v-font"> {{tableData.grant}}</span>
          </div>
          <div class="user_qianming">
            <span> {{ tableData.remark }}</span>
          </div>
          <div class="Points">
            <img src="../../../assets/imgs/mall/point.jpg" class="user-v-img"  alt=""/>
            <span class="user-v-font">{{tableData.points}}</span>
          </div>
        </div>
        <div>
          <el-button style="height: 50px; width: 100px; margin-left: 700px; margin-top: 50px" type="primary" @click="logout">退出登录</el-button>
        </div>
      </div>
    </div>
    <div class="person_body">
      <div class="person_body_left">
        <el-card class="box-card" :body-style="{ padding: '0px' }">
          <div slot="header" class="clearfix">
            <span class="person_body_list" style="border-bottom: none"
            >个人中心</span
            >
          </div>
          <el-menu
            router
            active-text-color="#00c3ff"
            class="el-menu-vertical-demo"
          >
            <el-menu-item
              index="info"
              :route="{ name: 'info'}"
            >
              <i class="el-icon-user"></i>
              <span slot="title">个人简介</span>
            </el-menu-item>
            <el-menu-item
              index="favor"
            >
              <i class="el-icon-document"></i>
              <span slot="title">收藏</span>
            </el-menu-item>
            <el-menu-item
              index="individualOrders"
            >
              <i class="el-icon-document"></i>
              <span slot="title">订单</span>
            </el-menu-item>
            <el-menu-item
              index="mall"
            >
              <i class="el-icon-tableware"></i>
              <span slot="title">积分商城</span>
            </el-menu-item>
            <el-menu-item
              index="changePwd"
            >
              <i class="el-icon-setting"></i>
              <span slot="title">修改密码</span>
            </el-menu-item>
          </el-menu>
        </el-card>
      </div>
      <div class="person_body_right">
        <router-view v-if="isRouterAlive"></router-view>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'

export default {
  name: 'Personal',
  provide () {
    return {
      reload1: this.reload1
    }
  },
  data () {
    return {
      imageUrl: '',
      tableData: {
        name: 'abc',
        remark: 'nbnbnb',
        grant: '',
        points: 99
      },
      userName: '',
      isRouterAlive: true,
      hotel: require('../../../assets/svgs/hotel.svg'),
      contact: require('../../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false
    }
  },
  mounted () {
    this.userName = sessionStorage.getItem('loginUser')
    this.imageUrl = `http://10.24.3.53:8181/getUser?username=${this.userName}`
    let url = `http://10.24.3.53:8181/getUserInfo?username=${this.userName}`
    axios.get(url).then(res => {
      this.tableData = res.data[0]
    })
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
  },
  methods: {
    reload1 () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
      console.log(1231164646)
    },
    logout () {
      sessionStorage.setItem('haveLogin', 'false')
      sessionStorage.setItem('loginUser', '')
      this.$message.success('退出成功')
      this.$router.push({
        name: 'MainPage'
      })
      this.$router.go(0)
    },
    LoadImg () {
      if (this.HaveLogin === 'true') {
        this.loginImg = require('../../../assets/svgs/login1.svg')
      } else {
        this.loginImg = require('../../../assets/svgs/login0.svg')
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
      this.hotel = require('../../../assets/svgs/hotel-hover.svg')
    },
    hotelOut () {
      this.hotel = require('../../../assets/svgs/hotel.svg')
    },
    contactOver () {
      this.contact = require('../../../assets/svgs/contact-hover.svg')
    },
    contactOut () {
      this.contact = require('../../../assets/svgs/contact.svg')
    },
    loginImgOver () {
      if (this.HaveLogin === 'true') {
        this.loginImg = require('../../../assets/svgs/login1-hover.svg')
      } else {
        this.loginImg = require('../../../assets/svgs/login0-hover.svg')
      }
    },
    loginImgOut () {
      if (this.HaveLogin) {
        this.loginImg = require('../../../assets/svgs/login1.svg')
      } else {
        this.loginImg = require('../../../assets/svgs/login0.svg')
      }
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.PersonSum {
  background:url("../../../assets/imgs/mall/bg.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

.me-video-player {
  background-color: transparent;
  width: 100%;
  height: 100%;
  object-fit: fill;
  display: block;
  position: fixed;
  left: 0;
  z-index: 0;
  top: 0;
}
.PersonTop {
  width: 1000px;
  height: 140px;
  padding-top: 20px;
  background-color: #55585a+7a;
  margin-top: 30px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  border-radius: 5px;
}

.PersonTop_img {
  width: 150px;
  height: 120px;
  background-color: #8c939d;
  margin-right: 0px;
  margin-left: 20px;
  overflow: hidden;
  border-radius: 20px;
}

.PersonTop_img img {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.PersonTop_text {
  height: 120px;
  width: 250px;
  display: flex;
}

.user_text {
  width: 60%;
  height: 100%;
  line-height: 30px;
}

.user_name {
  font-weight: bold;
}
.user-v {
  margin-bottom: -5px;
}
.user-v-img {
  width: 15px;
  height: 15px;
}
.user-v-font {
  font-size: 15px;
  color: #00c3ff;
}
.user_qianming {
  font-size: 14px;
  color: #999;
}

.user_num {
  width: 40%;
  height: 100%;
  display: flex;
  align-items: center;
}

.user_num > div {
  text-align: center;
  border-right: 1px dotted #999;
  box-sizing: border-box;
  width: 80px;
  height: 40px;
  line-height: 20px;
}

.num_text {
  color: #999;
}

.num_number {
  font-size: 20px;
  color: #333;
}
.el-menu-item>span {
  font-size: 16px;
  color: #999;
}

/*下面部分样式*/
.person_body {
  width: 1250px;
  margin-top: 210px;
  display: flex;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 5px;
}

.person_body_left {
  width: 18%;
  height: 600px;
  border-radius: 5px;
  margin-right: 3%;
  text-align: center;
}

.person_body_list {
  width: 100%;
  height: 50px;
  margin-top: 25px;
  font-size: 22px;
  border-bottom: 1px solid #f0f0f0;
  background-image: -webkit-linear-gradient(
    left,
    rgb(42, 134, 141),
    #e9e625dc 20%,
    #3498db 40%,
    #e74c3c 60%,
    #09ff009a 80%,
    rgba(82, 196, 204, 0.281) 100%
  );
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  -webkit-background-size: 200% 100%;
  -webkit-animation: masked-animation 4s linear infinite;
}

.el-menu-item {
  margin-top: 22px;
}

.person_body_right {
  width: 80%;
  /* height: 500px; */
  border-radius: 5px;
  background-color: white;
}

.box-card {
  height: 500px;
}

/*ui样式*/
.el-button {
  width: 84px;
}
</style>
