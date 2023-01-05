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
    <div class="Body" style="margin-top: -21px">
      <div class="Box">
        <div class="Select" style="display: flex; justify-content: center;">
          <div class="Grade">
            <h3>星级</h3>
            <div>
              <el-select v-model="hotelForm.grade" placeholder="Grade">
                <el-option v-for="item in hotelGradeOptions"
                           :key="item.value"
                           :value="item.value"/>
              </el-select>
            </div>
          </div>
          <div class="Destination" style="margin-left: 20px">
            <h3>目的地</h3>
            <div>
              <el-cascader v-model="hotelForm.destination"
                           placeholder="destination"
                           :options="destinations"
                           filterable
              >
              </el-cascader>
            </div>
          </div>
          <div class="DateRange" style="margin-left: 20px; display: block">
            <h3>入住时间</h3>
            <el-date-picker
              v-model="hotelForm.dateRange"
              :picker-options="setRange"
              type="daterange"
              value-format="yyyy-MM-dd HH:mm:ss"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </div>
          <div class="Search" style="margin-left: 20px; margin-top: 57px;" @mouseenter="buttonOver"
               @mouseleave="buttonOut">
            <el-button class="search_btn" @click="search"
                       :style="buttonStyle">
              搜索
            </el-button>
          </div>
        </div>
        <div class="Bottom">
          <div style="padding-top: 270px">
            <span style="font-size: 40px">南科大酒店，给您家的感觉</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainPage',
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      buttonStyle: 'background-color: #d9ecff; color: #222222; border-radius: 20px; width: 120px; font-size: 18px',
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      componentName: '',
      lastPage: [],
      myData: [],
      hotelForm: {
        grade: '',
        destination: '',
        dateRange: new Date()
      },
      hotelGradeOptions: [
        {label: '经济型', value: 1},
        {label: '舒适型', value: 2},
        {label: '高档型', value: 3},
        {label: '豪华型', value: 4},
        {label: '别墅型', value: 5}
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
      setRange: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        }
      }
    }
  },
  mounted () {
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
  },
  methods: {
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
    LoadImg () {
      if (this.HaveLogin === 'true') {
        this.loginImg = require('../../assets/svgs/login1.svg')
      } else {
        this.loginImg = require('../../assets/svgs/login0.svg')
      }
    },
    search () {
      // this.$message.info(typeof this.hotelForm.dateRange)
      // console.log(this.hotelForm.dateRange)
      this.$router.push({
        name: 'Reservation',
        params: {
          grade: this.hotelForm.grade,
          destination: this.hotelForm.destination,
          dateRange: this.hotelForm.dateRange,
          from: 'main'
        }
      })
    },
    Im () {
      this.$router.push('/chat')
      sessionStorage['user'] = '{"username":' + ' "' + this.loginUser + '"}'
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
    buttonOver () {
      this.buttonStyle = 'background-color: #7AC5CD; color: #222222; border-radius: 20px; width: 120px; font-size: 18px'
    },
    buttonOut () {
      this.buttonStyle = 'background-color: #d9ecff; color: #222222; border-radius: 20px; width: 120px; font-size: 18px'
    }
  }
}
</script>

<style scoped>
.Select {
  /*background:rgba(255,255,255,0.3);*/
  background: rgba(131, 139, 139, 0.3);
  margin-top: 21px;
  padding-top: 30px;
  height: 180px;
}

.Bottom {
  background: url('../../assets/imgs/bg.jpg') center;
  width: 1536px;
  height: 554px;
  margin-top: -22px;
}

</style>
