<template>
  <div class="clearfix">
    <el-badge :value="count" class="item">
      <el-button icon="el-icon-shopping-cart-2" @click="dialogShopCartTableVisible = true"></el-button>
    </el-badge>

    <el-dialog title="购物车" :visible.sync="dialogShopCartTableVisible" center>
      <el-dialog width="40%" title="订单确认" :visible.sync="innerDialogConfirmVisible" append-to-body>
        <el-table :data="multipleSelection" show-summary>
          <el-table-column property="articleTitle" label="标题"></el-table-column>
          <el-table-column property="articleAuthor" label="作者" width="100"></el-table-column>
          <el-table-column property="articleType" label="标签" width="100">
            <template slot-scope="scope">
              <el-tag
                disable-transitions
                :color="str2color(scope.row.articleType)"
                effect="dark"
              >{{scope.row.articleType}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column property="articlePrice" label="价格" width="100"></el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button @click="innerDialogConfirmVisible = false">取 消</el-button>
          <el-button
            type="primary"
            @click="innerDialogConfirmVisible = false; dialogShopCartTableVisible = false; dealShoppingCart()"
          >确 定</el-button>
        </div>
      </el-dialog>
      <el-table
        ref="filterTable"
        :data="ShopCartData"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="文章 ID">
                <span>{{ props.row.articleId }}</span>
              </el-form-item>
              <el-form-item label="上传日期">
                <span>{{ props.row.submissionDate }}</span>
              </el-form-item>
              <el-form-item label="下载量">
                <span>{{ props.row.articleDownload }}</span>
              </el-form-item>
              <el-form-item label="收藏量">
                <span>{{ props.row.articleStore }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="articleTitle" label="标题"></el-table-column>
        <el-table-column
          prop="articleAuthor"
          label="作者"
          column-key="articleAuthor"
          :filters="authorFilters"
          :filter-method="filterHandler"
        ></el-table-column>
        <el-table-column
          prop="articleType"
          label="标签"
          width="100"
          :filters="typeFilters"
          :filter-method="filterType"
          filter-placement="bottom-end"
        >
          <template slot-scope="scope">
            <el-tag
              disable-transitions
              :color="str2color(scope.row.articleType)"
              effect="dark"
            >{{scope.row.articleType}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="articlePrice" label="价格" sortable width="100"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              @click.native.prevent="deleteRow(scope.$index, ShopCartData)"
              type="danger"
              size="small"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clearFilter();toggleSelection()">重 置</el-button>
        <el-button type="primary" @click="innerDialogConfirmVisible = true;">购 买</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deleteDoc } from "../../network/doc";
import { getCart } from "../../network/doc";
import { dealCart } from "../../network/doc";
import { userRemain } from "../../network/user";

export default {
  name: "shopCart",
  data() {
    return {
      userId: localStorage.getItem("userId"),
      userRemain: {
        userPoint: 0,
        userRmb: 0,
      },
      dialogShopCartTableVisible: false,
      innerDialogConfirmVisible: false,
      purchasedList: [],
      multipleSelection: [],
      authorFilters: [],
      typeFilters: [],
      ShopCartData: [],
    };
  },
  computed: {
    count() {
      return this.ShopCartData.length;
    },
  },
  methods: {
    getRemain() {
      userRemain(this.userId).then((response) => {
        this.userRemain = response;
      });
    },
    clearFilter() {
      this.$refs.filterTable.clearFilter();
    },
    filterType(value, row) {
      return row.articleType === value;
    },
    filterHandler(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.filterTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.filterTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      let userId = localStorage.getItem("userId");
      this.purchasedList = [];
      val.forEach((val, index) => {
        this.ShopCartData.forEach((v, i) => {
          if (val.articleId == v.articleId) {
            this.purchasedList.push({
              articleId: val.articleId,
              userId: userId,
            });
          }
        });
      });
    },
    deleteRow(index, rows) {
      this.$confirm("此操作将从购物车删除文章, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          let userId = localStorage.getItem("userId");
          deleteDoc(userId, rows[index].articleId)
            .then((response) => {
              if (response.status_code != "0") {
                this.$message({
                  type: "success",
                  message: "删除成功!",
                });
                rows.splice(index, 1);
              } else {
                this.$message.error({
                  message: "删除购物车失败：文档已删除",
                  showClose: true,
                  type: "error",
                });
              }
            })
            .catch((response) => {
              this.$message.error({
                message: "删除失败",
                showClose: true,
                type: "error",
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消下架",
          });
        });
    },
    dealShoppingCart() {

      this.$message({ message: "正在处理订单!" });
      dealCart(this.purchasedList)
        .then((response) => {
          if (response.status_code != "0") {
            this.$message({
              type: "success",
              message: "购买成功!",
            });
          } else {
            console.log(response);
            this.$message.error({
              message: "用户余额不足",
              showClose: true,
              type: "error",
            });
          }
        })
        .catch((response) => {
          console.log(response);
          this.$message.error({
            message: "购买失败",
            showClose: true,
            type: "error",
          });
        });
    },
    uniqueArray(array, key) {
      var result = [array[0]];
      for (var i = 1; i < array.length; i++) {
        var item = array[i];
        var repeat = false;
        for (var j = 0; j < result.length; j++) {
          if (item[key] == result[j][key]) {
            repeat = true;
            break;
          }
        }
        if (!repeat) {
          result.push(item);
        }
      }
      return result;
    },
    getType(data) {
      for (let item in data) {
        this.typeFilters.push({
          text: data[item].articleType,
          value: data[item].articleType,
        });
      }
      this.typeFilters = this.uniqueArray(this.typeFilters, "text");
    },
    getAuthor(data) {
      for (let item in data) {
        this.authorFilters.push({
          text: data[item].articleAuthor,
          value: data[item].articleAuthor,
        });
      }
      this.authorFilters = this.uniqueArray(this.authorFilters, "text");
    },
    str2color(str) {
      if (str === "") {
        return "#FFFFFF";
      }
      let arr = [];
      for (let i = 0; i < str.length; i++) {
        arr.push(str.charCodeAt(i).toString(16) + 5);
      }
      let color = arr.join("");
      color = "#" + color.substr(color.length - 6);
      return color;
    },
  },
  mounted() {
    let userId = localStorage.getItem("userId");
    getCart(userId)
      .then((response) => {
        this.ShopCartData = response;
        this.getType(this.ShopCartData);
        this.getAuthor(this.ShopCartData);
      })
      .catch((response) => {
        console.log(response);
        console.log("get shopping cart failed, please login first");
      });
    this.getRemain();
  },
};
</script>

<style scoped>
.item {
  margin-top: 10px;
  margin-right: 40px;
  float: right;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
