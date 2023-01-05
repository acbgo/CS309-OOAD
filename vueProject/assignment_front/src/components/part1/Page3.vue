<template>
  <el-container>
    <el-header>
      <span>Open Courses</span>
    </el-header>
    <el-main>
      <div>
        <el-card>
<!--          搜索与添加区域-->
          <el-row :gutter="20">
            <el-col :span="8">
              <el-input placeholder="search by course name" class="input-with-select">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input>
            </el-col>
            <el-col :span="4">
              <el-button type="primary">Add class</el-button>
            </el-col>
          </el-row>
        </el-card>
        <el-table
          :data="courseData"
          border
          style="width: 100%"
          height="250">
          <el-table-column
            fixed
            prop="course_name"
            label="Course Name"
            width="150">
          </el-table-column>
          <el-table-column
            label="Course Code"
            width="120">
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
            width="120">
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
            width="120">
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
            width="120">
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
            width="120">
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
            width="120">
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
          <el-table-column label="Operation">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleEdit(scope.$index, scope.row)">Edit
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">Delete
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      courseData: [{
        course_name: 'OOAD',
        course_code: 'CS309',
        language: 'English',
        teacher: 'XXX',
        date: '2022/09/30',
        time: '19:00',
        location: 'Activity Room',
        duration: '3.0h',
        operation: ''
      }]
    }
  }
}
</script>

<style scoped>
.el-header {
  background-color: coral;
  display: flex;
  justify-content: center;
  color: #333333;
  font-size: 26px;
}


</style>
