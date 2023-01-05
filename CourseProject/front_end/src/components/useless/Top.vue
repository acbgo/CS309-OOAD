<template>
  <el-dropdown style="cursor: pointer">
    <span style="font-size: 18px">{{ name }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item @click="dialogShow">个人信息</el-dropdown-item>
      <el-dropdown-item>退出</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
export default {
  name: 'Top',
  data () {
    return {
      dialogVisible: false
    }
  },
  props: {
    name: String
  },
  methods: {
    dialogShow () {
      this.dialogVisible = true
    }
  }

}
</script>

<style scoped>

</style>
