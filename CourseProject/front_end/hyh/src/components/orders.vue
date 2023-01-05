<template>
  <div>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-input
        v-model="search"
        style="width: 200px;position: relative;left: 950px;top: -20px"
        placeholder="输入用户名搜索"/>
      <el-button  type="primary" icon="el-icon-search"
                  style="margin-left: 5px;position: relative;left: 950px;top: -20px" @click="handleSearch()">搜索</el-button>
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
        label="用户名"
        prop="username">
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
        label="房间类型"
        prop="room_type">
      </el-table-column>
      <el-table-column
        label="身份证"
        prop="idcard">
      </el-table-column>
      <el-table-column
        label="手机号码"
        prop="phoneNumber">
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
        <el-form-item label="房间号" prop="roomNumber">
          <el-input v-model="ruleForm.roomNumber" placeholder="输入的房间号为整数"></el-input>
        </el-form-item>
        <el-form-item  label="支付金额" prop="price">
          <el-input  v-model="ruleForm.price" placeholder="请输入不多于两位小数的数字"></el-input>
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
  name: 'orders',
  data () {
    return {
      tableData: [{
        id: '1',
        roomNumber: '423',
        username: 'cjy',
        price: '120',
        beginData: '',
        endData: '',
        room_type: '总统套房',
        idcard: '',
        phoneNumber: ''
      },
      {
        id: '2',
        roomNumber: '425',
        username: 'cjy',
        price: '130',
        beginData: '',
        endDate: '',
        room_type: '总统套房',
        idcard: '',
        phoneNumber: ''
      }],
      ruleForm: {
        id: '',
        roomNumber: '',
        username: '',
        price: '',
        beginData: '',
        endData: '',
        room_type: '',
        idcard: '',
        phoneNumber: ''
      },
      rules: {
        roomNumber: [
          { required: true, message: '请输入房间号', trigger: 'blur' },
          { pattern: /^\d*$/, message: '请输入整数', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
          { pattern: /^\d+(.\d{2})?$/, message: '请输入不多于两位小数的数字', trigger: 'blur' }
        ]
      },
      headerBg: 'headerBg',
      currentPage: 1,
      total: 20,
      pageSize: 5,
      dialogFormVisible: false,
      operation: '',
      hotelName: '深大分店',
      h_id: '',
      search: ''
    }
  },
  mounted () {
    this.h_id = 2
    this.receive()
  },
  methods: {
    receive () {
      axios.get('http://10.24.168.173:8181/findOrders?h_id=' + this.h_id).then(res => {
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
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url = 'http://10.24.168.173:8181/updateOrder?HotelName=' + this.hotelName +
            '&RoomNumber=' + this.ruleForm.roomNumber + '&username=' + this.ruleForm.username + '&price=' + this.ruleForm.price +
            '&room_type=' + this.ruleForm.room_type +
            '&idcard=' + this.ruleForm.idcard + '&phoneNumber=' + this.ruleForm.phoneNumber + '&h_id=' + this.h_id +
            '&id=' + this.ruleForm.id
          console.log(url)
          this.send(this.ruleForm, url)
          this.reload()
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
      const url = 'http://10.24.168.173:8181/deleteOrder?id=' + row.id
      console.log(url)
      this.operation = '删除'
      this.send(row, url)
      this.reload()
    },
    handleSearch () {
      const url = 'http://10.24.168.173:8181/findO?username=' + this.search + '&h_id=' + this.h_id
      axios.post(url, this.tableData).then((res) => {
        this.tableData = res.data
      })
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
