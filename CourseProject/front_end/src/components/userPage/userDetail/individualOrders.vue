<template>
  <div>
    <div>
    </div>
    <el-table
      :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
      :header-cell-class-name="headerBg"
      size="medium"
      height="500"
      border
      style="width: 100%;margin-top: 0">
      <el-table-column
        label="房间类型"
        prop="room_type">
      </el-table-column>
      <el-table-column
        label="价格"
        prop="price">
      </el-table-column>
      <el-table-column
        label="开始时间"
        prop="beginData">
      </el-table-column>
      <el-table-column
        label="结束时间"
        prop="endData">
      </el-table-column>
      <el-table-column
        label="酒店名称"
        prop="hotelName">
      </el-table-column>
      <el-table-column
        label="手机号码"
        prop="phoneNumber">
      </el-table-column>
      <el-table-column
        label="备注"
        prop="remark">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            style="margin-left: 10px"
            @click="handleEdit(scope.$index, scope.row)">修改<i class="el-icon-edit"></i></el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除<i class="el-icon-delete"></i></el-button>
          <el-button
            size="mini"
            type="primary"
            v-show="scope.row.flag"
            @click="pageJump(scope.$index, scope.row)">评价<i class="el-icon-edit-outline"></i></el-button>
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
    <el-dialog :visible.sync="dialogFormVisible" title= "Operation" append-to-body>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="手机号码" prop="phoneNumber">
          <el-input v-model="ruleForm.phoneNumber" placeholder="输入正确的电话号码"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="ruleForm.remark" placeholder="请输入备注"></el-input>
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

function isDuringDate (endDateStr) {
  const curDate = new Date()
  const endDate = new Date(endDateStr)
  return curDate > endDate
}

export default {
  name: 'orders',
  data () {
    return {
      tableData: [{
        id: '1',
        state: '',
        h_id: '',
        remark: '真好啊！',
        username: 'cjy',
        price: '120',
        beginData: '',
        endData: '',
        room_type: '总统套房',
        hotelName: '深大分店',
        idcard: '',
        phoneNumber: '18977571553',
        flag: true
      },
      {
        id: '2',
        state: '',
        h_id: '',
        remark: '真好啊！',
        username: 'cjy',
        price: '130',
        beginData: '',
        endDate: '',
        room_type: '总统套房',
        hotelName: '深大分店',
        idcard: '',
        phoneNumber: '',
        flag: true
      }],
      userName: '',
      ruleForm: {
        id: '',
        state: '',
        h_id: '',
        remark: '',
        username: '',
        price: '',
        beginData: '',
        endData: '',
        room_type: '',
        hotelName: '深大分店',
        phoneNumber: ''
      },
      rules: {
        phoneNumber: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^\d*$/, message: '请输入整数', trigger: 'blur' }
        ]
      },
      headerBg: 'headerBg',
      currentPage: 1,
      total: 20,
      pageSize: 5,
      dialogFormVisible: false
    }
  },
  mounted () {
    this.receive()
  },
  methods: {
    receive () {
      this.userName = sessionStorage.getItem('loginUser')
      let url = `http://10.24.3.53:8181/findOrderByName?username=${this.userName}`
      axios.get(url).then(res => {
        this.tableData = res.data
        for (let i = 0; i < res.data.length; i++) {
          this.tableData[i].flag = isDuringDate(res.data[i].endData) && res.data[i].state === 0
        }
        console.log(this.tableData)
      })
    },
    send (data, url) {
      axios.post(url, data).then((res) => {
      })
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url = 'http://10.24.3.53:8181/updateOrder?HotelName=' + this.ruleForm.hotelName +
            '&RoomNumber=' + this.ruleForm.roomNumber + '&username=' + this.ruleForm.username + '&price=' + this.ruleForm.price +
            '&room_type=' + this.ruleForm.room_type + '&phoneNumber=' + this.ruleForm.phoneNumber +
            '&state=' + this.ruleForm.state + '&remark=' + this.ruleForm.remark + '&h_id=' + this.ruleForm.h_id + '&id=' + this.ruleForm.id
          console.log(url)
          this.send(this.ruleForm, url)
          this.$alert('恭喜你，修改成功!', '修改订单', {
            confirmButtonText: '确定',
            callback: action => {
              this.$router.go(0)
            }
          }
          )
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
      const url = 'http://10.24.3.53:8181/deleteOrder?id=' + row.id
      console.log(url)
      this.send(row, url)
      this.$alert('恭喜你，删除成功!', '删除订单', {
        confirmButtonText: '确定',
        callback: action => {
          this.$router.go(0)
        }
      }
      )
    },
    pageJump (index, row) {
      this.ruleForm = row
      this.ruleForm.state = 1
      const url = 'http://10.24.3.53:8181/updateOrder?HotelName=' + this.ruleForm.hotelName +
        '&RoomNumber=' + this.ruleForm.roomNumber + '&username=' + this.ruleForm.username + '&price=' + this.ruleForm.price +
        '&room_type=' + this.ruleForm.room_type + '&phoneNumber=' + this.ruleForm.phoneNumber +
        '&state=' + this.ruleForm.state + '&remark=' + this.ruleForm.remark + '&h_id=' + this.ruleForm.h_id + '&id=' + this.ruleForm.id
      console.log(url)
      this.send(this.ruleForm, url)
      this.$router.push({
        name: 'review',
        params: {
          id: this.ruleForm.id,
          hotelName: this.ruleForm.hotelName,
          roomType: this.ruleForm.room_type
        }
      })
      // this.$router.go(0)
      // 跳转到评价界面
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
