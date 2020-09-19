<template>
	<div>
		<h2>个人资料</h2>
		<el-divider></el-divider>
		<ul>
			<li>
				昵称：&emsp;{{userInfo.name}}
			</li>
			<li>
				积分：&emsp;{{userInfo.point}}
			</li>
			<li>
				电话：&emsp;{{userInfo.phone}}
			</li>
			<li>
				上传文章：&emsp;{{userInfo.articleNum}}
			</li>
		</ul>
	</div>
</template>

<script>
	export default {
	  name: 'myInfo',
	  data() {
	    return {
	      userInfo: {
	                name: '',
	                point: '',
	                phone: '',
					articleNum: '',
	              },
	    };
	  },
	  created() {
		  this.userInfo.name = localStorage.getItem('userName')
		  this.userInfo.point = localStorage.getItem('userPoint')
		  this.userInfo.phone = localStorage.getItem('userPhone')
		  this.userInfo.articleNum = localStorage.getItem('userArticle')
	  }
	};
</script>

<style>
	li{
		color: #888;
		list-style: none;
		margin-bottom: 12px;
		&:last-child {
		  margin-bottom: 0;
		}
	}
</style>
