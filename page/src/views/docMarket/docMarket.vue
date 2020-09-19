<style scoped>
.page {
  width: 340px;
  margin: 0 auto;
  margin-top: 20px;
}
.box {
  display: inline;
  width: 80%;
}
.search {
  display: inline;
  float: left;
  width: 60%;
  margin-top: 50px;
  margin-bottom: 50px;
}
.upload {
  display: inline;
  float: right;
  margin-top: 50px;
}
.shopcart {
  display: inline;
  float: right;
  width: 10%;
  margin-top: 38px;
}
.select {
  width: 110px;
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

<template>
  <div>
    <div class="box">
      <div class="search">
        <el-input placeholder="在文鸟上搜索文档" v-model="ruleForm.input">
          <el-select v-model="ruleForm.select" slot="prepend" placeholder="文章类型" class="select">
            <el-option v-for="(key, value) in types" :key="key" :label="value" :value="key"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="onSubmit"></el-button>
        </el-input>
      </div>

      <div class="shopcart">
        <ShopCart></ShopCart>
      </div>

      <div class="upload">
        <UploadArticle></UploadArticle>
      </div>
    </div>

    <el-table :data="tableData.list" style="width: 100%;">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="文章标题">
              <span>{{ props.row.articleTitle }}</span>
            </el-form-item>
            <el-form-item label="文章作者">
              <span>{{ props.row.articleAuther }}</span>
            </el-form-item>
            <el-form-item label="上传日期">
              <span>{{ props.row.submissionDate }}</span>
            </el-form-item>
            <el-form-item label="文章价格">
              <span>{{ props.row.articlePrice }}</span>
            </el-form-item>
            <el-form-item label="文章类型">
              <span>{{ props.row.articleType }}</span>
            </el-form-item>
            <el-form-item label="文章下载量">
              <span>{{ props.row.articleDownload }}</span>
            </el-form-item>
            <el-form-item label="文章收藏量">
              <span>{{ props.row.articleStore }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column prop="submissionDate" label="上传日期" width="120"></el-table-column>
      <el-table-column prop="articlePrice" label="积分" width="120"></el-table-column>
      <el-table-column prop="articleAuther" label="作者" width="180"></el-table-column>
      <el-table-column prop="articleTitle" label="文章"></el-table-column>
      <el-table-column prop="articleType" label="标签" width="100">
        <template slot-scope="scope">
          <el-tag
            disable-transitions
            :color="str2color(scope.row.articleType)"
            effect="dark"
          >{{scope.row.articleType}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.$index, tableData)" type="text" size="small">查看文章</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="page"
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="ruleForm.pageNum"
      :page-size="ruleForm.pageSize"
      :pager-count="5"
      layout="prev, pager, next"
      :total="Number(this.tableData.nums)"
    ></el-pagination>
  </div>
</template>

<script>
import ShopCart from "./shoppingCart";
import UploadArticle from "./uploadArticle";
import { getArticle } from "../../network/doc";

export default {
  components: {
    ShopCart,
    UploadArticle,
  },
  methods: {
    handleClick(index, rows) {
      this.$router.push({
        name: "docDetail",
        params: {
          key: this.ruleForm.input,
          type: this.ruleForm.select,
        },
        query: { id: rows.list[index].articleId },
      });
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.ruleForm.pageNum = val;
      this.$options.methods.submitForm.bind(this)();
    },
    onSubmit() {
      this.$options.methods.submitForm.bind(this)();
    },
    submitForm() {
      if (this.ruleForm.input === undefined) {
        this.ruleForm.input = "";
      }
      if (this.ruleForm.select === undefined) {
        this.ruleForm.select = "";
      }
      getArticle(
        this.ruleForm.pageSize,
        this.ruleForm.pageNum,
        this.ruleForm.input,
        this.ruleForm.select
      )
        .then((response) => {
          this.tableData = response;
        })
        .catch((response) => {
          this.$message({
            message: "获取文章列表失败",
            showClose: true,
            type: "error",
          });
        });
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
  data() {
    return {
      ruleForm: {
        pageSize: 10,
        pageNum: 1,
        input: "",
        select: "",
      },
      types: {
        全部: "",
        互联网: "互联网",
        医学: "医学",
        教育: "教育",
        计算机: "计算机",
        Java: "Java",
      },
      tableData: {
        nums: "4",
        list: [
          {
            articleId: "5",
            submissionDate: "2016-05-16",
            articlePrice: "10",
            articleAuther: "张三",
            articleTitle: "人工智能",
            articleType: "互联网",
            articleDownload: 10,
            articleStore: 10,
          },
          {
            articleId: "5",
            submissionDate: "2016-03-16",
            articlePrice: "11",
            articleAuther: "李四",
            articleTitle: "计算机组成与体系结构",
            articleType: "互联网",
            articleDownload: 10,
            articleStore: 10,
          },
          {
            articleId: "5",
            submissionDate: "2016-05-20",
            articlePrice: "8",
            articleAuther: "王五",
            articleTitle: "数据结构",
            articleType: "互联网",
            articleDownload: 10,
            articleStore: 10,
          },
          {
            articleId: "5",
            submissionDate: "2016-06-16",
            articlePrice: "12",
            articleAuther: "小明",
            articleTitle: "高等数学",
            articleType: "互联网",
            articleDownload: 10,
            articleStore: 10,
          },
        ],
      },
    };
  },
  mounted() {
    this.ruleForm.input = this.$route.query.search;
    this.ruleForm.select = this.$route.query.type;
    this.$options.methods.submitForm.bind(this)();
  },
};
</script>