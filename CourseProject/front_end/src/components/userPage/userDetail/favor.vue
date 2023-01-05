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
        label="酒店图片" align="center">
        <template slot-scope="scope">
          <el-popover placement="right" trigger="hover">
            <el-image slot="reference" :src="_img(scope.$index)" width="80" height="80"/>
            <el-image style="width: 250px; height: 250px" :src="_img(scope.$index)" fit="cover"/>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="酒店名称"
        prop="name">
      </el-table-column>
      <el-table-column
        label="酒店评价等级"
        prop="score">
      </el-table-column>
      <el-table-column
        label="酒店地址"
        prop="location">
      </el-table-column>
      <el-table-column
        label="酒店描述"
        prop="remark">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            style="margin-left: 10px"
            @click="pageJump(scope.$index, scope.row)">预定<i class="el-icon-edit"></i></el-button>
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
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'favor',
  data () {
    return {
      tableData: [{
        id: '1',
        username: 'cjy',
        graphPath: '',
        name: '深大分店',
        score: '4',
        location: '南方科技大学',
        remark: '真好啊！'
      }],
      userName: '',
      headerBg: 'headerBg',
      currentPage: 1,
      total: 20,
      pageSize: 5
    }
  },
  mounted () {
    this.receive()
  },
  methods: {
    receive () {
      this.userName = sessionStorage.getItem('loginUser')
      let url = `http://10.24.3.53:8181/getFav?username=${this.userName}`
      console.log(url)
      axios.get(url).then(res => {
        this.tableData = res.data
      })
    },
    send (data, url) {
      axios.post(url, data).then((res) => {
        console.log(res.data)
      })
    },
    _img (index) {
      return require('../../../assets/room/' + this.tableData[index].graphPath)
    },
    pageJump (index, row) {
      this.ruleForm = row
      this.$router.push(
        {
          name: 'roomShow',
          params: {
            id: row.id,
            name: row.name,
            position: row.location,
            from: 'favor'
          }
        })
      // 跳转到酒店预定界面
    },
    handleDelete (index, row) {
      const url = 'http://10.24.3.53:8181/deleteFav?username=' + this.userName + '&h_id=' + row.id
      console.log(url)
      this.send(row, url)
      this.$alert('恭喜你，删除成功！', '删除', {confirmButtonText: '确定', callback: action => { this.$router.go(0) }}
      )
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
.headerBg {
  background: #eee !important;
}
</style>
