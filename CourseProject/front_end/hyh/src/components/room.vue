<template>
  <div>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item>房间基本信息</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button style="position: relative;left: 1150px;top: -20px"
                 type="primary"
                 @click="handleAdd"
                 icon="el-icon-circle-plus-outline">添加房间</el-button>
<!--      <el-button style="position: relative;left: 1050px;top: -20px" type="danger" icon="el-icon-remove-outline" >批量删除</el-button>-->
    </div>
  <el-table
    :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
    :header-cell-class-name="headerBg"
    size="medium"
    height="550"
    border
    style="width: 100%;margin-top: 0">
    <el-table-column
      label="房间号"
      prop="roomNumber">
    </el-table-column>
    <el-table-column
      label="房间类型"
      prop="room_type">
    </el-table-column>
    <el-table-column
      label="图片名称"
      prop="graphPath">
    </el-table-column>
    <el-table-column
      align="right"
      label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="success"
          @click="handleEdit(scope.$index, scope.row)">修改<i class="el-icon-edit"></i></el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除<i class="el-icon-delete"></i></el-button>
      </template>
    </el-table-column>
  </el-table>
  <div style="padding: 10px 0">
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="tableData.length">
    </el-pagination>
  </div>
    <el-dialog :visible.sync="dialogFormVisible" title= "Operation">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="房间类型" prop="room_type">
          <el-select v-model="ruleForm.room_type" placeholder="请选择房间类型">
            <el-option label="标准单人间" value="标准单人间"></el-option>
            <el-option label="豪华单人间" value="豪华单人间"></el-option>
            <el-option label="标准大床房" value="标准大床房"></el-option>
            <el-option label="标准双床房" value="标准双床房"></el-option>
            <el-option label="豪华双床房" value="豪华双床房"></el-option>
            <el-option label="总统套房" value="总统套房"></el-option>
            <el-option label="三床房" value="三床房"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="ruleForm.roomNumber" placeholder="输入的房间号为整数"></el-input>
        </el-form-item>
        <el-form-item  label="图片名称" prop="graphPath">
          <el-input  v-model="ruleForm.graphPath" placeholder="输入的图片为.jpg"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import axios from 'axios'

export default {
  inject: ['reload'],
  name: 'room',
  data () {
    return {
      tableData: [{
        id: '1',
        roomNumber: '423',
        room_type: '总统套房',
        graphPath: 'D:\\Download\\a.jpg'
      },
      {
        id: '2',
        roomNumber: '424',
        room_type: '总统套房',
        graphPath: 'D:\\Download\\b.jpg'
      }],
      ruleForm: {
        id: '',
        roomNumber: '',
        room_type: '',
        graphPath: ''
      },
      rules: {
        roomNumber: [
          { required: true, message: '请输入房间号', trigger: 'blur' },
          { pattern: /^\d*$/, message: '请输入整数', trigger: 'blur' }
        ],
        room_type: [
          { required: true, message: '请选择房间类型', trigger: 'change' }
        ],
        graphPath: [
          { required: true, message: '请输入房间图片名称(.jpg)', trigger: 'blur' },
          // eslint-disable-next-line no-useless-escape
          { pattern: /\.jpg$/, message: '请输入正确的图片格式', trigger: 'blur' }
        ]
      },
      headerBg: 'headerBg',
      currentPage: 1,
      total: 20,
      pageSize: 5,
      dialogFormVisible: false,
      operation: '',
      hotelName: '南山分店',
      h_id: ''
    }
  },
  mounted () {
    this.h_id = 1
    this.receive()
    console.log(this.tableData)
  },
  methods: {
    receive () {
      axios.get('http://10.24.168.173:8181/findRooms?h_id=' + this.h_id).then(res => {
        this.tableData = res.data
      })
    },
    send (data, url) {
      axios.post(url, data).then((res) => {
        console.log(res.data)
        if (!res.data) {
          this.$alert('很遗憾，' + this.operation + '失败！', this.operation, {
            confirmButtonText: '确定'
          })
        } else {
          this.$alert('恭喜你' + this.operation + '成功！', this.operation, {
            confirmButtonText: '确定'
          })
        }
      })
    },
    handleAdd () {
      this.ruleForm =
        {id: '',
          roomNumber: '',
          room_type: '',
          graphPath: ''
        }
      this.dialogFormVisible = true
      this.operation = '添加'
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.operation === '修改') {
            const url = 'http://10.24.168.173:8181/updateRoom?HotelName=' + this.hotelName +
              '&RoomNumber=' + this.ruleForm.roomNumber + '&graphPath=' + this.ruleForm.graphPath +
              '&h_id=' + this.h_id + '&room_type=' + this.ruleForm.room_type + '&id=' + this.ruleForm.id
            const temp = {
              hotelName: this.hotelName,
              roomNumber: parseInt(this.ruleForm.roomNumber),
              graphPath: this.ruleForm.graphPath,
              h_id: this.h_id,
              room_type: this.ruleForm.room_type,
              id: this.ruleForm.id
            }
            this.send(temp, url)
            this.reload()
          }
          if (this.operation === '添加') {
            const url = 'http://10.24.168.173:8181/insertRoom?HotelName=' + this.hotelName +
              '&RoomNumber=' + this.ruleForm.roomNumber + '&graphPath=' + this.ruleForm.graphPath +
              '&h_id=' + this.h_id + '&room_type=' + this.ruleForm.room_type
            const temp = {
              hotelName: this.hotelName,
              roomNumber: parseInt(this.ruleForm.roomNumber),
              graphPath: this.ruleForm.graphPath,
              h_id: this.h_id,
              room_type: this.ruleForm.room_type
            }
            this.send(temp, url)
            this.reload()
          }
        } else {
          this.$alert('很遗憾，' + this.operation + '失败！', this.operation, {
            confirmButtonText: '确定'
          })
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    handleEdit (index, row) {
      this.ruleForm = row
      this.dialogFormVisible = true
      this.operation = '修改'
    },
    handleDelete (index, row) {
      this.operation = '删除'
      const url = 'http://10.24.168.173:8181/deleteRoom?id=' + row.id
      this.operation = '删除'
      this.send(row, url)
      this.reload()
    },
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.pageSize = val
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.currentPage = val
    }
  }
}
</script>

<style>
.headerBg{
  background: #eee!important;
}
</style>
