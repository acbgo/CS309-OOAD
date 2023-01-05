<template>
  <div id="FashionClothes" style="overflow-y:scroll;overflow-x:hidden;height:500px">
    <!-- 潮流衣服 -->
    <div>
      <img src="../../../assets/imgs/mall/hq.jpg">
      <p>
        旅行机票(可达世界各地)
        <br>
        <br>
        所需积分:{{goods.first}}
      </p>
      <p>
<!--        <el-button type="text" class="button" icon="el-icon-star-off">添加收藏</el-button>-->
        <el-button type="text" class="button" icon="el-icon-goods" @click="change(goods.first)">积分兑换</el-button>
      </p>
    </div>
    <div>
      <img src="../../../assets/imgs/mall/yn.jpg">
      <p>
        云南三日游家庭套餐
        <br>
        <br>
        所需积分:{{goods.second}}
      </p>
      <p>
<!--        <el-button type="text" class="button" icon="el-icon-star-off">添加收藏</el-button>-->
        <el-button type="text" class="button" icon="el-icon-goods" @click="change(goods.second)">积分兑换</el-button>
      </p>
    </div>
    <div>
      <img src="../../../assets/imgs/mall/QQSVIP.jpg">
      <p>
        QQ超级会员一个月
        <br>
        <br>
        所需积分:{{goods.third}}
      </p>
      <p>
<!--        <el-button type="text" class="button" icon="el-icon-star-off">添加收藏</el-button>-->
        <el-button type="text" class="button" icon="el-icon-goods" @click="change(goods.third)">积分兑换</el-button>
      </p>
    </div>
    <div>
      <img src="../../../assets/imgs/mall/QQlz.jpg">
      <p>
        QQ豪华绿钻一年
        <br>
        <br>
        所需积分:{{goods.forth}}
      </p>
      <p>
<!--        <el-button type="text" class="button" icon="el-icon-star-off">添加收藏</el-button>-->
        <el-button type="text" class="button" icon="el-icon-goods" @click="change(goods.forth)">积分兑换</el-button>
      </p>
    </div>
    <div>
      <img src="../../../assets/imgs/mall/40yhq.jpg">
      <p>
        40元酒店代金券
        <br>
        <br>
        所需积分:{{goods.fifth}}
      </p>
      <p>
<!--        <el-button type="text" class="button" icon="el-icon-star-off">添加收藏</el-button>-->
        <el-button type="text" class="button" icon="el-icon-goods" @click="change(goods.fifth)">积分兑换</el-button>
      </p>
    </div>
    <div>
      <img src="../../../assets/imgs/mall/100yhq.jpg">
      <p>
        100元酒店代金券
        <br>
        <br>
        所需积分:{{goods.sixth}}
      </p>
      <p>
<!--        <el-button type="text" class="button" icon="el-icon-star-off">添加收藏</el-button>-->
        <el-button type="text" class="button" icon="el-icon-goods" @click="change(goods.sixth)">积分兑换</el-button>
      </p>
    </div>
  </div>
</template>

<script>

import axios from 'axios'

export default {
  name: 'mall',
  data () {
    return {
      goods: {
        first: 50000,
        second: 39999,
        third: 600,
        forth: 7000,
        fifth: 1500,
        sixth: 3200
      },
      tableData: {
        username: 'abc',
        points: 99,
        email: ''
      }
    }
  },
  mounted () {
    this.tableData.username = sessionStorage.getItem('loginUser')
    let url = `http://10.24.3.53:8181/getUserInfo?username=${this.tableData.username}`
    axios.get(url).then(res => {
      this.tableData = res.data[0]
    })
  },
  methods: {
    change (point) {
      if (this.tableData.points >= point) {
        const data = this.tableData.points - point
        let url = 'http://10.24.3.53:8181/points?username=' + this.tableData.username + '&points=' + data
        axios.post(url, data).then((res) => {
          console.log(res.data)
        })
        this.$alert('恭喜你兑换成功,请注意查看邮件！', '积分兑换', {
          confirmButtonText: '确定',
          callback: action => {
            this.$router.go(0)
          }
        }
        )
        url = 'http://10.24.3.53:8181/mail?to=' + this.tableData.email +
          '&context=恭喜您，成功兑换积分商品！我们将会在三天内将商品发往您的邮箱，请注意查收！商品兑换后，您当前剩余积分为' + data + '。' +
          '&username=' + this.tableData.username
        axios.post(url, this.tableData).then((res) => {
          console.log(res.data)
        })
      } else {
        this.$alert('对不起，你的积分不足，兑换失败！', '积分兑换', {
          confirmButtonText: '确定'
        })
      }
    }
  }
}
</script>

<style scoped>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

#FashionClothes>div {
  float: left;
  width: 200px;
  height: 270px;
  margin: 10px 10px 13px 12px;
  padding: 8px;
  border: 1px solid #919191;
  transition: all 0.5s;
}

#FashionClothes>div:hover {
  border: 2px solid #ff0000;
  transform: translate(0, -10px);
}

#FashionClothes>div>img {
  width: 180px;
  height: 165px;
}

#FashionClothes>div>p {
  width: 180px;
  font-size: 13px;
  text-align: justify;
  /* text-indent: 2rem; */
}

#FashionClothes>div>p:nth-of-type(1) {
  overflow: hidden;
  width: 180px;
  height: 60px;
  margin-top: 5px;
}

#FashionClothes>div>p:nth-of-type(2) {
  position: relative;
  margin-top: 2px;
  text-align: center;
}
.el-button:hover{
  color: #1cce66;
}
</style>
