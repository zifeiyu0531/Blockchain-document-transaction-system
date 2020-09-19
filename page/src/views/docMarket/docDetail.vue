<style scoped>
.page-header {
  width: 100%;
  font-size: 20px;
  margin-top: 20px;
  margin-bottom: 20px;
}
.title-text {
  font-family: "PingFang SC";
  font-size: 30px;
  font-weight: bolder;
  color: #409eff;
  margin: 20px;
}
.el-icon-s-claim {
  float: left;
}
.title-tag {
  text-align: center;
  margin: 20px;
}
.doc-title {
  font-family: "PingFang SC";
  font-size: 30px;
  font-weight: bolder;
  display: inline-block;
  margin-right: 20px;
}
.doc-param {
  text-align: center;
  width: 50%;
  margin: 0 auto;
  margin-bottom: 20px;
}
.param-style {
  width: 50%;
  display: inline-block;
  font-size: 18px;
  text-align: left;
}
.doc-abstract {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  font-size: 18px;
  color: #303133;
  font-family: "PingFang SC";
  text-align: left;
  padding: 10px;
  line-height: 30px;
}
.doc-context {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  white-space: pre-line;
  margin-top: 30px;
  padding: 10px;
  text-align: left;
  font-size: 16px;
  line-height: 33px;
}
.button-list {
  text-align: center;
  margin: 20px;
}
.purchase-text {
  margin: 20px;
  margin-left: 60px;
  font-size: 20px;
}
</style>

<template>
  <div>
    <el-page-header @back="goBack" content="文档详情" class="page-header"></el-page-header>

    <div style="width:62%;float: left;text-align:center;">
      <div class="title-tag">
        <div class="doc-title">{{doc.articleInfo.articleTitle}}</div>
        <el-tag style="display:inline-block;">{{doc.articleInfo.articleType}}</el-tag>
      </div>
      <div class="doc-param">
        <div class="param-style" style="color: #409EFF">
          <i class="el-icon-user-solid"></i>
          &nbsp;{{doc.articleInfo.articleAuthor}}
        </div>
        <div class="param-style" style="color: gray">
          <i class="el-icon-time"></i>
          &nbsp;{{doc.articleInfo.submissionDate}}
        </div>
      </div>
      <div class="doc-param">
        <div class="param-style" style="color: gray">
          <i class="el-icon-download"></i>
          &nbsp;{{doc.articleInfo.articleDownload}}
        </div>
        <div class="param-style" style="color: gray">
          <i class="el-icon-star-on"></i>
          &nbsp;{{doc.articleInfo.articleStore}}
        </div>
      </div>
      <div class="doc-abstract">
        摘要：
        <br />
        &#12288;&#12288;{{doc.articleInfo.articleAbstract}}
      </div>
      <div class="doc-context">
        {{doc.articleContext}}
        <i class="el-icon-more"></i>
        <br />
        <i class="el-icon-more"></i>
        <br />
        <i class="el-icon-more"></i>
        <br />
        <div style="text-align: right; color: #409eff;">
          <i class="el-icon-coin">&#12288;价格：{{doc.articleInfo.articlePrice}}&#12288;POINTS</i>
        </div>
      </div>
      <div class="button-list">
        <el-button
          type="success"
          v-if="!doc.isAuthor==1 && !doc.hasPurchased==1"
          @click="addToCart"
        >加入购物车</el-button>
        <el-button
          type="primary"
          v-if="!doc.isAuthor==1 && !doc.hasPurchased==1"
          @click="buyDoc"
        >购买文档</el-button>
        <el-button
          type="success"
          v-if="doc.isAuthor==1 || doc.hasPurchased==1"
          @click="downDoc"
        >下载文档</el-button>
        <el-button type="info" v-if="!doc.hasStored==1" @click="storeDoc">收藏文档</el-button>
        <el-button type="warning" v-if="doc.hasStored==1" @click="notStoreDoc">⭐已收藏</el-button>
      </div>
    </div>

    <div style="width:35%;float: right;">
      <div class="title-text">
        <i class="el-icon-s-claim"></i>&nbsp;推荐文档
        <el-table :data="recomTable" style="width: 100%" @row-click="clickData">
          <el-table-column prop="articleTitle" label="标题"></el-table-column>
          <el-table-column prop="articleStore" label="收藏量"></el-table-column>
          <el-table-column prop="articleDownload" label="下载量"></el-table-column>
          <el-table-column prop="articleAuthor" label="作者"></el-table-column>
        </el-table>
      </div>

      <div class="title-text">
        <i class="el-icon-trophy"></i>&nbsp;热门文档
        <el-table :data="hotTable" style="width: 100%" @row-click="clickData">
          <el-table-column prop="articleTitle" label="标题"></el-table-column>
          <el-table-column prop="articleStore" label="收藏量"></el-table-column>
          <el-table-column prop="articleDownload" label="下载量"></el-table-column>
          <el-table-column prop="articleAuthor" label="作者"></el-table-column>
        </el-table>
      </div>
    </div>

    <el-dialog title="文档购买" :visible.sync="dialogFormVisible" v-loading="loading">
      <div class="purchase-text">{{doc.articleInfo.articleTitle}}</div>
      <div class="purchase-text">价格：{{doc.articleInfo.articlePrice}} POINTS</div>
      <div class="purchase-text">余额：{{userRemain.userPoint}} POINTS</div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitBuy">购 买</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getArticleDetail } from "../../network/doc";
import { addCart } from "../../network/doc";
import { purchaseArticle } from "../../network/doc";
import { downloadArticle } from "../../network/doc";
import { storeArticle } from "../../network/doc";
import { storeCancel } from "../../network/doc";
import { getRecommendArticle } from "../../network/doc";
import { getPopularArticle } from "../../network/doc";
import { userRemain } from "../../network/user";
import { SERVER_BASE_URL } from "../../config/baseConfig";

export default {
  name: "docDetail",

  components: {},

  data() {
    return {
      docId: 0,
      userId: 0,
      keyWord: "",
      type: "",
      userRemain: {
        userPoint: 0,
        userRmb: 0,
      },
      doc: {
        isAuthor: 0,
        hasPurchased: 0,
        hasStored: 0,
        articleInfo: {
          articleId: 0,
          articleTitle: "",
          articleAuthor: "",
          submissionDate: "",
          articlePrice: 0,
          articleType: "",
          articleAbstract: "",
          articleDownload: 0,
          articleStore: 0,
        },
        articleContext: "",
      },
      dialogFormVisible: false,
      loading: false,
      recomTable: [],
      hotTable: [],
    };
  },

  mounted() {
    this.docId = this.$route.query.id;
    this.keyWord = this.$route.params.key;
    this.type = this.$route.params.type;
    this.userId = localStorage.getItem("userId");
    this.$options.methods.getDoc.bind(this)();
    this.$options.methods.getRemain.bind(this)();
    this.$options.methods.getRecomTable.bind(this)();
    this.$options.methods.getHotTable.bind(this)();
  },

  watch: {},

  methods: {
    goBack() {
      this.$router.push({
        name: "doc",
        query: { search: this.keyWord, type: this.type },
      });
    },
    getDoc() {
      getArticleDetail(this.docId, this.userId).then((response) => {
        console.log(response);
        this.doc = response;
      });
    },
    getRemain() {
      userRemain(this.userId).then((response) => {
        this.userRemain = response;
      });
    },
    getRecomTable() {
      getRecommendArticle(this.userId).then((response) => {
        this.recomTable = response;
      });
    },
    getHotTable() {
      getPopularArticle().then((response) => {
        this.hotTable = response;
      });
    },
    addToCart() {
      addCart(this.docId, this.userId).then((response) => {
        this.$message({
          message: "添加成功！",
          type: "success",
          center: true,
        });
      });
    },
    buyDoc() {
      this.dialogFormVisible = true;
    },
    submitBuy() {
      this.loading = true;
      purchaseArticle(this.docId, this.userId).then((response) => {
        this.loading = false;
        this.$message({
          message: "购买成功！",
          type: "success",
          center: true,
        });
        this.doc.hasPurchased = 1;
        this.dialogFormVisible = false;
      });
    },
    downDoc() {
      window.location.replace(
        SERVER_BASE_URL + "/article/downloadArticle?id=" + this.docId
      );
    },
    storeDoc() {
      storeArticle(this.docId, this.userId).then((response) => {
        this.$message({
          message: "收藏成功！",
          type: "success",
          center: true,
        });
        this.doc.hasStored = 1;
      });
    },
    notStoreDoc() {
      storeCancel(this.docId, this.userId).then((response) => {
        this.$message({
          message: "取消收藏！",
          type: "warning",
          center: true,
        });
        this.doc.hasStored = 0;
      });
    },
    clickData(row, event, column) {
      this.$router.push({ name: "docDetail", query: { id: row.articleId } });
      location.reload();
    },
  },
};
</script>


