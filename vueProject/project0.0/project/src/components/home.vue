<template>
  <!--  <el-card>-->
  <!--    <el-image :src="require('../assets/logo.png')" :fit="fit"></el-image>-->
  <!--    <el-col>-->
  <!--      <el-button type="primary" round>log in</el-button>-->
  <!--      <el-button type="primary" round>sign up</el-button>-->
  <!--    </el-col>-->
  <!--  </el-card>-->
  <div class="test" :style="test">
  </div>
</template>

<script>
export default {
  data () {
    return {
      test: {
        backgroundImage: 'url(' + require('../assets/test.png') + ')',
        backgroundRepeat: 'no-repeat',
        backgroundSize: '25px auto',
        marginTop: '5px'
      }
    }
  }
}
</script>

<style scoped>

</style>
