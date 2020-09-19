<template>
  <div class="container">
    <el-tabs class="userbox" type="border-card">
      <el-tab-pane label="登录">
        <div class="formbox">
          <loginform></loginform>
        </div>
      </el-tab-pane>
      <el-tab-pane label="注册">
        <div class="formbox">
          <registerform></registerform>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import loginform from './loginform.vue'
import registerform from './registerform.vue'
export default {
  name: 'login',
  components: {
    loginform,
    registerform
  },
  data () {
    return {
    }
  },
}
</script>

<style scoped>
.userbox {
  width:70%;
  min-width:470px;
  margin:35px auto;
}
.formbox {
  width:450px;
  margin:0 auto;
}
</style>
