<template>
  <div>
    <div class="Header" style="background-color: #d9ecff" id="head">
      <div style="display: flex; justify-content: center">
        <img :src="hotel" alt @click="Home" style="width: 50px;" @mouseover="hotelOver" @mouseout="hotelOut">
        <img :src="contact" alt @click="ContactUs" style="width: 50px; margin-left: 20px" @mouseover="contactOver" @mouseout="contactOut">
        <img :src="loginImg" alt @click="LoginMethods" style="width: 50px; margin-left: 20px" @mouseover="loginImgOver" @mouseout="loginImgOut">
      </div>
    </div>
    <h2>评价页面</h2>
    <h3>您对入住的酒店满意吗?</h3>
    <div class="Starts">
      <div>
        <span style="display: inline">总体</span>
        <el-rate v-model="review.overallRate" show-score text-color="#ff9900" style="display: inline" required="true"></el-rate>
      </div>
      <div style="margin-top: 10px">
        <span style="display: inline">环境</span>
        <el-rate v-model="review.environmentRate" show-score text-color="#ff9900" style="display: inline"></el-rate>
      </div>
      <div style="margin-top: 10px">
        <span style="display: inline">噪音</span>
        <el-rate v-model="review.noiseRate" show-score text-color="#ff9900" style="display: inline"></el-rate>
      </div>
      <div style="margin-top: 10px">
        <span style="display: inline">服务</span>
        <el-rate v-model="review.serveRate" show-score text-color="#ff9900" style="display: inline"></el-rate>
      </div>
    </div>
    <div style="margin-top: 50px;">
      <el-input type="textarea" :rows="6" placeholder="您对此次入住体验如何？" v-model="review.message"
                maxlength="500" show-word-limit style="width: 600px;"></el-input>
      <div>
        <span style="font-size: 5px">点击左侧图标上传图片或视频，视频推荐格式mp4</span>
      </div>
    </div>
    <el-upload class="upload-area"
               action="http://10.24.3.53:8181/fileup"
               :on-preview="handlePreview"
               :before-remove="beforeRemove"
               :on-remove="handleRemove"
               multiple
               :limit="6"
               accept=".png, .jpg, .jpeg, .mp4, .flv"
               :on-exceed="handleExceed"
               :auto-upload="false"
               :file-list="fileList"
               list-type="picture"
               :on-progress="uploadProcess"
               :on-change="handChange"
               style="margin-top: -5px; margin-left: 470px; height: 40px; width: 300px;">
      <div class="video-area">
        <el-dialog :visible.sync="videoDialogVisible" style="text-align: center">
          <video :src="videoUrl" controls="controls" autoplay width="700px" height="400px"/>
        </el-dialog>
      </div>
      <div class="image-area">
        <el-dialog :visible.sync="imageDialogVisible" style="text-align: center">
          <img :src="imageUrl" controls="controls" width="500px"/>
        </el-dialog>
      </div>

      <div slot="trigger"
           style="float: left; width: 40px; background-color: #4c8df4; border-radius: 10px; margin-left: -150px">
        <img :src="require('../../assets/svgs/camera.svg')" alt style="height: 40px">
      </div>
      <el-progress
        v-if="uploadFlag == true"
        type="circle"
        :percentage="uploadPercent"
        style="margin-top:30px;"
      ></el-progress>
    </el-upload>

    <div>
      <el-button
        style="margin-left: 530px; background-color: darkturquoise; color: snow; font-weight: bold"
        @click="submit">提交
      </el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'evaluation',
  data () {
    return {
      hotel: require('../../assets/svgs/hotel.svg'),
      contact: require('../../assets/svgs/contact.svg'),
      loginUser: '',
      loginImg: '',
      HaveLogin: false,
      review: {
        overallRate: 0,
        environmentRate: 0,
        noiseRate: 0,
        serveRate: 0,
        message: ''
      },
      fileList: [],
      currentFile: [],
      uploadFlag: false,
      uploadPercent: 0,
      videoDialogVisible: false,
      imageDialogVisible: false,
      videoUrl: '',
      imageUrl: '',
      id: 0,
      hotelName: '',
      roomType: ''
    }
  },
  mounted () {
    if (sessionStorage.getItem('haveLogin') === 'true') {
      this.HaveLogin = sessionStorage.getItem('haveLogin')
      this.loginUser = sessionStorage.getItem('loginUser')
    }
    this.LoadImg()
    this.id = this.$route.params.id
    console.log(this.$route.params.id)
    this.hotelName = this.$route.params.hotelName
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
    handleRemove (file) {
      this.$message.success(`已移除${file.name}`)
    },
    handlePreview (file) {
      // alert(file)
      console.log(file)
      if (file.raw.type === 'video/mp4') {
        this.videoDialogVisible = true
        this.videoUrl = file.url
      } else {
        this.imageDialogVisible = true
        this.imageUrl = file.url
      }
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`您确定要移除 ${file.name}吗？`)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择6个文件，本次选择了${files.length}个文件，共选择了${files.length + fileList.length}个文件`)
    },
    uploadProcess (event, file, fileList) {
      console.log(event.percent, file, fileList)
      this.uploadFlag = true
      this.uploadPercent = Math.floor(event.percent)
    },
    handChange (file, fileList) {
      // 一旦选中文件，就将该文件添加到currentFile中
      this.currentFile.push(file)
    },
    submit () {
      if (this.check()) {
        let this_ = this
        let formData = new FormData()
        formData.append('review', this.loginUser)
        formData.append('review', this.review.overallRate)
        formData.append('review', this.review.environmentRate)
        formData.append('review', this.review.noiseRate)
        formData.append('review', this.review.serveRate)
        formData.append('review', this.review.message)
        formData.append('review', this.$route.params.id)
        console.log(this.$route.params.id)
        // console.log(this.currentFile)
        // alert(this.review.message)
        // 必须使用FormData对象向后台传递文件
        if (this.currentFile.length > 0) {
          let key
          // 将待提交的文件放到FormData对象中，必须挨个放
          for (key in this.currentFile) {
            formData.append('currentFile', this.currentFile[key].raw)
          }
          this.$axios({
            method: 'POST',
            data: formData,
            url: 'http://10.24.3.53:8181/fileUp'
          }).then(function (resp) {
            console.log(resp.data)
            if (resp.data === 200) {
              this_.$router.push({name: 'MainPage'})
            }
          })
        } else {
          console.log(formData.get('review'))
          this.$axios({
            method: 'POST',
            data: formData,
            url: 'http://10.24.3.53:8181/fileUp1'
          }).then(function (resp) {
            console.log('fileUp1')
            console.log(resp.data)
            if (resp.data === 200) {
              this_.review.overallRate = ''
              this_.review.environmentRate = ''
              this_.review.serveRate = ''
              this_.review.noiseRate = ''
              this_.review.message = ''
              this_.currentFile.pop()
              this_.$router.push({name: 'MainPage'})
            }
          })
        }
        // console.log(formData.get('review'))
      }
    },
    check () {
      if (this.review.overallRate === 0) {
        this.$message.error('请选择总体评价')
        return false
      } else if (this.review.environmentRate === 0) {
        this.$message.error('请选择环境评价')
        return false
      } else if (this.review.noiseRate === 0) {
        this.$message.error('请选择噪声评价')
        return false
      } else if (this.review.serveRate === 0) {
        this.$message.error('请选择服务评价')
        return false
      } else if (this.review.message === '') {
        this.$message.error('请添加文字说明')
        return false
      } else {
        return true
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
    }
  }
}
</script>

<style scoped>

</style>
