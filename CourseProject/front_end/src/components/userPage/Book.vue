<template>
  <div style="background:#eeeeee;font-size:20px;">
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;" @mouseover="hotelOver" @mouseout="hotelOut">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px" @mouseover="contactOver"
             @mouseout="contactOut">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px" @mouseover="loginImgOver"
             @mouseout="loginImgOut">
      </div>
    </div>
    <el-container style="margin-left: 70px;margin-right: 80px">
      <el-main>
        <el-card class="el-card" style="height: 270px">
          <p style="font-size: 30px;margin-top:0px;font-weight: bold">{{ hotelName }}</p>
          <span style="display: block; margin-top: -20px; font-size: 15px">详细地址: {{ hotelAddress }}</span>
          <p style="font-size: 15px; display: block">{{ roomType }} | 公共卫浴 | {{ bedNum }}张床位</p>
          <span>订房需知</span>
          <p style="font-size: 15px"><i class="el-icon-warning">该价格仅适用于持大陆身份证的中国籍客人</i></p>
          <p style="font-size: 15px"><i class="el-icon-warning">该房型不允许携带儿童入住。</i></p>
          <p style="font-size: 15px"><i class="el-icon-warning">共用浴室、共用卫生间</i></p>
        </el-card>
        <el-card style="height: 370px">
          <p style="margin-top: 0;font-size: 15px">租住日期
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </p>
          <p style="font-size: 15px">房间数
            <el-select v-model="roomNum">
              <el-option label="1" value="1"></el-option>
              <el-option label="2" value="2"></el-option>
              <el-option label="3" value="3"></el-option>
              <el-option label="4" value="4"></el-option>
              <el-option label="5" value="5"></el-option>
              <el-option label="6" value="6"></el-option>
            </el-select>
          </p>

          <span style="font-size: 20px;font-family: 黑体;justify-content: left; display: block">住客资料</span>
          <span style="color: gray;font-size: 15px; display: block; margin-top: 5px">请按实际入住人数填写，姓名和证件保持一致<i
            class="el-icon-warning"></i>
          </span>
          <el-form :model="userForm" :rules="rules" ref="user-form" style="margin-top: 10px; text-align: center">
            <el-form-item label="住客姓名" prop="userName" style="margin-left: 200px">
              <el-input style="width: 200px; margin-left: -230px" v-model="userForm.userName"></el-input>
            </el-form-item>
            <el-form-item label="联系电话" prop="phoneNum" style="margin-left: 200px">
              <el-input style="width: 200px; margin-left: -230px" maxlength="11" minlength="11"
                        v-model="userForm.phoneNum"></el-input>
            </el-form-item>
            <el-form-item label="联系邮箱" prop="email" style="margin-left: 200px">
              <el-input style="width: 200px; margin-left: -230px" v-model="userForm.email"></el-input>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="height: 200px">
          <p style="font-size: 20px;margin-top: 0px">预计到店</p>
          <!--          <p style="font-size: 15px">到达时间</p>-->
          <p>
            <el-time-select
              v-model="time"
              :picker-options="{
            start: '0:00',
            step: '00:30',
            end: '23:30'
            }"
              placeholder="选择时间">
            </el-time-select>
          </p>
          <p style="font-size: 15px">房间将会整晚保留</p>
        </el-card>
        <el-card style="height: 130px">
          <p style="margin-top: 0px">特别要求</p>
          <p>
            <el-input v-model="requirement" placeholder="我们会将您的需求转达给酒店，但不能保证一定满足。"></el-input>
          </p>
        </el-card>
        <el-card style="height: 65px">
          <div>
            在线支付￥{{ price }}
          </div>
          <div>
            <el-button type="pay" @click="pay"
                       style="float: right; margin-top: -36px; background-color: #b3d4fc; color: #222222; font-weight: bold; font-size: 18px">
              去支付
            </el-button>
          </div>
        </el-card>
      </el-main>
      <el-aside width="500px" style="margin-right: -60px">
        <br>
        <el-card class="el-card" style="width: 480px;height: 300px;">
          <h3 style="margin-top: 0;margin-left: 0">限时取消</h3>
          <p style="font-size: 15px;">
            入住当天18:00前可免费取消修改。逾期不可取消/修改，若未入住将收取您首晚房费¥{{ this.price }}（如用优惠券则以券后支付价为准）。订单需等酒店或供应商确认后生效，订单确认结果以携程短信、邮件或app通知为准，如订单不确认将全额退款至您的付款账户。</p>
          <h3>说明</h3>
          <p style="font-size: 15px">
            预订服务由南科大ooad小组提供、住宿服务由酒店提供，交易款项由商家委托南科大ooad小组统一收取。</p>
        </el-card>
        <el-button @click="ali">阿里测试</el-button>
      </el-aside>
    </el-container>
    <el-row justify="center" style="padding: 20px 0 20px 0;">
      <hr width="600px">
      <p style="display: block">© 2022 Company, Inc.</p>
    </el-row>
    <el-dialog title="支付状态" :visible="dialogVisible" width="500px" @close="dialogClose">
      <h1>支付成功！</h1>
    </el-dialog>
  </div>

</template>
<script>

export default {
  name: 'room',
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      hotelName: '哈工深酒店',
      hotelAddress: '大学城',
      roomType: '双人间',
      bedNum: '2',
      dateRange: '',
      roomNum: 1,
      userForm: {
        userName: '',
        phoneNum: '',
        email: ''
      },
      time: '',
      h_id: 0,
      requirement: '',
      price: 0,
      oderId: 0,
      dialogVisible: false,
      rules: {
        userName: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        phoneNum: [{required: true, message: '请输入手机号码', trigger: 'blur'},
          {
            validator: function (rule, value, callback) {
              if (value === '' || value === null) {
                callback(new Error('请输入手机号码'))
              } else {
                if (/^1[3-9]\d{9}$/.test(value)) {
                  callback()
                } else {
                  callback(new Error('请输入正确的手机号码'))
                }
              }
            },
            trigger: 'blur'
          }
        ],
        email: [{required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'},
          {
            validator: function (rule, value, callback) {
              if (value === '' || value === null) {
                callback(new Error('请输入邮箱'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  activated () {
    if (this.$route.params.from === 'roomShow') {
      if (sessionStorage.getItem('haveLogin') === 'true') {
        this.HaveLogin = sessionStorage.getItem('haveLogin')
        this.loginUser = sessionStorage.getItem('loginUser')
      }
      this.LoadImg()
      this.hotelName = this.$route.params.hotelName
      this.hotelAddress = this.$route.params.position
      this.dateRange = this.$route.params.dateRange
      this.price = this.$route.params.price
      this.h_id = this.$route.params.h_id
      this.roomType = this.$route.params.roomType
    }
  },
  mounted () {
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
    this.hotelName = this.$route.params.hotelName
    this.hotelAddress = this.$route.params.position
    this.dateRange = this.$route.params.dateRange
    this.price = this.$route.params.price
    this.h_id = this.$route.params.h_id
    this.roomType = this.$route.params.roomType
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
    // 非中文无法输入
    inputChange (event) {
      const val = event.target
      val.value = val.value.replace(/[^\u4E00-\u9FA5]/g, '')
    },
    pay () {
      if (this.loginUser === '') {
        this.$message.error('请先登录')
        this.$router.push({name: 'Login', params: {form: 'Book'}})
      } else {
        if (this.userForm.userName !== '' && this.userForm.phoneNum !== '' && this.userForm.email !== '') {
          // this.$message.info('打钱！')
          this.$message.info(this.dateRange[0])
          let this_ = this
          // this.$message.info(typeof this.h_id)
          this.$axios({
            url: 'http://10.24.3.53:8181/insertO',
            method: 'POST',
            params: {
              HotelName: this.hotelName,
              RoomNumber: 103,
              username: this.loginUser,
              price: this.price,
              BeginData: this.dateRange[0],
              EndData: this.dateRange[1],
              room_type: this.roomType,
              PhoneNumber: this.userForm.phoneNum,
              h_id: this.h_id
            }
          }).then(function (resp) {
            // this_.$message.info(resp.data)
            console.log(this_.dateRange[0])
            console.log(this_.dateRange[1])
            this_.oderId = resp.data
            let url = `http://10.24.3.53:8181/alipay?orderId=${this_.oderId}`
            window.open(url, '_blank')
            this_.dialogVisible = true
          })
        } else {
          this.$message.error('请完善基本信息')
        }
      }
    },
    ali () {
      let url = 'http://10.24.3.53:8181/alipay?orderId=1'
      window.open(url, '_blank')
    },
    dialogClose () {
      let this_ = this
      this.dialogVisible = false
      this.$axios({
        url: `http://10.24.3.53:8181/points1?username=${this.loginUser}&points=${this.price}`,
        method: 'POST'
      }).then(function (resp) {
        if (resp.data === 200) {
          this_.$message.success('支付成功，相关信息已通过邮件通知您')
          let msg = `您已成功预定${this_.hotelName}的${this_.roomType},入住时间为${this_.dateRange[0]}-${this_.dateRange[1]},订单编号为${this_.oderId},祝您入住愉快！`
          let url = `http://10.24.3.53:8181/mail?to=${this_.userForm.email}&context=${msg}&username=${this_.loginUser}`
          let xmlHttp = new XMLHttpRequest()
          xmlHttp.open('POST', url, false)
          xmlHttp.send()
          this_.$router.push({name: 'MainPage'})
        } else {
          this_.$message.error('未知错误，稍后退款将打入您的支付账户')
          this_.$router.push({name: 'MainPage'})
        }
      })
    }
  }
}
</script>

<style scoped>
.el-card {
  background: #fbfbfb;
  width: 750px;
  height: 400px;
  display: inline-block;
  margin: 0em;
  justify-content: left;
  /*border-radius: 20px;*/
  box-shadow: 5px 5px 5px 5px #cccccc;
}
</style>
