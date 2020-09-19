<template>
	<div class="bigbox">
		<el-row class="tac">
			<el-col>
				<el-menu :default-active="Flag" 
				class="el-menu-vertical-demo" 
				@select="handleSelect" 
				:collapse="isCollapse">
					<el-menu-item index="1">
						<i class="el-icon-user"></i>
						<span slot="title">个人资料</span>
					</el-menu-item>
					<el-menu-item index="2">
						<i class="el-icon-wallet"></i>
						<span slot="title">我的钱包</span>
					</el-menu-item>
					<el-menu-item index="3">
						<i class="el-icon-document"></i>
						<span slot="title">我的文章</span>
					</el-menu-item>
					<el-menu-item index="4">
						<i class="el-icon-coin"></i>
						<span slot="title">在售积分</span>
					</el-menu-item>
					<el-menu-item @click="logout()" >
					  <i class="el-icon-setting"></i>
					  <span slot="title">登出</span>
					</el-menu-item>
				</el-menu>
			</el-col>
		</el-row>

		<div class="content">
			<div v-if="Flag === '1'">
				<myInfo></myInfo>
			</div>
			<div v-if="Flag === '2'">
				<myWallet></myWallet>
			</div>
			<div v-if="Flag === '3'">
				<myArticle></myArticle>
			</div>
			<div v-if="Flag === '4'">
				<mySale></mySale>
			</div>
		</div>

	</div>
</template>

<script>
	import myInfo from './myInfo.vue'
	import myWallet from './myWallet.vue'
	import myArticle from './myArticle.vue'
	import mySale from './mySale.vue'
	export default {
		name: 'userCenter',
		components: {
			myInfo: myInfo,
			myWallet: myWallet,
			myArticle: myArticle,
			mySale: mySale,
		},
		data() {
			return {
				Flag: '1',
				isCollapse: false,
				screenWidth: document.body.clientWidth,
			};
		},
		mounted () {
		    const that = this
		    window.onresize = () => {
		        return (() => {
		            window.screenWidth = document.body.clientWidth
		            that.screenWidth = window.screenWidth
		        })()
		    }
		},
		watch:{
		    screenWidth(val){
		        // 为了避免频繁触发resize函数导致页面卡顿，使用定时器
		        if(!this.timer){
		            // 一旦监听到的screenWidth值改变，就将其重新赋给data里的screenWidth
		            this.screenWidth = val
		            this.timer = true
		            let that = this
		            setTimeout(function(){
		                // 根据screenWidth改变图标
						if(that.screenWidth <= 850)
						{
							that.isCollapse = true
						}
						else
						{
							that.isCollapse = false
						}
		                that.timer = false
		            },400)
		        }
		    }
		},
		methods: {
			logout() {
				this.$message({
					showClose: true,
					message: '登出成功',
					type: 'success'
				})
				this.$store.dispatch('setStatus', false)
				localStorage.setItem('isLogin', false)
				localStorage.removeItem('userPhone')
				localStorage.removeItem('userName')
				localStorage.removeItem('userPoint')
				localStorage.removeItem('userArticle')
				localStorage.removeItem('userId')
				this.$router.push('/index')
			},
			handleSelect(key, keyPath) {
				this.Flag = key
			},
		},
	}
</script>

<style scoped>
	.bigbox {
		width: 100%;
		float: left;
	}

	.tac {
		float: left;
		margin-top: 80px;
	}

	.tac span {
		font-size: 18px;
	}

	.content {
		float: left;
		margin-left: 8%;
		margin-top: 50px;
		width: 68%;
	}
</style>
