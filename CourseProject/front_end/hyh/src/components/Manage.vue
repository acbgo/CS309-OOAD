<template>
<el-container style="height: 100%" >
  <el-aside width="200px" style="height: 760px" >
  <Aside :hotelName="hotelName" />
  </el-aside>

<el-container>
  <el-header style="text-align: right; font-size: 18px;height: 60px;line-height: 60px">
    <i class="el-icon-user-solid" style="font-size: 20px"></i>
  <Top :name="name"/>
  </el-header>
  <el-main>
    <router-view v-if="isRouterAlive"/>
  </el-main>
</el-container>
</el-container>
</template>

<style >
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
.el-container{
  background-color:#ecf3f8
}
</style>

<script>
import Aside from './Aside'
import Top from './Top'

export default {
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      name: '孙兵',
      hotelName: '南山分店',
      isRouterAlive: true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  },
  components: {Aside, Top}
}
</script>
