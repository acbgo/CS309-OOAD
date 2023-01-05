<template>
  <div>
    <div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item>房型管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button style="position: relative;left: 550px;top: -20px"
                 type="primary"
                 @click="handleAdd"
                 icon="el-icon-circle-plus-outline">添加房型</el-button>
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
        label="房间类型"
        prop="room_type">
      </el-table-column>
      <el-table-column
        label="面积(㎡)"
        prop="area">
      </el-table-column>
      <el-table-column
        label="床数"
        prop="bed_number">
      </el-table-column>
      <el-table-column
        label="价格(元)"
        prop="price">
      </el-table-column>
      <el-table-column
        label="折扣"
        prop="discount">
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
    <el-dialog :visible.sync="dialogFormVisibleAdd" title= "Operation">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleFormAdd">
        <el-form-item  label="房间类型" prop="room_type">
          <el-input v-model="ruleForm.room_type" placeholder="输入需要添加的房型"></el-input>
        </el-form-item>
        <el-form-item label="面积" prop="area">
          <el-input v-model="ruleForm.area" placeholder="请输入不多于两位小数的数字"></el-input>
        </el-form-item>
        <el-form-item  label="床数" prop="bed_number">
          <el-input  v-model="ruleForm.bed_number" placeholder="请输入整数"></el-input>
        </el-form-item>
        <el-form-item  label="价格" prop="price">
          <el-input  v-model="ruleForm.price" placeholder="请输入不多于两位小数的数字"></el-input>
        </el-form-item>
        <el-form-item  label="折扣" prop="discount">
          <el-input  v-model="ruleForm.discount" placeholder="请输入不大于1的数"></el-input>
        </el-form-item>
        <el-form-item  label="服务设施" prop="kind">
          <el-input  v-model="ruleForm.kind" placeholder="请输入8位二进制数"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisibleEdit" title= "Operation">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleFormEdit">
        <el-form-item label="面积" prop="area">
          <el-input v-model="ruleForm.area" placeholder="请输入不多于两位小数的数字"></el-input>
        </el-form-item>
        <el-form-item  label="床数" prop="bed_number">
          <el-input  v-model="ruleForm.bed_number" placeholder="请输入整数"></el-input>
        </el-form-item>
        <el-form-item  label="价格" prop="price">
          <el-input  v-model="ruleForm.price" placeholder="请输入不多于两位小数的数字"></el-input>
        </el-form-item>
        <el-form-item  label="折扣" prop="discount">
          <el-input  v-model="ruleForm.discount" placeholder="请输入不大于1的数"></el-input>
        </el-form-item>
        <el-form-item  label="服务设施" prop="kind">
          <el-input  v-model="ruleForm.kind" placeholder="请输入8位二进制数"></el-input>
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
  name: 'roomType',
  data () {
    return {
      tableData: [{
        id: '1',
        room_type: '标准单人间',
        area: '50',
        bed_number: '1',
        price: '120',
        discount: '1',
        kind: '11110000'
      },
      {
        id: '1',
        room_type: '总统套房',
        area: '150',
        bed_number: '3',
        price: '1000',
        discount: '1',
        kind: '11111111'
      }],
      ruleForm: {
        id: '',
        room_type: '',
        area: '',
        bed_number: '',
        price: '',
        discount: '',
        kind: ''
      },
      rules: {
        room_type: [
          { required: true, message: '请输入需要添加的房型', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' },
          { pattern: /^\d+(.\d{2})?$/, message: '请输入不多于两位小数的数字', trigger: 'blur' }
        ],
        discount: [
          { required: true, message: '请输入折扣', trigger: 'blur' },
          { pattern: /^(0.\d+|0|1)$/, message: '请输入不大于1的数', trigger: 'blur' }
        ],
        bed_number: [
          { required: true, message: '请输入房间床数', trigger: 'blur' },
          { pattern: /^\d*$/, message: '请输入整数', trigger: 'blur' }
        ],
        area: [
          { required: true, message: '请输入房间面积', trigger: 'blur' },
          { pattern: /^\d+(.\d{2})?$/, message: '请输入不多于两位小数的数字', trigger: 'blur' }
        ],
        kind: [
          { required: true, message: '请输入8位二进制数', trigger: 'blur' },
          { pattern: /^[0-1]{1,8}$/, message: '请输入8位二进制数', trigger: 'blur' }
        ]
      },
      headerBg: 'headerBg',
      currentPage: 1,
      total: 20,
      pageSize: 5,
      dialogFormVisibleAdd: false,
      dialogFormVisibleEdit: false,
      operation: ''
    }
  },
  mounted () {
    this.receive()
  },
  methods: {
    receive () {
      axios.get('http://10.24.3.53:8181/findallroomtype').then(res => {
        this.tableData = res.data
        console.log(res.data)
        console.log(this.tableData)
      })
    },
    send (data, url) {
      axios.post(url, data).then((res) => {
        console.log('数据：', res.data)
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
      this.ruleForm = {
        id: '',
        room_type: '',
        area: '',
        bed_number: '',
        price: '',
        discount: '',
        kind: ''
      }
      this.dialogFormVisibleAdd = true
      this.operation = '添加'
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.operation === '修改') {
            // 发送修改后的ruleForm
            const url = 'http://10.24.3.53:8181/updateRoomType?room_type=' + this.ruleForm.room_type +
              '&area=' + this.ruleForm.area + '&bed_number=' + this.ruleForm.bed_number + '&price=' +
              this.ruleForm.price + '&discount=' + this.ruleForm.discount + '&kind=' + this.ruleForm.kind + '&id=' +
              this.ruleForm.id
            this.send(this.ruleForm, url)
            this.reload()
            // 接受返回的数据，如果数据合理则关闭表单显示数据，否则表单不关闭
          }
          if (this.operation === '添加') {
            // 发送添加的ruleForm
            const url = 'http://10.24.3.53:8181/insertRoomType?room_type=' + this.ruleForm.room_type +
              '&area=' + this.ruleForm.area + '&bed_number=' + this.ruleForm.bed_number + '&price=' +
              this.ruleForm.price + '&discount=' + this.ruleForm.discount + '&kind=' + this.ruleForm.kind
            this.send(this.ruleForm, url)
            this.reload()
            // 接受返回的数据，关闭表单显示数据
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
      this.dialogFormVisibleEdit = true
      this.operation = '修改'
    },
    handleDelete (index, row) {
      // this.tableData.splice(index, 1)
      // 发送删除的数据
      const url = 'http://10.24.3.53:8181/deleteRoomType?id=' + row.id
      this.operation = '删除'
      this.send(row, url)
      this.reload()
      // console.log(index, row)
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
