<style lang="scss">
#purchase {
  font-size: 20px;
}

.poinTable {
  .el-table {
    font-size: 50px;
  }
}

.el-table .warning-row {
  font-size: 20px;
  background: oldlace;
}

.el-table .success-row {
  font-size: 20px;
  background: #f0f9eb;
}

.page {
  width: 340px;
  margin: 0 auto;
  margin-top: 20px;
}

.purchase-text {
  margin: 20px;
  margin-left: 60px;
  font-size: 20px;
}

.onshelf-text {
  margin: 0 auto;
  text-align: center;
  font-size: 20px;
  font-weight: bolder;
}
</style>

<template>
  <div>
    <div id="chartLineBox" style="width: 100%;height: 500px;margin: 0 auto;margin-top:30px"></div>

    <el-form ref="ruleForm" :model="ruleForm" label-width="100px">
      <el-row type="flex" justify="center">
        <el-col :span="3">
          <el-form-item label="最大积分数">
            <el-input v-model="ruleForm.salePointNumMax" style="width: 70px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="最小积分数">
            <el-input v-model="ruleForm.salePointNumMin" style="width: 70px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="最大单价">
            <el-input v-model="ruleForm.salePointPriceMax" style="width: 70px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="最小单价">
            <el-input v-model="ruleForm.salePointPriceMin" style="width: 70px"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="积分类型">
            <el-select v-model="ruleForm.salePointType" placeholder="请选择积分类型" style="width: 90px">
              <el-option label="全类型" value="-1"></el-option>
              <el-option label="零售" value="0"></el-option>
              <el-option label="捆绑" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item>
            <el-button type="primary" style="width: 100px" @click="onSubmit">筛选</el-button>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item>
            <el-button type="success" style="width: 100px" @click="onShelf">上架积分</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table
      class="poinTable"
      :data="tableData"
      style="width: 100%;margin: 0 auto"
      :row-class-name="tableRowClassName"
    >
      <el-table-column prop="salePointUserName" label="出售人" width="180"></el-table-column>
      <el-table-column label="类型">
        <template v-slot="scope">{{renderTableCell_type(scope)}}</template>
      </el-table-column>
      <el-table-column prop="salePoint.salePointNum" label="积分总量">
        <template v-slot="scope">{{renderTableCell_num(scope)}}</template>
      </el-table-column>
      <el-table-column prop="salePoint.salePointRemain" label="剩余量">
        <template v-slot="scope">{{renderTableCell_remain(scope)}}</template>
      </el-table-column>
      <el-table-column prop="salePoint.salePointPrice" label="单价">
        <template v-slot="scope">{{renderTableCell_price(scope)}}</template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="purchasePoint(scope.$index, tableData)"
            type="text"
            size="small"
            id="purchase"
          >购买</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="积分购买" :visible.sync="dialogFormVisible">
      <div class="purchase-text">积分类型：{{pointType}}</div>
      <div class="purchase-text">积分总量：{{salePoint.salePointNum}} POINTS</div>
      <div class="purchase-text">剩余积分：{{salePoint.salePointRemain}} POINTS</div>
      <div class="purchase-text">积分单价：{{salePoint.salePointPrice}} RMB</div>
      <el-form :model="ruleForm" style="margin: 20px;  margin-left: 60px;">
        <el-form-item label="购买量">
          <el-input-number
            v-model="pointNum"
            :min="1"
            :max="salePoint.salePointRemain"
            :disabled="isPack"
          ></el-input-number>
        </el-form-item>
      </el-form>
      <div class="purchase-text">总价：{{salePoint.salePointPrice * pointNum}} RMB</div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitPurchase">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="上架积分" :visible.sync="onshelfFormVisible">
      <div class="onshelf-text">用户：{{this.userName}}</div>
      <div class="onshelf-text">剩余积分：{{userRemain.userPoint}}</div>
      <el-form ref="onShelfForm" :model="onShelfForm">
        <el-row type="flex" justify="center">
          <el-col align="center" :span="6" style="margin:20px">
            <el-form-item label="出售个数">
              <el-input-number
                v-model="onShelfForm.salePointNum"
                :min="1"
                :max="userRemain.userPoint"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col align="center" :span="3" style="margin:20px">
            <el-form-item label="积分单价">
              <el-input v-model="onShelfForm.salePointPrice"></el-input>
            </el-form-item>
          </el-col>
          <el-col align="center" :span="3" style="margin:20px">
            <el-form-item label="积分类型">
              <el-select v-model="onShelfForm.salePointType" placeholder="请选择积分类型">
                <el-option label="零售" :value="0"></el-option>
                <el-option label="捆绑" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="onshelfFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitOnShelf">上 架</el-button>
      </div>
    </el-dialog>

    <el-pagination
      class="page"
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="ruleForm.pageNum"
      :page-size="ruleForm.pageSize"
      :pager-count="5"
      layout="prev, pager, next"
      :total="80"
    ></el-pagination>
  </div>
</template>

<script>
import { getSalePoint } from "../../network/point";
import { getRecentPrice } from "../../network/point";
import { purchaseRetailPoint } from "../../network/point";
import { purchasePackPoint } from "../../network/point";
import { userRemain } from "../../network/user";
import { pointOnShelf } from "../../network/point";
import echarts from "echarts";

export default {
  name: "pointMarket",

  components: {},

  data() {
    return {
      userId: localStorage.getItem("userId"),
      userName: localStorage.getItem("userName"),
      userRemain: {
        userPoint: 0,
        userRmb: 0,
      },
      ruleForm: {
        pageSize: 10,
        pageNum: 1,
        salePointType: -1,
        salePointNumMin: 0,
        salePointNumMax: 32767,
        salePointPriceMin: 0,
        salePointPriceMax: 32767,
      },
      onShelfForm: {
        salePointId: 0,
        salePointNum: 0,
        salePointUser: localStorage.getItem("userId"),
        salePointType: null,
        salePointRemain: 0,
        salePointPrice: 0,
      },
      salePoint: {},
      tableData: [],
      diagramData: [],
      pointType: "",
      pointNum: 0,
      dialogFormVisible: false,
      onshelfFormVisible: false,
      isPack: false,
    };
  },

  mounted() {
    this.$options.methods.submitForm.bind(this)();
    this.$options.methods.getDiagramData.bind(this)();
    this.$options.methods.getRemain.bind(this)();
  },

  watch: {},

  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (row.salePoint.salePointType === 1) {
        return "warning-row";
      } else if (row.salePoint.salePointType === 0) {
        return "success-row";
      }
      return "";
    },

    onSubmit() {
      this.$options.methods.submitForm.bind(this)();
    },

    onShelf() {
      this.onshelfFormVisible = true;
    },

    submitForm() {
      getSalePoint(
        this.ruleForm.pageSize,
        this.ruleForm.pageNum,
        this.ruleForm.salePointType,
        this.ruleForm.salePointNumMin,
        this.ruleForm.salePointNumMax,
        this.ruleForm.salePointPriceMin,
        this.ruleForm.salePointPriceMax
      ).then((response) => {
        this.tableData = response;
      });
    },

    getDiagramData() {
      getRecentPrice().then((response) => {
        response.forEach((item, index, array) => {
          var orderPoint = item.orderPoint;
          var orderPrice = item.orderPrice;
          var singlePrice = orderPrice / orderPoint;
          this.diagramData.push(singlePrice);
        });

        this.chartLine = echarts.init(document.getElementById("chartLineBox"));

        var option = {
          tooltip: {
            trigger: "axis",
          },
          legend: {
            data: ["积分单价"],
          },
          color: ["#FA6F53"],
          xAxis: {
            type: "category",
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: "#FA6F53",
              },
            },
          },
          yAxis: {
            name: "POINT PRICE",
            nameTextStyle: {
              color: "#FA6F53",
              fontSize: 16,
              padding: [0, 0, 10, 0],
            },
            axisLine: {
              lineStyle: {
                color: "#FA6F53",
              },
            },
            type: "value",
          },
          series: [
            {
              name: "积分单价",
              data: this.diagramData,
              type: "line",
              lineStyle: {
                normal: {
                  color: "#FA6F53",
                },
              },
            },
          ],
        };

        this.chartLine.setOption(option);
      });
    },

    getRemain() {
      userRemain(this.userId).then((response) => {
        this.userRemain = response;
      });
    },

    submitOnShelf() {
      this.$message("您的积分正在上架！");
      this.onshelfFormVisible = false;
      pointOnShelf(
        this.onShelfForm.salePointId,
        this.onShelfForm.salePointNum,
        this.onShelfForm.salePointUser,
        this.onShelfForm.salePointType,
        this.onShelfForm.salePointRemain,
        this.onShelfForm.salePointPrice
      ).then((response) => {
        this.$message({
          message: "积分上架成功！",
          type: "success",
        });
      });
    },

    renderTableCell_type({ row }) {
      if (row.salePoint.salePointType === 1) {
        return "捆绑";
      } else {
        return "零售";
      }
    },

    renderTableCell_num({ row }) {
      return row.salePoint.salePointNum + " Points";
    },

    renderTableCell_remain({ row }) {
      return row.salePoint.salePointRemain + " Points";
    },

    renderTableCell_price({ row }) {
      return row.salePoint.salePointPrice + " RMB";
    },

    handleSizeChange(val) {},

    handleCurrentChange(val) {
      this.ruleForm.pageNum = val;
      this.$options.methods.submitForm.bind(this)();
    },

    purchasePoint(index, tableData) {
      if (tableData[index].salePoint.salePointUser == this.userId) {
        this.$alert("不能购买自己上架的积分", "提示", {
          confirmButtonText: "确定",
          callback: (action) => {
            this.$message({
              type: "info",
              message: `action: ${action}`,
            });
          },
        });
      } else {
        this.dialogFormVisible = true;
        this.salePoint = tableData[index].salePoint;
        this.pointNum = tableData[index].salePoint.salePointNum;
        if (tableData[index].salePoint.salePointType == 0) {
          this.pointType = "零售";
          this.isPack = false;
        } else {
          this.pointType = "捆绑";
          this.isPack = true;
        }
      }
    },

    submitPurchase() {
      this.$message("正在处理您的购买！");
      this.dialogFormVisible = false;
      if (this.salePoint.salePointType == 0) {
        purchaseRetailPoint(
          this.salePoint.salePointId,
          this.userId,
          this.pointNum
        ).then((response) => {
          this.$message({
            message: "购买成功！",
            type: "success",
          });
        });
      } else {
        this.$message("正在处理您的购买！");
        purchasePackPoint(this.salePoint.salePointId, this.userId).then(
          (response) => {
            this.$message({
              message: "购买成功！",
              type: "success",
            });
          }
        );
      }
    },
  },
};
</script>