<template>
	<div>
		<h2>我的钱包</h2>
		<el-divider></el-divider>
		<el-row :gutter="30">
			<el-col :span="12">
				<el-card class="box-card">
				  <div slot="header" class="clearfix">
				    <span>积分余额</span>
					<el-tooltip class="helpItem" effect="dark" placement="bottom-start">
					    <div slot="content">
							<b>提示：</b><br/>
							&emsp;1.积分来自上传文档和购买积分<br/>
							&emsp;2.当前显示为可用积分
						</div>
						<el-button size="mini" icon="el-icon-more" circle></el-button>
					</el-tooltip>
					<el-button
					  size="mini"
					  style="float: right; padding: 5px 5px"
					  icon="el-icon-tickets"
					  type="text"
					  @click="dialogPointTableVisible = true"
					  circle>
					</el-button>
					<el-dialog title="积分交易记录" :visible.sync="dialogPointTableVisible" center>
					  <el-table :data="PointTransactionData">
					    <el-table-column property="date" label="日期" width="150"></el-table-column>
					    <el-table-column label="类型" width="200">
							<template slot-scope="scope">
							        <span>{{ showType(scope.row.type) }}</span>
							</template>
						</el-table-column>
					    <el-table-column property="num" label="数量"></el-table-column>
					  </el-table>
					</el-dialog>
				  </div>
				  <div class="text item">
				    <span>{{userRemain.userPoint}}</span>
				  </div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card class="box-card">
				  <div slot="header" class="clearfix">
				    <span>人民币余额</span>
					<el-tooltip class="helpItem" effect="dark" placement="bottom-start">
					    <div slot="content">
							<b>提示：</b><br/>
							&emsp;1.人民币余额可用于购买积分或提现<br/>
							&emsp;2.提现时金额必须大于100元<br/>
							&emsp;3.只能整体提现
						</div>
						<el-button size="mini" icon="el-icon-more" circle></el-button>
					</el-tooltip>
					<el-button 
					  @click="withdraw"
					  style="float: right; padding: 5px 5px" 
					  type="primary" 
					  round>
					  提现
					</el-button>
					<el-button
					  size="mini"
					  style="float: right; padding: 5px 5px"
					  icon="el-icon-tickets"
					  type="text"
					  @click="dialogRmbTableVisible = true"
					  circle>
					</el-button>
					<el-dialog title="现金交易记录" :visible.sync="dialogRmbTableVisible" center>
					  <el-table :data="CashTransactionData">
					    <el-table-column property="date" label="日期" width="150"></el-table-column>
					    <el-table-column label="类型" width="200">
							<template slot-scope="scope">
							        <span>{{ showType(scope.row.type) }}</span>
							</template>
						</el-table-column>
					    <el-table-column property="num" label="数量"></el-table-column>
					  </el-table>
					</el-dialog>
				  </div>
				  <div class="text item">
				    <span>{{userRemain.userRmb}}元</span>
				  </div>
				</el-card>
			</el-col>
		</el-row>
		<el-row :gutter="30">
			<el-col :span="12">
				<el-card class="box-card">
				  <div slot="header" class="clearfix">
				    <span>累计收益</span>
					<el-tooltip class="helpItem" effect="dark" placement="bottom-start">
					    <div slot="content">
							<b>提示：</b><br/>
							&emsp;1.累计收益是指获得过的积分总量<br/>
							&emsp;2.累计收益不代表可用积分
						</div>
						<el-button size="mini" icon="el-icon-more" circle></el-button>
					</el-tooltip>
				  </div>
				  <div class="text item">
				    <span>{{userIncome}}</span>
				  </div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card class="box-card">
				  <div slot="header" class="clearfix">
				    <span>银行卡</span>
					<el-button 
					  @click="dialogFormVisible = true"
					  style="float: right; padding: 5px 5px" 
					  type="primary" 
					  round>
					  绑定
					</el-button>
					<el-dialog title="绑定银行卡" :visible.sync="dialogFormVisible" center>
					  <el-form :model="form" ref="form" status-icon :rules="rules">
					    <el-form-item label="银行卡号" prop="bankcard" :label-width="formLabelWidth">
					      <el-input v-model="form.bankcard"></el-input>
					    </el-form-item>
						<el-form-item label="身份证号" prop="idcard" :label-width="formLabelWidth">
						    <el-input v-model="form.idcard"></el-input>
						</el-form-item>
						<el-form-item label="预留手机" prop="mobile" :label-width="formLabelWidth">
						      <el-input v-model="form.mobile"></el-input>
						</el-form-item>
						<el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
						      <el-input v-model="form.name"></el-input>
						</el-form-item>
					    <el-form-item label="开户行" prop="bankId" :label-width="formLabelWidth">
					      <el-select v-model="form.bankId" placeholder="请选择开户行">
					        <el-option 
							  v-for="(key, value) in banks"
							  :key="key"
							  :label="key" 
							  :value="value">
							</el-option>					        
					      </el-select>
					    </el-form-item>
					  </el-form>
					  <div slot="footer" class="dialog-footer">
					    <el-button @click="dialogFormVisible = false;resetForm('form')">取 消</el-button>
					    <el-button type="primary" @click="dialogFormVisible = false;submitForm('form')">确 定</el-button>
					  </div>
					</el-dialog>
				  </div>
				  <div class="text item">
				    <span>
					{{fascinatingCardNumber(userBankCard.bankAccount)}}
					&emsp;{{getBankName(userBankCard.bankId)}}
					</span>
				  </div>
				</el-card>
			</el-col>
		</el-row>
	</div>
	
	
</template>

<script>
	import banks from '../../assets/banks.json';
	import {userRemain, userIncome, userBankCard, bindBankCard, userWithdraw, userPointHistory, userRmbHistory} from "../../network/user";
	
	export default {
		name: 'myWallet',
		data() {
			return {
				banks,
				userRemain: {
				  userPoint: '0',
				  userRmb: '0',
				},
				userIncome: '0',
				userBankCard: {
				  bankAccount: '未绑定银行卡',
				  bankId: '',
				},
				dialogPointTableVisible: false,
				dialogRmbTableVisible: false,
				dialogFormVisible: false,
				PointTransactionData: [],
				CashTransactionData: [],
				form: {
				  bankcard: '',
				  idcard: '',
				  mobile: '',
				  name: '',
				  bankId: '',
				},
				formLabelWidth: '120px',
				rules: {
					bankcard: [
					  { required: true, message: '请输入银行卡号', trigger: 'blur' }
					],
					idcard: [
					  { required: true, message: '请输入身份证号', trigger: 'blur' },
					  { min: 15, max: 18, message: '请输入正确的身份证号码', trigger: 'blur' },
					  {
						pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
						message: '请输入正确的身份证号码',
						trigger: 'blur'
					  }
					],
					mobile: [
					  { required: true, message: '请输入手机号', trigger: 'blur' },
					  {
						pattern: /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/,
						message: '请输入正确的手机号',
						trigger: 'blur'
					  }
					],
					name:[
					  { required: true, message: '请输入真实姓名', trigger: 'blur' },
					  {
						pattern: /^[\u4e00-\u9fa5]+$/,
						message: '请输入正确的姓名',
						trigger: 'blur'
					  }
					],
					bankId:[
					  { required: true, message: '请选择开户行', trigger: ['blur', 'change'] }
					]
				}
			};
		},
		methods: {
		  withdraw() {
			this.$confirm('此操作将提现全部人民币余额, 是否继续?', '提示', {
			          confirmButtonText: '确定',
			          cancelButtonText: '取消',
			          type: 'warning'
			        }).then(() => {
					  let userId = localStorage.getItem('userId')
					  userWithdraw(userId, this.userRemain.userRmb, this.userBankCard.bankAccount)
					    .then((response) => {
					      if (response.status_code != '0') {
					  		this.$message({
					  		  type: 'success',
					  		  message: '提现成功!',
					  		})
					  		this.userRemain.userRmb = '0'
					      } else {
					        this.$message.error({
					          message: '用户余额不足',
					          showClose: true,
					          type: 'error'
					        })
					      }
					    })
					    .catch((response) => {
					      this.$message.error({
					        message: '提现失败',
					        showClose: true,
					        type: 'error'
					      })
					    })
			          
			        }).catch(() => {
			          this.$message({
			            type: 'info',
			            message: '已取消提现'
			          });          
			        });
		  },
		  submitForm (formName) {
		    this.$refs[formName].validate((valid) => {
		      if (valid) {
				let userId = localStorage.getItem('userId')
				bindBankCard(userId, this.form.bankcard, this.form.bankId, this.form.mobile, this.form.idcard, this.form.name)
				  .then((response) => {
				    if (response.status_code != '0') {
					  this.$message.success({
					    message: '绑定成功',
					    showClose: true,
					    type: 'success'
					  })
					  this.userBankCard.bankAccount = this.form.bankcard
					  this.userBankCard.bankId = this.form.bankId
					  this.resetForm(formName)
				    } else {
				      this.$message.error({
				        message: '绑定银行账户失败',
				        showClose: true,
				        type: 'error'
				      })
				    }
				  })
				  .catch((response) => {
				    this.$message.error({
				      message: '绑定银行账户失败',
				      showClose: true,
				      type: 'error'
				    })
				  })
			  } else {
				this.$message.error({
				  message: '提交失败, 请检查表项',
				  showClose: true,
				  type: 'error'
				})
				return false
			}
			})
		  },
		  resetForm (formName) {
		    this.$refs[formName].resetFields()
		  },
		  getBankName(bankId) {
			for(let bankSymbol in this.banks) {
				if(bankId === bankSymbol) {
					return this.banks[bankId];
				}
			}
			return '未知银行';
		  },
		  fascinatingCardNumber(cardNumber) {
			if (cardNumber && cardNumber.length > 8) {
			        return `${cardNumber.substring(0, 4)} ${"*".repeat(cardNumber.length - 8).replace(/(.{4})/g, `$1 `)}${cardNumber.length % 4 ? " " : ""}${cardNumber.slice(-4)}`;
			    }
			return cardNumber;
		  },
		  showType(type) {
		  	if(type == '0') {
		  		return '支出'
		  	} else {
		  		return '收入'
		  	}
		  },
		},
		created() {
		  let userId = localStorage.getItem('userId')
		  userRemain(userId)
		    .then((response) => {
		      if (response.userPoint != '-1.0') {
				  this.userRemain.userPoint = response.userPoint
				  this.userRemain.userRmb = response.userRmb
		      } else {
		        this.$message.error({
		          message: '用户余额错误, 请耐心等待系统创建钱包',
		          showClose: true,
		          type: 'error'
		        })
		      }
		    })
		    .catch((response) => {
		      this.$message.error({
		        message: '获取余额失败',
		        showClose: true,
		        type: 'error'
		      })
		    })
		  userIncome(userId)
		    .then((response) => {
		      if (response.status_code != '0') {
		  		this.userIncome = response.message
		      } else {
		        this.$message.error({
		          message: '用户Id错误',
		          showClose: true,
		          type: 'error'
		        })
		      }
		    })
		    .catch((response) => {
		      this.$message.error({
		        message: '获取累计收益失败',
		        showClose: true,
		        type: 'error'
		      })
		    })
		  userBankCard(userId)
		    .then((response) => {
		      if (response.userId != '0') {
		  		this.userBankCard.bankAccount = response.bankAccount
				this.userBankCard.bankId = response.bankId
		      } else {
		        this.$message.warning({
		          message: '未绑定银行卡',
		          showClose: true,
		          type: 'warning'
		        })
		      }
		    })
		    .catch((response) => {
		      this.$message.error({
		        message: '获取银行卡信息失败',
		        showClose: true,
		        type: 'error'
		      })
		    })
			userRmbHistory(userId)
			  .then((response) => {
			    this.CashTransactionData = response
			  })
			  .catch((response) => {
			    this.$message.error({
			      message: '获取现金交易记录失败',
			      showClose: true,
			      type: 'error'
			    })
			})
			userPointHistory(userId)
			  .then((response) => {
			    this.PointTransactionData = response
			  })
			  .catch((response) => {
			    this.$message.error({
			      message: '获取现金交易记录失败',
			      showClose: true,
			      type: 'error'
			    })
			})
		},
	}
</script>

<style>
	.el-row {
	    margin-bottom: 20px;
	    &:last-child {
	      margin-bottom: 0;
	    }
	  }
	
	.text {
	    font-size: 14px;
	  }
	
	.item {
	    margin-bottom: 18px;
		clear: both
	  }
	
	.clearfix:before,
	.clearfix:after {
	    display: table;
	    content: "";
	  }
	.clearfix:after {
	    clear: both
	  }
	
	.box-card {
	    width: 100%;
	  }
</style>
