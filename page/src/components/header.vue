<style lang="scss" scoped>
  .headerWrapper {
    height: 80px;
  }

  .header {
    height: 80px;
    background-color: #fff;
    color: #fff;
    top: 0;
    left: 0;
    width: 100%;
    line-height: 80px;
    z-index: 100;
    position: relative;

    .container {
      height: 100%;
      box-sizing: border-box;
      border-bottom: 1px solid #DCDFE6;
    }
	
	.nav-logo {
	  width: 220px;
	  margin-top: 10px;
	}
	
	.nav-logo-small {
	  width: 50px;
	}

    h1 {
      margin: 0;
      float: left;
      font-size: 32px;
      font-weight: normal;

      a {
        color: #333;
        text-decoration: none;
        display: block;
      }

      span {
        font-size: 12px;
        display: inline-block;
        width: 34px;
        height: 18px;
        border: 1px solid rgba(255, 255, 255, .5);
        text-align: center;
        line-height: 18px;
        vertical-align: middle;
        margin-left: 10px;
        border-radius: 3px;
      }
    }

    .nav {
      float: right;
      height: 100%;
      line-height: 80px;
      background: transparent;
      padding: 0;
      margin: 0;
      &::before, &::after {
        display: table;
        content: "";
      }
      &::after {
        clear: both;
      }
    }
	
    .nav-logo,
    .nav-logo-small {
      vertical-align: sub;
    }

    .nav-logo-small {
      display: none;
    }

    .nav-item {
      margin: 0;
      float: left;
	  font-size: 18px;
      list-style: none;
      position: relative;
      cursor: pointer;
    
      &.lang-item,
      &:last-child {
        cursor: default;

        span {
          opacity: .8;
        }

        .nav-lang {
          cursor: pointer;
          display: inline-block;
          height: 100%;
          color: #888;

          &:hover {
            color: #409EFF;
          }
          &.active {
             font-weight: bold;
             color: #409EFF;
           }
        }
      }

      a {
        text-decoration: none;
        color: #1989FA;
        opacity: 0.5;
        display: block;
        padding: 0 22px;

        &.active,
        &:hover {
          opacity: 1;
        }

        &.active::after {
          content: '';
          display: inline-block;
          position: absolute;
          bottom: 0;
          left: calc(50% - 30px);
          width: 60px;
          height: 2px;
          background: #409EFF;
        }
      }
    }
  }

  
  @media (max-width: 850px) {
    .header {
      .nav-logo {
        display: none;
      }
      .nav-logo-small {
        display: inline-block;
      }
      .nav-item {
		font-size: 16px;
        margin-left: 6px;

        &.lang-item,
        &:last-child {
          margin-left: 10px;
        }
         
        a {
          padding: 0 5px;
        }
      }
    }
  }

  @media (max-width: 700px) {
    .header {
      .container {
        padding: 0 12px;
      }
      .nav-item {
        a {
          font-size: 12px;
          vertical-align: top;
        }

        &.lang-item {
          height: 100%;
         
          .nav-lang {
            display: flex;
            align-items: center;
            
            span {
              padding-bottom: 0;
            }
          }
        }
      }
    }
  }
</style>
<template>
  <div class="headerWrapper">
    <header class="header" ref="header">
      <div class="container">
        <h1><router-link to="/index">
          <!-- logo -->
          <slot>
            <img
              src="../assets/wenniao_logo.png"
              alt="element-logo"
              class="nav-logo">
            <img
              src="../assets/wenniao_logo_small.png"
              alt="element-logo"
              class="nav-logo-small">
          </slot>

        </router-link></h1>

        <!-- nav -->
        <ul class="nav">
          <li class="nav-item">
            <router-link
              active-class="active"
              to="/search">文章市场
            </router-link>
          </li>
          <li class="nav-item">
            <router-link
              active-class="active"
              to="/point">积分市场
            </router-link>
          </li>
          <li 
            class="nav-item">
            <router-link
			  v-if="isLogin"
              active-class="active"
              to="/user">个人中心
            </router-link>
          </li>
		  <li
		    class="nav-item">
			  <router-link 
			    v-if="!isLogin"
			    active-class="active"
			    to="/login">
				登录/注册
			  </router-link>
		  </li>
        </ul>
      </div>
    </header>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        active: '',
      };
	},
	computed: {
		isLogin () {
			return this.$store.state.isLogin
		}
	}
}
	
</script>
