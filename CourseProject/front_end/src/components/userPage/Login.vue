<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../../assets/imgs/small_logo2.png">
      </div>
      <!-- 登录表单区域 -->
      <el-form :model="loginForm" :rules="loginFormRules" ref="loginFormRef" label-width="0px" class="login_form">
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="请输入账号"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" placeholder="请输入密码"
                    show-password></el-input>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
          <el-button type="primary" @click="login">login</el-button>
          <el-button type="primary" @click="registerMethod">register</el-button>
          <el-button type="info" @click="resetLoginForm">reset</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginFormRules: {
        username: [{required: true, message: '请输入登录名称', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur'}],
        password: [{required: true, message: '请输入登录密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}]
      },
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false
    }
  },
  mounted () {
    if (window.history && window.history.pushState) {
      history.pushState(null, null, document.URL)
      window.addEventListener('popstate', this.back, false)
    }
  },
  methods: {
    // 点击重置按钮，重置登录表单
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      let url = 'http://10.24.3.53:8181/login?username=' + this.loginForm.username + '&password=' + this.loginForm.password
      let xmlHttp = new XMLHttpRequest()
      xmlHttp.open('POST', url, false)
      xmlHttp.send()
      let myData = xmlHttp.responseText
      // console.log(myData)
      if (myData === 'true') {
        this.$router.push({
          name: 'MainPage'
        })
        this.$router.go(0)
        sessionStorage.setItem('haveLogin', 'true')
        sessionStorage.setItem('loginUser', this.loginForm.username)
      } else {
        alert('用户不存在')
      }
    },
    registerMethod () {
      this.$router.push({
        name: 'Register'
      })
    }
  }
}

</script>

<style scoped>
.login_container {
  height: 792px;
  background-color: #2b4b6b;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.avatar_box {
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
}

img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #eee;
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  justify-content: flex-end;
}

</style>
