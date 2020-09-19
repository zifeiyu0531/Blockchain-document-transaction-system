<template>
	<div>
		<h2>我的文章</h2>
		<el-divider></el-divider>
		<el-tabs v-model="activeName">
		    <el-tab-pane name="first">
				<span slot="label">
					<i class="el-icon-takeaway-box"></i> 
					已上传文档
				</span>
				<el-table
				    :data="UploadData"
				    height="300"
				    border
				    style="width: 100%">
				    <el-table-column
				      prop="submissionDate"
				      label="日期"
				      width="100">
				    </el-table-column>
				    <el-table-column
				      prop="articleAuthor"
				      label="作者"
				      width="180">
				    </el-table-column>
				    <el-table-column
				      prop="articleTitle"
				      label="标题"
					  width="200">
				    </el-table-column>
					<el-table-column
					  prop="articleDownload"
					  label="下载"
					  width="100">
					</el-table-column>
					<el-table-column
					  prop="articleStore"
					  label="收藏"
					  width="100">
					</el-table-column>
				</el-table>
			</el-tab-pane>
		    <el-tab-pane name="second">
				<span slot="label">
					<i class="el-icon-document-checked"></i> 
					已购买文档
				</span>
				<el-table
				    :data="PurchaseData"
				    height="300"
				    border
				    style="width: 100%">
				    <el-table-column
				      prop="submissionDate"
				      label="日期"
				      width="100">
				    </el-table-column>
				    <el-table-column
				      prop="articleAuthor"
				      label="作者"
				      width="180">
				    </el-table-column>
				    <el-table-column
				      prop="articleTitle"
				      label="标题"
					  width="200">
				    </el-table-column>
					<el-table-column
					  prop="articlePrice"
					  label="价格">
					</el-table-column>
				</el-table>
			</el-tab-pane>
		    <el-tab-pane name="third">
				<span slot="label">
					<i class="el-icon-star-off"></i> 
					已收藏文档
				</span>
				<el-table
				    :data="FavoriteData"
				    height="300"
				    border
				    style="width: 100%">
				    <el-table-column
				      prop="submissionDate"
				      label="日期"
				      width="100">
				    </el-table-column>
				    <el-table-column
				      prop="articleAuthor"
				      label="作者"
				      width="180">
				    </el-table-column>
				    <el-table-column
				      prop="articleTitle"
				      label="标题"
					  width="200">
				    </el-table-column>
					<el-table-column
					  prop="articlePrice"
					  label="价格">
					</el-table-column>
					<el-table-column
					      label="操作"
					      width="120">
					      <template slot-scope="scope">
					        <el-button
					          @click.native.prevent="deleteRow(scope.$index, FavoriteData)"
					          type="danger"
					          size="small">
					          取消收藏
					        </el-button>
					      </template>
					</el-table-column>
				</el-table>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
	import { getUploadArticle, getPurchaseArticle, getStoreArticle} from "../../network/user";
	import { storeCancel } from "../../network/doc";
	export default {
		name: 'myArticle',
	    data() {
	      return {
	        activeName: 'first',
			UploadData: [],
			PurchaseData: [],
			FavoriteData: []
	      };
		},
	    methods: {
		  deleteRow(index, rows) {
		    this.$confirm('此操作将取消收藏, 是否继续?', '提示', {
		              confirmButtonText: '确定',
		              cancelButtonText: '取消',
		              type: 'warning'
		            }).then(() => {
					  let userId = localStorage.getItem('userId')
					  storeCancel(rows[index].articleId, userId)
					    .then((response) => {
					      this.$message({
					          type: 'success',
					          message: '取消成功!',
					        });
					      rows.splice(index, 1);
					    })
					    .catch((response) => {
					      console.log("cancel favorite article failed!")
						})
		            }).catch(() => {
		              this.$message({
		                type: 'info',
		                message: '已取消操作'
		              });          
		            });
		  }
	    },
		created() {
			let userId = localStorage.getItem('userId')
			getUploadArticle(userId)
			  .then((response) => {
			    this.UploadData = response
			  })
			  .catch((response) => {
			    this.$message.error({
			      message: '获取文章失败',
			      showClose: true,
			      type: 'error'
			    })
			  })
			getPurchaseArticle(userId)
			  .then((response) => {
			    this.PurchaseData = response
			  })
			  .catch((response) => {
			    this.$message.error({
			      message: '获取文章失败',
			      showClose: true,
			      type: 'error'
			    })
			  })
			getStoreArticle(userId)
			  .then((response) => {
			    this.FavoriteData = response
			  })
			  .catch((response) => {
			    this.$message.error({
			      message: '获取文章失败',
			      showClose: true,
			      type: 'error'
			    })
			  })
		}
	  };
</script>

<style scoped>
</style>
