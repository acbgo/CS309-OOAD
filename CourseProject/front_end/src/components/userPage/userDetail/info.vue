<template>
  <div>
    <el-card>
      <el-descriptions class="margin-top" title="简介" :column="2" border>
        <template slot="extra">
          <el-button type="primary" size="small" plain @click="open">修改</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture-outline"></i>
            头像
          </template>
          <img class="img" :src="imageUrl" alt="" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            账户名
          </template>
          {{ tableData.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            姓名
          </template>
          {{ tableData.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-odometer"></i>
            年龄
          </template>
          {{ tableData.age }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-male"></i>
            <i class="el-icon-female"></i>
            性别
          </template>
          <el-tag size="small">{{ tableData.gender }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-message"></i>
            邮箱Email
          </template>
          {{ tableData.email }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            手机号码
          </template>
          {{ tableData.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            地区
          </template>
          {{ tableData.address }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-magic-stick"></i>
            个性签名
          </template>
          {{ tableData.remark }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
    <el-dialog
      title="修改个人信息"
      :visible.sync="dialogVisible"
      width="60%"
      append-to-body
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="150px">
        <div class="updateinfo">
          <div class="left">
            <el-form-item label="头像" prop="imageUrl">
              <el-upload
                class="avatar-uploader"
                action="http://10.24.3.53:8181/userUpdate"
                :show-file-list="false"
                :auto-upload="false"
                :on-change="handChange">
                <img v-if="imageUrl" :src="imageUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
              <!--              <img style="width:150px;height:110px" src="../assets/logo.png" alt="">-->
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input v-model="form.age"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
              <el-switch
                v-model="form.gender"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="男"
                inactive-text="女"
                :active-value= "1"
                :inactive-value= "0"
              >
              </el-switch>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"></el-input>
            </el-form-item>

          </div>
          <div class="right">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" disabled></el-input>
            </el-form-item>
            <el-form-item label="账户名" prop="username">
              <el-input v-model="form.username" disabled></el-input>
            </el-form-item>
            <el-form-item label="地区" prop="address">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="个性签名" prop="remark">
              <el-input v-model="form.remark"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone"></el-input>
            </el-form-item>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" @click="submit('form')">提 交</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>

import axios from 'axios'
import _ from 'lodash'

export default {
  inject: ['reload1'],
  name: 'Info',
  data () {
    return {
      tableData: {
        username: '123456',
        age: '20',
        email: '12011118@sb.com',
        phone: '13833884388',
        address: 'shenzhen',
        name: 'csb',
        gender: 1,
        remark: 'nbnbnb'
      },
      imageUrl: '',
      upFile: '',
      dialogVisible: false,
      form: {
        username: '123456',
        age: '20',
        email: '12011118@sb.com',
        phone: '13833884388',
        address: 'shenzhen',
        name: 'csb',
        gender: 1,
        remark: 'nbnbnb'
      },
      rules: {
        name: [
          { required: true, message: '昵称不能为空', trigger: 'blur' }
        ],
        age: [
          { pattern: /^\+?[1-9][0-9]*$/, message: '请输入大于0的整数', trigger: 'blur' }
        ],
        email:
          [{required: true, message: '请输入邮箱', trigger: 'blur'},
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
  mounted () {
    this.tableData.username = sessionStorage.getItem('loginUser')
    this.imageUrl = `http://10.24.3.53:8181/getUser?username=${this.tableData.username}`
    let url = `http://10.24.3.53:8181/getUserInfo?username=${this.tableData.username}`
    axios.get(url).then(res => {
      this.tableData = res.data[0]
      if (res.data[0].gender === 1) {
        this.tableData.gender = '男'
      } else {
        this.tableData.gender = '女'
      }
    })
  },
  methods: {
    open () {
      this.dialogVisible = true
      this.form = _.cloneDeep(this.tableData)
      if (this.form.gender === '男') {
        this.form.gender = 1
      } else {
        this.form.gender = 0
      }
    },
    submit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('upData', this.tableData.username)
          formData.append('upData', this.form.age)
          formData.append('upData', this.form.gender)
          formData.append('upData', this.form.email)
          formData.append('upData', this.form.address)
          formData.append('upData', this.form.remark)
          formData.append('upData', this.form.phone)
          if (this.upFile !== '') {
            formData.append('upFile', this.upFile.raw)
            axios({
              method: 'POST',
              data: formData,
              url: 'http://10.24.3.53:8181/userUpdate'
            }).then(function (resp) {
              if (resp.data === 200) {
              }
            })
          } else {
            axios({
              method: 'POST',
              data: formData,
              url: 'http://10.24.3.53:8181/userUpdate1'
            }).then(function (resp) {
              if (resp.data === 200) {
              }
            })
          }
          this.$alert('恭喜你，修改成功！', '简介修改', {
            confirmButtonText: '确定',
            callback: action => {
              this.$router.go(0)
            }
          })
          this.dialogVisible = false
        } else {
          this.$message.error('很遗憾，修改失败!')
          return false
        }
      })
    },
    handleClose () {
      this.dialogVisible = false
      this.$emit('flesh')
    },
    handChange (file) {
      const isJPG = file.raw.type === 'image/jpeg'
      const isLt2M = file.raw.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      if (isJPG && isLt2M) {
        this.upFile = file
        this.imageUrl = URL.createObjectURL(file.raw)
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
.img {
  width: 100px;
  height: 80px;
}
.updateinfo {
  height: 350px;
  overflow: auto;
}
.left {
  /* width: 330px; */
  float: left;
}
.right {
  overflow: hidden;
}
</style>
