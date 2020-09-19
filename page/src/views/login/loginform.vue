<template>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" id="f" class="demo-ruleForm">
    <el-form-item label="用户名/手机" prop="name" class="sf">
      <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="pass" class="sf">
      <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item class="sf">
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {userLogin} from "../../network/user";

export default {
  name: 'loginform',
  data () {
    var validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'))
      } else {
        callback()
      }
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        name: '',
        password: ''
      },
      rules: {
        name: [
          { validator: validateName, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
		  userLogin(this.ruleForm.name, this.ruleForm.password)
            .then((response) => {
              if (response.userInfo != null) {
				this.$store.dispatch('setStatus', true)
				localStorage.setItem('userId', response.userInfo.userId)
				localStorage.setItem('userName', response.userInfo.userName)
				localStorage.setItem('userPhone', response.userInfo.userPhone)
				localStorage.setItem('userPoint', response.userInfo.userPoint)
				localStorage.setItem('userArticle', response.userArticle)
                this.$message.success({
                  message: '登录成功',
                  showClose: true,
                  type: 'success'
                })
				this.$router.push('/index')
              } else {
                this.$message.error({
                  message: '用户名或密码错误',
                  showClose: true,
                  type: 'error'
                })
              }
            })
            .catch((response) => {
              this.$message.error({
                message: '登录失败',
                showClose: true,
                type: 'error'
              })
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
