<template>
  <div>
    <h2 style="margin-top: 50px">点击进行预定</h2>
    <img src="../../assets/imgs/floor1.png" style="height: 600px">
    <!--    <div style="position: absolute; top: 130px; left: 246px; height: 163px; width: 103px; background-color: #00CED1"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 352px; height: 163px; width: 107px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 465px; height: 163px; width: 108px; background-color: #222222"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 578px; height: 163px; width: 105px; background-color: antiquewhite"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 688px; height: 163px; width: 116px; background-color: burlywood"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 808px; height: 163px; width: 113px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 925px; height: 163px; width: 125px; background-color: #00CED1"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 1055px; height: 163px; width: 115px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 1175px; height: 163px; width: 110px; background-color: #00CED1"></div>-->

    <!--    <div style="position: absolute; top: 130px; left: 246px; height: 163px; width: 103px;" @click="Click101"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 352px; height: 163px; width: 107px;" @click="Click102"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 465px; height: 163px; width: 108px;" @click="Click103"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 578px; height: 163px; width: 105px;" @click="Click104"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 688px; height: 163px; width: 116px;" @click="Click105"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 808px; height: 163px; width: 113px;" @click="Click106"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 925px; height: 163px; width: 125px;" @click="Click107"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 1055px; height: 163px; width: 115px;" @click="Click108"></div>-->
    <!--    <div style="position: absolute; top: 130px; left: 1175px; height: 163px; width: 110px;" @click="Click109"></div>-->

    <!--    <div style="position: absolute; top: 418px; left: 243px; height: 150px; width: 140px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 388px; height: 150px; width: 154px; background-color: #00CED1"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 547px; height: 150px; width: 166px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 812px; height: 150px; width: 210px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 1025px; height: 150px; width: 257px; background-color: #00CED1"></div>-->

    <!--    <div style="position: absolute; top: 418px; left: 243px; height: 150px; width: 140px;" @click="Click110"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 388px; height: 150px; width: 154px;" @click="Click111"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 547px; height: 150px; width: 166px;" @click="Click112"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 812px; height: 150px; width: 210px;" @click="Click113"></div>-->
    <!--    <div style="position: absolute; top: 418px; left: 1025px; height: 150px; width: 257px;" @click="Click114"></div>-->

    <!--    <div style="position: absolute; top: 110px; left: 255px; height: 200px; width: 145px; background-color: #00CED1"></div>-->
    <!--    <div style="position: absolute; top: 110px; left: 460px; height: 205px; width: 148px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 105px; left: 670px; height: 200px; width: 370px; background-color: #222222"></div>-->
    <!--    <div style="position: absolute; top: 108px; left: 1120px; height: 200px; width: 145px; background-color: antiquewhite"></div>-->
    <!--    <div style="position: absolute; top: 460px; left: 250px; height: 240px; width: 310px; background-color: #00CED1"></div>-->
    <!--    <div style="position: absolute; top: 487px; left: 605px; height: 205px; width: 150px; background-color: #0e4f66"></div>-->
    <!--    <div style="position: absolute; top: 492px; left: 815px; height: 205px; width: 163px; background-color: #222222"></div>-->
    <!--    <div style="position: absolute; top: 485px; left: 1035px; height: 210px; width: 234px; background-color: antiquewhite"></div>-->

    <!--1：标准单人间；2：标准大床房；3：总统套房；4：经济单人间；5：三床房；6：标准双床房；7：豪华双人间    -->
    <div style="position: absolute; top: 110px; left: 255px; height: 200px; width: 145px;" @click="whichRoom(1)"></div>
    <div style="position: absolute; top: 110px; left: 460px; height: 205px; width: 148px;" @click="whichRoom(2)"></div>
    <div style="position: absolute; top: 105px; left: 670px; height: 200px; width: 370px;" @click="whichRoom(3)"></div>
    <div style="position: absolute; top: 108px; left: 1120px; height: 200px; width: 145px;" @click="whichRoom(4)"></div>
    <div style="position: absolute; top: 460px; left: 250px; height: 240px; width: 310px;" @click="whichRoom(5)"></div>
    <div style="position: absolute; top: 487px; left: 605px; height: 205px; width: 150px;" @click="whichRoom(1)"></div>
    <div style="position: absolute; top: 492px; left: 815px; height: 205px; width: 163px;" @click="whichRoom(6)"></div>
    <div style="position: absolute; top: 485px; left: 1035px; height: 210px; width: 234px;" @click="whichRoom(7)"></div>

  </div>
</template>

<script>
export default {
  name: 'floor',
  data () {
    return {
      h_id: 0,
      hotelName: '',
      hotelPosition: '',
      roomType: '',
      price: '',
      dateRange: ''
    }
  },
  mounted () {
    this.h_id = this.$route.params.h_id
    this.hotelName = this.$route.params.hotelName
    this.hotelPosition = this.$route.params.hotelPosition
    this.dateRange = this.$route.params.dateRange
  },
  methods: {
    whichRoom (type) {
      if (type === 1) {
        this.go(72, '标准单人间')
      } else if (type === 2) {
        this.go(220, '标准大床房')
      } else if (type === 3) {
        this.go(425, '总统套房')
      } else if (type === 4) {
        this.go(80, '经济单人间')
      } else if (type === 5) {
        this.go(308, '三床房')
      } else if (type === 6) {
        this.go(250, '标准双床房')
      } else if (type === 7) {
        this.go(210, '豪华双人间')
      }
    },
    go (price, roomType) {
      this.$router.push({
        name: 'Book',
        params: {
          h_id: this.h_id,
          hotelName: this.hotelName,
          position: this.hotelPosition,
          dataRange: this.dateRange,
          price: price,
          roomType: roomType
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
