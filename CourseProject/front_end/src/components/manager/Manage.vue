<template>
  <div>
    <el-container style="height: 100%">
      <el-aside width="200px" style="height: 760px">
        <Aside :hotelName="hotelName"/>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 18px;height: 60px;line-height: 60px">
          <i class="el-icon-user-solid" style="font-size: 20px"></i>
          <el-dropdown style="cursor: pointer">
            <span style="font-size: 18px">{{ name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="dialogShow">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <!--  <Top :name="name"/>-->
        </el-header>
        <el-main>
          <router-view v-if="isRouterAlive"/>
        </el-main>
      </el-container>
    </el-container>
    <el-dialog title="用户信息" :visible="dialogVisible" width="500px" @close="dialogClose">
      <span style="font-size: 20px; font-weight: bold; display: block">用户名: {{name}}</span>
      <span style="font-size: 20px; font-weight: bold; display: block">手机号: {{phone}}</span>
    </el-dialog>
  </div>

</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-container {
  background-color: #ecf3f8
}
</style>

<script>
import Aside from './Aside'

export default {
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      name: '孙兵',
      phone: 12345678910,
      hotelName: '南山分店',
      isRouterAlive: true,
      dialogVisible: false,
      h_id: 0
    }
  },
  mounted () {
    this.name = sessionStorage.getItem('loginUser')
    this.hotelName = sessionStorage.getItem('managerHotelName')
    this.h_id = sessionStorage.getItem('managerH_id')
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    },
    dialogShow () {
      this.dialogVisible = true
    },
    dialogClose () {
      this.dialogVisible = false
    },
    logout () {
      this.$message.success('退出成功')
      this.$router.push({
        name: 'roomShow'
      })
    }
  },
  components: {Aside}
}
</script>
