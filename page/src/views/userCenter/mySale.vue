<template>
	<div>
		<h2>在售积分</h2>
		<el-divider></el-divider>
		<el-table
		      :data="tableData"
		      style="width: 100%">
		      <el-table-column
		        prop="salePointId"
		        label="ID"
		        width="120"
				fixed>
		      </el-table-column>
			  <el-table-column
			    prop="salePointNum"
			    label="出售量"
			    width="120">
			  </el-table-column>
		      <el-table-column
		        label="类型"
		        width="120">
				<template slot-scope="scope">
				        <span>{{ showType(scope.row.salePointType) }}</span>
				</template>
		      </el-table-column>
		      <el-table-column
		        prop="salePointRemain"
		        label="余量"
				width="120">
		      </el-table-column>
			  <el-table-column
			    prop="salePointPrice"
			    label="单价"
			  	width="120">
			  </el-table-column>
			  <el-table-column
			        fixed="right"
			        label="操作"
			        width="120">
			        <template slot-scope="scope">
			          <el-button
			            @click.native.prevent="deleteRow(scope.$index, tableData)"
			            type="danger"
			            size="small">
			            下架
			          </el-button>
			        </template>
			  </el-table-column>
		</el-table>
	</div>
</template>

<script>
	import { getSalePoint } from "../../network/user";
	import { pointOffShelf } from "../../network/point";
	export default {
		  name: 'mySale',
	      data() {
	        return {
	          tableData: [{
				"salePointId": 1, 
				"salePointNum": 10.0, 
				"salePointUser": 1, 
				"salePointType": 0, 
				"salePointRemain": 10.0, 
				"salePointPrice": 2.0 
			  },{
				"salePointId": 2, 
				"salePointNum": 10.0, 
				"salePointUser": 1, 
				"salePointType": 0, 
				"salePointRemain": 10.0, 
				"salePointPrice": 2.0 
			  },{
				"salePointId": 3, 
				"salePointNum": 10.0, 
				"salePointUser": 1, 
				"salePointType": 0, 
				"salePointRemain": 10.0, 
				"salePointPrice": 2.0 
			  },{
				"salePointId": 4, 
				"salePointNum": 10.0, 
				"salePointUser": 1, 
				"salePointType": 0, 
				"salePointRemain": 10.0, 
				"salePointPrice": 2.0 
			  },{
				"salePointId": 5, 
				"salePointNum": 10.0, 
				"salePointUser": 1, 
				"salePointType": 0, 
				"salePointRemain": 10.0, 
				"salePointPrice": 2.0 
			  }]
	        }
	      },
		  methods: {
			deleteRow(index, rows) {
			  this.$confirm('此操作将下架积分, 是否继续?', '提示', {
			            confirmButtonText: '确定',
			            cancelButtonText: '取消',
			            type: 'warning'
			          }).then(() => {
						pointOffShelf(rows[index].salePointId)
						  .then((response) => {
							  if(response.status_code != '0'){
								  this.$message({
								    type: 'success',
								    message: '下架成功!',
								  });
								  rows.splice(index, 1);
							  }
						  })
						  .catch((response) => {
						    this.$message.error({
						      message: '下架失败',
						      showClose: true,
						      type: 'error'
						    })
						  })
			          }).catch(() => {
			            this.$message({
			              type: 'info',
			              message: '已取消下架'
			            });          
			          });
			},
			showType(type) {
				if(type == '0') {
					return '零售'
				} else {
					return '捆绑'
				}
			}
		  },
		  created() {
		  	let userId = localStorage.getItem('userId')
		  	getSalePoint(userId)
		  	  .then((response) => {
		  	    this.tableData = response
		  	  })
		  	  .catch((response) => {
		  	    this.$message.error({
		  	      message: '获取文章失败',
		  	      showClose: true,
		  	      type: 'error'
		  	    })
		  	  })
		  }
	    }
</script>

<style>
</style>
