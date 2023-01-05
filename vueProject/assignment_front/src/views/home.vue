<template>
  <el-container>
    <el-header>
      <span>Open Courses</span>
    </el-header>
    <el-main>
      <div>
        <el-card>
          <!-- 搜索与添加区域
          gutter 属性来设置方格之间的距离
          span是宽度-->
          <el-row :gutter="20">
            <el-col :span="5" :offset="17">
              <el-input placeholder="search by course name" class="input-with-select">
                <el-button slot="append" icon="el-icon-search" @click="searchClass"></el-button>
              </el-input>
            </el-col>
            <el-col :span="2">
              <el-button type="primary" @click="logout">log out</el-button>
            </el-col>
          </el-row>
        </el-card>

        <!--v-fit-columns是一个插件，可以让列宽自适应-->
        <el-table
          :data="courseData"
          border
          style="width: 100%"
        >
          <el-table-column
            fixed
            prop="course_name"
            label="Course Name"
            width="160">
          </el-table-column>

          <el-table-column
            label="Course Code"
            width="150">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Course Code: {{ scope.row.course_code }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.course_code }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column
            label="Language"
            width="140">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Language: {{ scope.row.language }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.language }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column
            label="Teacher"
            width="140">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Teacher: {{ scope.row.teacher }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.teacher }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column
            label="Date"
            width="140">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Date: {{ scope.row.date }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.date }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column
            label="Time"
            width="134">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Time: {{ scope.row.time }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.time }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column
            label="Location"
            width="280">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Location: {{ scope.row.location }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.location }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column
            label="Duration"
            width="120">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>Duration: {{ scope.row.duration }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.duration }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column label="Operation" width="215">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="editClass(scope.row)">Edit
              </el-button>
              <el-button
                size="mini"
                type="danger"
                icon="el-icon-circle-close"
                @click="deleteClass(scope.row)">Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="addClass"
                   style="display: block; margin: 0 auto">Add class</el-button>
        <add_class ref="add_class" @add_event="from_add"></add_class>
      </div>
    </el-main>
  </el-container>
</template>

<script>
let len = window.localStorage.length
let data = []
for (let i = 1; i <= len - 1; i++) {
  i = i.toString()
  let temp = window.localStorage.getItem(i)
  data.push(JSON.parse(temp))
}

import add_class from './add'

export default {
  name: 'home',
  components: {
    add_class,
  },
  data () {
    return {
      courseData: data,
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
      }
    }
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      //重定向到登录页面
      this.$router.push('/login')
      this.$message({
        message: '退出成功',
        type: 'success'
      })
    },

    searchClass () {
      this.$message({
        message: '点击了搜索',
      })
      console.log('this is data')
      console.log(data)
      console.log('this is item1')
      console.log(window.localStorage.getItem('1'))
      console.log('this is len')
      console.log(len)
    },

    addClass () {
      this.$refs.add_class.showDialog()
    },

    editClass (row) {
      this.$refs.add_class.edit_data(row)
    },
    from_add (obj) {
      if (obj) {
        location.reload()
      }
    },

    deleteClass (row) {
      this.$message({
        message: '删除成功',
      })
      let select_index = row.operation
      let len = window.localStorage.length
      // let data = []
      // for (let i = 1; i < len-1; i++) {
      //   i = i.toString()
      //   let temp = window.localStorage.getItem(i)
      //   data.push(temp)
      // }
      for (let j = 0; j < len; j++) {
        j = j.toString()
        window.localStorage.removeItem(j)
      }
      let count = 1
      for (let i = 0; i < data.length; i++) {
        let temp = data[i]
        if (temp.operation !== select_index){
          this.classForm.course_name = temp.course_name
          this.classForm.course_code = temp.course_code
          this.classForm.language = temp.language
          this.classForm.teacher = temp.teacher
          this.classForm.date = temp.date
          this.classForm.time = temp.time
          this.classForm.location = temp.location
          this.classForm.duration = temp.duration
          this.classForm.operation = count
          window.localStorage.setItem(count.toString(), JSON.stringify(this.classForm))
          count += 1
        }
      }
      location.reload()
    },
  }
}
</script>

<style scoped>
.el-header {
  background-color: coral;
  display: flex;
  justify-content: center;
  font-size: 30px;
}
</style>
