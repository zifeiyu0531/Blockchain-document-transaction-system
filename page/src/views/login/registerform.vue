<template>
  <el-form :model="ruleForm" ref="ruleForm" status-icon :rules="rules"  label-width="100px" class="demo-ruleForm">
    <el-form-item label="用户名" prop="name">
      <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model.number="ruleForm.phone"></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="vcode">
      <el-input v-model.number="ruleForm.vcode" style="width:60%;float:left"></el-input>
      <el-button
	   type="primary" @click="send_smscode('ruleForm')" :style="buttonstyle">{{content}}</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {userRegister, getSMSCode} from"../../network/user";

export default {
  name: 'registerform',
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
		content: '发送验证码', 
		totalTime: 60,
		canClick: true ,
		buttonstyle: 'width:40%;float:left;',
		ruleForm: {
			name: '',
			password: '',
			checkPass: '',
			phone: '',
			vcode: ''
		},
		rules: {
			name: [
			  { required: true, message: '请输入用户名', trigger: 'blur' },
			  { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' },
			  {
			    required: true,
			    pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/,
			    message: '用户名不支持特殊字符',
			    trigger: 'blur'
			  }
			],
			password: [
			  { required: true, validator: validatePass, trigger: 'blur' }
			],
			checkPass: [
			  { required: true, validator: validatePass2, trigger: 'blur' }
			],
			phone:[
			  { required: true, message: '请输入手机号', trigger: 'blur' }
			],
			vcode:[
			  { required: true, message: '请输入验证码', trigger: 'blur' }
			]
		}
    }
  },
  methods: {
	countDown () {
	 if (!this.canClick) return  //改动的是这两行代码
	 this.canClick = false
	 this.buttonstyle = 
	 "width:40%;float:left;background-color: #ddd;border-color: #ddd;color:#57a3f3;cursor: not-allowed;"
	 this.content = this.totalTime + 's后重新发送'
	 let clock = window.setInterval(() => {
	  this.totalTime--
	  this.content = this.totalTime + 's后重新发送'
	  if (this.totalTime < 0) {
	   window.clearInterval(clock)
	   this.content = '重新发送验证码'
	   this.buttonstyle ="width:40%;float:left;"
	   this.totalTime = 10
	   this.canClick = true  //这里重新开启
	  }
	 },1000)
	},
    send_smscode (formName) {
		if (!this.canClick) return 
		this.$refs[formName].validateField('phone',(valid)=>{
			if (valid!='请输入手机号') {
			getSMSCode(this.ruleForm.phone)
			  .then((response) => {
				if (response) {
				  this.$message.success({
				    message: '获取成功',
				    showClose: true,
				    type: 'success'
				  })
				  this.countDown()
				} else {
				  this.$message.error({
				    message: '手机号已被注册',
				    showClose: true,
				    type: 'error'
				  })
				}
			  })
			  .catch((response) => {
			    this.$message.error({
			      message: '获取失败',
			      showClose: true,
			      type: 'error'
			    })
			  })
			}	
		})	
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
		  userRegister(this.ruleForm.name, this.ruleForm.password, this.ruleForm.phone)
            .then((response) => {
              if (response.userInfo != null){
				  this.$store.dispatch('setStatus', true)
				  localStorage.setItem('userId', response.userInfo.userId)
				  localStorage.setItem('userName', response.userInfo.userName)
				  localStorage.setItem('userPhone', response.userInfo.userPhone)
				  localStorage.setItem('userPoint', response.userInfo.userPoint)
				  localStorage.setItem('userArticle', response.userArticle)
				  this.$message.success({
					message: '注册成功',
					showClose: true,
					type: 'success'
				  })
				  this.$router.push('/index')
			  }
              else {
                this.$message.error({
                  message: '用户名或手机号已被注册',
                  showClose: true,
                  type: 'error'
                })
              }
            })
            .catch((response) => {
              this.$message.error({
                message: '注册失败',
                showClose: true,
                type: 'error'
              })
            })
        } else {
          console.log('error submit!!')
          return false
        }
      }
      )
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
  }
}
</script>
<style scoped>
	.disabled{
	 background-color: #ddd;
	 border-color: #ddd;
	 color:#57a3f3;
	 cursor: not-allowed;
	}
</style>