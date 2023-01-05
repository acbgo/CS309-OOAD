<template>
  <el-menu text-color="#fff"
           background-color="#2c3e50"
           active-text-color="#ffd04b"
           router
           style="height: 100%">
    <div style="height: 60px;line-height: 80px;text-align: center">
      <img src="../assets/logo1.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px;">
      <b style="color: white;font-size: 20px">{{hotelName}}</b>
    </div>
    <el-menu-item index="/room" style="margin-top: 5px">
      <template slot="title"><i class="el-icon-message"></i><span style="font-size: 16px">房间基本信息</span></template>
    </el-menu-item>
    <el-menu-item index="/roomType" style="margin-top: 5px">
      <template slot="title"><i class="el-icon-message"></i><span style="font-size: 16px">房型管理</span></template>
    </el-menu-item>
    <el-menu-item index="/orders">
      <template slot="title"><i class="el-icon-menu"></i><span style="font-size: 16px">订单管理</span></template>
    </el-menu-item>
    <el-menu-item index="/analyse">
      <template slot="title"><i class="el-icon-setting"></i><span style="font-size: 16px">数据分析</span></template>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'Aside',
  props: {
    hotelName: String
  }
}
</script>

<style scoped>

</style>
