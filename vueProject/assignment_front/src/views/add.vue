<!--add是一个弹窗组件-->
<template>
  <el-dialog center title="Add Course"  :visible.sync="dialogFormVisible" style="height: 100%">
    <el-form :model="classForm"
             :rules="rules"
             ref="classForm"
             label-position="right"
             label-width="115px"
             style="width: 500px; margin-left: 70px; height: 400px">

      <el-form-item label="Course Name" prop='course_name'>
        <el-input :rows="1" v-model="classForm.course_name"></el-input>
      </el-form-item>

      <el-form-item label="Course Code" prop="course_code">
        <el-input :rows="1" v-model="classForm.course_code"></el-input>
      </el-form-item>

      <el-form-item label="Language" prop="language">
        <el-radio-group v-model="classForm.language" style="margin-right: 12px;">
          <el-radio v-for="(radio, index) in languageList" :key="index" :label="radio">{{ radio }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="Teacher" prop="teacher">
        <el-input :rows="1" prefix-icon="el-icon-user-solid" v-model="classForm.teacher"></el-input>
      </el-form-item>

      <el-form-item label="Date" prop="date">
        <el-date-picker
          @input="selectChange()"
          v-model="classForm.date"
          type="date"
          value-format="yyyy/MM/dd"
          :picker-options="datePicker"
          placeholder="select date">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="Time" prop="time">
        <el-time-picker
          format='HH:mm'
          value-format="HH:mm"
          @input="selectChange()"
          v-model="classForm.time"
          :picker-options="{
      selectableRange: '00:00:00 - 23:59:59'
    }"
          placeholder="any time">
        </el-time-picker>
      </el-form-item>

      <el-form-item label="Location" prop="location">
        <el-select
          v-model="classForm.location"
          placeholder="Location">
          <el-option v-for="item in locationMapOptions"
                     :key="item.label"
                     :label="item.label"
                     :value="item.label"/>
        </el-select>
      </el-form-item>

      <el-form-item label="Duration" placeholder="Duration" prop="duration">
        <el-select v-model="classForm.duration" placeholder="Duration">
          <el-option v-for="item in durations"
                     :key="item.label"
                     :label="item.label"
                     :value="item.label"/>
        </el-select>
      </el-form-item>
    </el-form>

    <div slot="footer" class="dialog-footer" >
      <el-button type="primary" @click="submitData()">Submit</el-button>
      <el-button @click="cancel()">Cancel</el-button>
    </div>

  </el-dialog>
</template>

<script>
let if_edit = false
let len = window.localStorage.length
let data = []
for (let i = 1; i <= len - 1; i++) {
  i = i.toString()
  let temp = window.localStorage.getItem(i)
  data.push(JSON.parse(temp))
}
export default {
  name: 'add_class',
  data () {
    return {
      datePicker: {
        disabledDate (time) {
          return time.getTime() < Date.now()
        }
      },
      dialogFormVisible: false,
      languageList: ['Chinese', 'English', 'Bilingual'],
      durations: [
        {label: '0.5h', key: '0.5'},
        {label: '1.0h', key: '1'},
        {label: '1.5h', key: '1.5'},
        {label: '2.0h', key: '2'},
        {label: '2.5h', key: '2.5'},
        {label: '3.0h', key: '3'},
        {label: '3.5h', key: '3.5'},
        {label: '4.0h', key: '4'},
      ],
      locationMapOptions: [
        {label: 'Teaching Building No.1 Lecture Hall', key: 'TB1'},
        {label: 'Research Building Lecture Hall', key: 'Research'},
        {label: 'Library Conference Hall', key: 'lib'},
        {label: 'Activity Room', key: 'activity'}
      ],
      classForm: {
        course_name: '',
        course_code: '',
        language: '',
        teacher: '',
        date: '',
        time: '',
        location: '',
        duration: '',
        operation: ''
      },
      rules: {
        course_name: [{required: true, message: '请输入课程名称', trigger: 'blur'},
          {
            validator: function (rule, value, callback) {
              let reg = /[a-zA-z]$/
              if (value === '' || value === null) {
                callback(new Error('请输入课程名称'))
              } else {
                if (!reg.test(value)) {
                  callback(new Error('课程名称必须为英文'))
                } else {
                  callback()
                }
              }
            }, trigger: 'blur'
          }
        ],
        course_code: [{required: true, message: '请输入课程代码', trigger: 'blur'},
          {
            validator: function (rule, value, callback) {
              let reg = (/^(?![^a-zA-Z]+$)(?!\D+$)/)
              if (value === '' || value === null) {
                callback(new Error('请输入课程代码'))
              } else {
                if (!reg.test(value)) {
                  callback(new Error('课程代码必须为英文和数字结合'))
                } else {
                  callback()
                }
              }
            }, trigger: 'blur'
          }
        ],
        teacher: [{required: true, message: '请输入教师姓名', trigger: 'blur'},
          {
            validator: function (rule, value, callback) {
              let reg = /[a-zA-z]$/
              if (value === '' || value === null) {
                callback(new Error('请输入教师姓名'))
              } else {
                if (!reg.test(value)) {
                  callback(new Error('教师姓名必须为英文'))
                } else {
                  callback()
                }
              }
            }, trigger: 'blur'
          }
        ],
        language: [{required: true, message: '请选择教学语言', trigger: 'change'},],
        date: [{required: true, message: '请选择上课日期', trigger: 'change'},],
        time: [{required: true, message: '请选择上课时间', trigger: 'change'},],
        location: [{required: true, message: '请选择教学地点', trigger: 'change'},],
        duration: [{required: true, message: '请选择教学时长', trigger: 'change'},],
      },
    }
  },

  methods: {
    edit_data (row) {
      if_edit = true
      this.classForm.course_name = row.course_name
      this.classForm.course_code = row.course_code
      this.classForm.language = row.language
      this.classForm.teacher = row.teacher
      this.classForm.date = row.date
      this.classForm.time = row.time
      this.classForm.location = row.location
      this.classForm.duration = row.duration
      this.classForm.operation = row.operation
      this.dialogFormVisible = true
    },
    showDialog () {
      this.dialogFormVisible = true
    },
    submitData () {
      let flag = true
      this.$refs.classForm.validate(valid => {
        if (valid) {
          //经测试，数据已经可以成功回传，接下来需要处理对数据的判断
          console.log('valid')
          console.log(this.classForm)
          if (this.sameTimeAndRoom()) {
            this.$message({
              message: 'two different courses cannot share one room at same time.',
              type: 'error'
            })
            flag = false
          }
          if (this.moreThanOneLec()) {
            this.$message({
              message: 'Each teacher can take no more than one lecture per day.',
              type: 'error'
            })
            flag = false
          }
          if (this.moreThanOneDay()) {
            this.$message({
              message: 'One course is scheduled at most once a day.',
              type: 'error'
            })
            flag = false
          }
          if (this.sameCode()) {
            this.$message({
              message: 'Different courses should have different course codes..',
              type: 'error'
            })
            flag = false
          }
          if (flag) {
            //key 是对应的index
            let index = this.classForm.operation
            if (!if_edit) {
              index = window.localStorage.length
              if (index == 0) {
                index += 1
              }
              index = index.toString()
              this.classForm.operation = index
            }
            window.localStorage.setItem(index, JSON.stringify(this.classForm))
            this.$message({
              message: 'submit',
              type: 'success'
            })
            this.$emit('add_event', this.classForm)
            this.cancel()
          }

        } else {
          // $emit触发当前实例上的事件
          // 触发父组件的children事件，将this.form回传过去
          this.$message({
            message: 'Error',
            type: 'error'
          })
          console.log(this.classForm)
        }
      })
    },
    cancel () {
      location.reload()
      // // 关闭对话框
      this.dialogFormVisible = false
    },
    selectChange () {
      this.$nextTick(() => {
        this.$forceUpdate()
      })
    },
    sameTimeAndRoom () {
      for (let i = 0; i < data.length; i++) {
        let temp = data[i]
        if (temp.operation !== this.classForm.operation) {
          let flag = false
          let had_time = parseInt(temp.time.substring(0, 2))
          let cur_time = parseInt(this.classForm.time.substring(0, 2))
          if (cur_time === had_time || cur_time === had_time + 1 || cur_time === had_time - 1) {
            flag = true
          }
          if (this.classForm.date === temp.date && this.classForm.location === temp.location && flag) {
            return true
          }
        }
      }
    },
    moreThanOneLec () {
      for (let i = 0; i < data.length; i++) {
        let temp = data[i]
        if (temp.operation !== this.classForm.operation) {
          if (temp.date === this.classForm.date && this.classForm.teacher === temp.teacher) {
            return true
          }
        }
      }
    },
    moreThanOneDay () {
      for (let i = 0; i < data.length; i++) {
        let temp = data[i]
        if (temp.operation !== this.classForm.operation) {
          if (this.classForm.course_name === temp.course_name && this.classForm.date === temp.date) {
            return true
          }
        }
      }
    },
    sameCode () {
      for (let i = 0; i < data.length; i++) {
        let temp = data[i]
        if (temp.operation !== this.classForm.operation) {
          if (this.classForm.course_code !== temp.course_code && this.classForm.course_name === temp.course_name) {
            return true
          }
        }
      }
    }
  }
}
</script>

<style scoped>

.el-form-item {
  margin: 15px;
}
.el-button{
  margin: 30px;
}

</style>
