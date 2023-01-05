<template>
    <el-form :model="personalForm" :rules="rules" ref="personalForm" style="margin-top:60px">
      <el-row :gutter="12" style="margin-top:20px;">
        <el-col :span="6">
          <div style="text-align:right;"><span>手机号：</span></div>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="phone">
            <el-input type="text" v-model="personalForm.phone" placeholder="绑定的手机号" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="12" style="margin-top:1px;">
        <el-col :span="6">
          <div style="text-align:right;"><span>旧密码：</span></div>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="old_psw">
            <el-input type="password" show-password v-model="personalForm.old_psw" placeholder="请输入您的旧密码" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="12" style="margin-top:1px;">
        <el-col :span="6">
          <div style="text-align:right;"><span>新密码：</span></div>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="password1">
            <el-input type="password" show-password v-model="personalForm.password1" placeholder="请输入新的密码" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="12" style="margin-top:1px;">
        <el-col :span="6">
          <div style="text-align:right;"><span>确认密码：</span></div>
        </el-col>
        <el-col :span="5">
          <el-form-item prop="password2">
            <el-input type="password" show-password v-model="personalForm.password2" placeholder="请再次输入新的密码" ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="12" style="margin-top:10px;">
        <el-col :span="17" style="text-align:center;">
          <el-button type="primary" plain round size="medium" @click="submit('personalForm')">修改</el-button>
          <el-button type="primary" plain round size="medium" @click="reset">重置</el-button>
        </el-col>
      </el-row>
    </el-form>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      tableData: [{
        password: '',
        phone: '',
        username: '',
        email: ''
      }],
      userName: '',
      personalForm: {
        old_psw: '',
        phone: '',
        password1: '',
        password2: ''
      },
      rules: {
        phone: [
          { required: true, message: '请输入绑定的手机号', trigger: 'blur' },
          { pattern: /^\d*$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        old_psw: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        password1: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { pattern: /^[A-Za-z0-9]{6,18}$/, message: '密码为长度为6-18位的字母、数字', trigger: 'blur' }
        ],
        password2: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { pattern: /^[A-Za-z0-9]{6,18}$/, message: '密码为长度为6-18位的字母、数字', trigger: 'blur' }
        ]
      }
    }
  },
  mounted () {
    this.userName = sessionStorage.getItem('loginUser')
    let url = `http://10.24.3.53:8181/getUserInfo?username=${this.userName}`
    axios.get(url).then(res => {
      this.tableData = res.data
    })
  },
  methods: {
    submit (formName) { // 提交
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.personalForm.old_psw === this.tableData[0].password &&
            this.personalForm.phone === this.tableData[0].phone &&
            this.personalForm.password1 === this.personalForm.password2) {
            // 修改密码
            var URL = 'http://10.24.3.53:8181/password?username=' + this.tableData[0].username +
              '&password=' + this.personalForm.password1
            axios.post(URL, this.personalForm).then((res) => {
              console.log(res.data)
            })
            URL = 'http://10.24.3.53:8181/mail?to=' + this.tableData[0].email +
              '&context=恭喜您密码修改成功！' + '&username=' + this.tableData[0].username
            axios.post(URL, this.personalForm).then((res) => {
              console.log(res.data)
            })
            this.$alert('恭喜您密码修改成功!', '修改密码', {
              confirmButtonText: '确定',
              callback: action => {
                this.$router.push({
                  name: 'MainPage'
                })
              }
            }
            )
          } else {
            this.$message.error('修改失败，请检查您的输入信息！')
          }
        } else {
          this.$message.error('修改失败，请检查您的输入信息！')
        }
      })
    },
    reset () {
      this.personalForm.old_psw = ''
      this.personalForm.phone = ''
      this.personalForm.password1 = ''
      this.personalForm.password2 = ''
    }

  }
}
</script>

<style>
.name{ text-align: right; }
.value{ text-align: left; }
</style>
