<template>
  <div>
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px">
      </div>
    </div>
    <div class="Text" style="margin-top: 40px; margin-bottom: 50px">
      <span style="font-size: 45px; font-weight: 700; color: #666; text-align: center">联系我们</span>
      <p>如果您对酒店的服务有任何问题，欢迎随时联系我们</p>
      <hr width="300px">
    </div>
    <div class="Box" style="display: flex; justify-content: center">
      <div class="Address">
        <img :src="require('../../assets/svgs/location.svg')" alt style="width: 50px; margin-top: 20px">
        <h3>地址</h3>
        <p>南方科技大学湖畔3栋</p>
      </div>
      <div class="Email">
        <img :src="require('../../assets/svgs/email.svg')" alt style="width: 50px; margin-top: 20px">
        <h3>邮箱</h3>
        <p>12010000@mail.sustech.edu.cn</p>
      </div>
      <div class="Phone">
        <img :src="require('../../assets/svgs/phone.svg')" alt style="width: 50px; margin-top: 20px">
        <h3>联系电话</h3>
        <p>+86 12345678910</p>
      </div>
    </div>
    <div class="Contact" style="display: flex">
      <div class="Form">
        <form>
          <div style="display: flex; justify-content: center; margin-top: 20px">
            <div>
              <span style="float: left; margin-left: 0px">您的名字</span>
              <p style="line-height: 10px;"></p>
              <input v-model="form.name" style="width: 420px; height: 25px; border-radius: 15px;"
                     placeholder="例如：李明">
            </div>
            <div style="margin-left: 30px">
              <span style="float: left;">您的邮箱</span>
              <p style="line-height: 10px;"></p>
              <input v-model="form.email" style="width: 420px; height: 25px; border-radius: 15px;"
                     placeholder="例如：123456789@qq.com">
            </div>
          </div>
          <div style="margin-top: 10px; margin-left: 47px;">
            <label style="float: left">主体信息</label>
            <textarea v-model="form.msg" rows="5"
                      style="width: 875px; margin-top: 20px; margin-left: -110px; font-size: 17px; font-weight: 200; border-radius: 20px; border: #222222 2px solid"
                      placeholder="例如：这家酒店真是太棒了！！！"
            ></textarea>
          </div>
        </form>
        <el-button
          style="background-color: #222222; color: #00CED1; float: left; margin-left: 50px; margin-top: 20px; width: 150px; height: 40px; border-radius: 20px; font-size: 17px">
          发送
        </el-button>
      </div>
      <div class="Online">
        <img :src="require('../../assets/svgs/CustomerService.svg')" alt @click="Im()" style="width: 70px; margin-top: 5px">
        <h3 style="margin-top: 0px">在线客服</h3>
      </div>
    </div>
  </div>
</template>

<script>
import Im from './Chat.vue'
import MainPage from './MainPage.vue'

export default {
  name: 'ContactUs',
  components: {Im},
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      componentName: '',
      lastPage: '',
      components: {Im},
      form: {
        name: '',
        email: '',
        title: '',
        msg: ''
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
    MainPage () {
      return MainPage
    },
    Im () {
      this.$router.push('/chat')
      sessionStorage['user'] = '{"username":' + ' "' + this.loginUser + '"}'
      console.log(this.componentName)
    }
  }
}
</script>

<style scoped>
.Address {
  width: 280px;
  height: 170px;
  background: #222222;
  border-radius: 30px;
  color: #fffafa;
}

.Email {
  width: 280px;
  height: 170px;
  background: #222222;
  border-radius: 30px;
  margin-left: 50px;
  color: #fffafa;
}

.Phone {
  width: 280px;
  height: 170px;
  background: #222222;
  border-radius: 30px;
  margin-left: 50px;
  color: #fffafa;
}

.Form {
  width: 980px;
  height: 335px;
  margin-left: 280px;
  margin-top: 30px;
}

.Online {
  width: 120px;
  height: 120px;
  background: #222222;
  margin-left: 110px;
  margin-top: 220px;
  border-radius: 30px;
  color: #fffafa;
}
</style>
