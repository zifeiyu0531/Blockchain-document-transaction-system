<style lang="scss" scoped>
.banner {
  text-align: center;
}
.banner-desc {
  padding-top: 20px;

  h1 {
    font-size: 34px;
    margin: 0;
    line-height: 48px;
    color: #555;
  }

  p {
    font-size: 18px;
    line-height: 28px;
    color: #888;
    margin: 10px 0 5px;
  }
}
@keyframes fade-in {
  0% {
    opacity: 0;
  } /*初始状态 透明度为0*/
  40% {
    opacity: 0;
  } /*过渡状态 透明度为0*/
  100% {
    opacity: 1;
  } /*结束状态 透明度为1*/
}
@-webkit-keyframes fade-in {
  /*针对webkit内核*/
  0% {
    opacity: 0;
  }
  40% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
.jumbotron {
  animation: fade-in; /*动画名称*/
  animation-duration: 1s; /*动画持续时间*/
  -webkit-animation: fade-in 1s; /*针对webkit内核*/
  width: 890px;
  margin: 30px auto;
  position: relative;
  img {
    width: 90%;
  }
}
.cards {
  animation: fade-in; /*动画名称*/
  animation-duration: 1s; /*动画持续时间*/
  -webkit-animation: fade-in 1s; /*针对webkit内核*/
  margin: 60px auto 110px;
  width: auto;

  .container {
    padding: 0;
    margin: 0 auto;
    width: auto;
    &::before,
    &::after {
      display: table;
      content: "";
    }
    &::after {
      clear: both;
    }
  }

  li {
    width: 33.33333%;
    padding: 0 19px;
    box-sizing: border-box;
    float: left;
    list-style: none;
  }

  img {
    width: 100px;
    height: 75px;
  }
}
.small-text {
  margin-top: 20px;
}
.card {
  height: 530px;
  width: 90%;
  background: #ffffff;
  border: 1px solid #eaeefb;
  border-radius: 5px;
  box-sizing: border-box;
  text-align: center;
  position: relative;
  transition: all 0.3s ease-in-out;
  bottom: 0;

  img {
    margin: 66px auto 60px;
    transform: scaleY(1.3);
  }
  h3 {
    margin: 0;
    font-size: 18px;
    color: #1f2f3d;
    font-weight: normal;
  }
  p {
    font-size: 14px;
    color: #99a9bf;
    padding: 0 25px;
    line-height: 20px;
  }
  a {
    height: 53px;
    line-height: 52px;
    font-size: 14px;
    color: #409eff;
    text-align: center;
    border: 0;
    border-top: 1px solid #eaeefb;
    padding: 0;
    cursor: pointer;
    width: 100%;
    position: absolute;
    bottom: 0;
    left: 0;
    background-color: #fff;
    border-radius: 0 0 5px 5px;
    transition: all 0.3s;
    text-decoration: none;
    display: block;

    &:hover {
      color: #fff;
      background: #409eff;
    }
  }
  &:hover {
    bottom: 6px;
    box-shadow: 0 6px 18px 0 rgba(232, 237, 250, 0.5);
  }
}
@media (max-width: 1140px) {
  .cards {
    width: 100%;
    .container {
      width: 100%;
      margin: 0;
    }
  }
  .banner .container {
    width: 100%;
    box-sizing: border-box;
  }
  .banner img {
    right: 0;
  }
}

@media (max-width: 1000px) {
  .banner .container {
    img {
      display: none;
    }
  }
  .jumbotron {
    display: none;
  }
}

@media (max-width: 768px) {
  .cards {
    li {
      width: 80%;
      margin: 0 auto 20px;
      float: none;
    }
    .card {
      height: auto;
      padding-bottom: 54px;
    }
  }
  .banner-stars {
    display: none;
  }
  .banner-desc {
    #line2 {
      display: none;
    }
    h2 {
      font-size: 32px;
    }
    p {
      width: auto;
    }
  }
}
.theme-intro-b {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 200;
  .intro-banner {
    position: absolute;
  }
  img {
    width: 300px;
  }
  .title {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    color: #fff;
    text-align: center;
    font-weight: bold;
    font-size: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    p {
      padding: 0;
      margin: 10px 0;
    }
  }
}
.theme-intro-a {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 200;
  .mask {
    position: fixed;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background: #000;
    opacity: 0.5;
  }
  .intro-banner {
    top: 50%;
    left: 50%;
    position: fixed;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    box-sizing: border-box;
    text-align: center;
    z-index: 100;
    img {
      width: 100%;
    }
    .intro-text {
      position: absolute;
      top: 50%;
      left: 0;
      right: 0;
      p {
        padding: 0;
        margin: 0;
        font-size: 48px;
        font-weight: bold;
        color: #fff;
      }
    }
  }
}
</style>
<template>
  <div>
    <div class="banner">
      <div class="banner-desc">
        <h1>技术文档交易平台</h1>
        <p>Mannikin，一个基于区块链的文档交易平台</p>
      </div>
    </div>
    <div class="jumbotron" ref="indexMainImg">
      <img src="../assets/index_big.png" alt />
    </div>

    <div class="cards">
      <ul class="container">
        <li>
          <div class="card">
            <img src="../assets/doc.png" alt />
            <h3>文档市场</h3>
            <p class="small-text">在文鸟，和世界分享你的知识</p>
            <router-link active-class="active" to="/doc" exact>查看详情</router-link>
          </div>
        </li>
        <li>
          <div class="card">
            <img src="../assets/point.png" alt />
            <h3>积分市场</h3>
            <p class="small-text">浏览积分实时价格走势，参与积分买卖，赚取现金</p>
            <router-link active-class="active" to="/point" exact>查看详情</router-link>
          </div>
        </li>
        <li>
          <div class="card">
            <img src="../assets/user.png" alt />
            <h3>我的文鸟</h3>
            <p class="small-text">查看个人资料，获取交易信息</p>
            <router-link active-class="active" to="/user" exact>查看详情</router-link>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  name: "index",
  data() {
    return {
      mainImgOffset: 0,
    };
  },
  computed: {
    isLogin() {
      return this.$store.state.isLogin;
    },
  },
};
</script>
