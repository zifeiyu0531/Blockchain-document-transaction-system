<template>
	<div class="sub">
		<div class="shopcart">
			<ShopCart></ShopCart>
		</div>
		
		<div class="image">
			<img src="../../assets/search_logo_2.png" alt="">
		</div>
		
		<div class="box">
			<div class="search">
				<el-input
				  placeholder="在文鸟上搜索文档"
				  autofocus=true
				  v-model="input"
				  class="search-input">
					<el-button 
					  slot="append"
					  icon="el-icon-search"
					  @click="routeToResult">
					</el-button>
				</el-input>
			</div>
				
			<div class="upload">
				<UploadArticle></UploadArticle>
			</div>
		</div>
		
	</div>
</template>

<script>
	import ShopCart from './shoppingCart'
	import UploadArticle from './uploadArticle'
	
	export default {
		name: 'docSearch',
		components: {
			ShopCart,
			UploadArticle,
		},
    	data() {
      		return {
				input:'',
      		};
    	},
    	methods: {
			routeToResult() {
				this.$router.push({ name: "doc", query: { search: this.input, type: '' } });
			}
    	},
		
  	}
</script>

<style scoped>
	.sub {
		text-align: center;
	}
	.shopcart {
		margin-top: 20px;
	}
	.image {
		width: 80%;
    	margin: 10px auto 30px;
    	position: relative;
	}
	.box {
		margin: 60px 20%;
		width: 65%;
	}
	.search {
		float: left;
		width: 80%;
	}
	.upload {
		float: right;
		width: 20%;
	}
</style>