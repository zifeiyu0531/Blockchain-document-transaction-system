<template>
  <div class="clearfix">
    <el-button @click="dialogFormVisible = true" type="primary">上传文章</el-button>
    <el-dialog title="上传文章" width="500px" :visible.sync="dialogFormVisible" center>
      <el-form :model="form" ref="form" status-icon :rules="rules">
        <el-form-item label="文档标题" prop="articleTitle" :label-width="formLabelWidth">
          <el-input v-model="form.articleTitle"></el-input>
        </el-form-item>
        <el-form-item label="文档作者" prop="articleAuthor" :label-width="formLabelWidth">
          <el-input v-model="form.articleAuthor"></el-input>
        </el-form-item>
        <el-form-item label="文档价格" prop="articlePrice" :label-width="formLabelWidth">
          <el-input-number v-model="form.articlePrice" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-form-item label="文档类型" prop="articleType" :label-width="formLabelWidth">
          <el-select v-model="form.articleType" placeholder="请选择文章类型">
            <el-option v-for="(key, value) in types" :key="key" :label="value" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文档摘要" prop="articleAbstract" :label-width="formLabelWidth">
          <el-input type="textarea" :rows="3" v-model="form.articleAbstract"></el-input>
        </el-form-item>
        <input type="file" ref="file" />
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false;resetForm('form')">取 消</el-button>
        <el-button
          type="primary"
          @click="dialogFormVisible = false;submitForm('form');resetForm('form')"
        >确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { uploadArticle } from "../../network/doc";
import { uploadArticleByForm } from "../../network/doc";
import { Loading } from "element-ui";

export default {
  name: "upload",
  data() {
    return {
      userId: "",
      dialogFormVisible: false,
      loading: true,
      timer: null,
      formLabelWidth: "80px",
      form: {
        articleTitle: "",
        articleAuthor: "",
        articlePrice: 0,
        articleType: "",
        articleAbstract: "",
        file: null,
      },
      file: null,
      types: {
        全部类型: "",
        互联网: "互联网",
        医学: "医学",
        教育: "教育",
        计算机: "计算机",
        Java: "Java",
      },
      rules: {
        articleTitle: [
          {
            required: true,
            message: "请输入文章标题",
            trigger: "blur",
          },
        ],
        articleAuthor: [
          {
            required: true,
            message: "请输入作者",
            trigger: "blur",
          },
        ],
        articlePrice: [
          {
            required: true,
            message: "请选择价格",
            trigger: ["blur", "change"],
          },
        ],
        articleType: [
          {
            required: true,
            message: "请选择类型",
            trigger: ["blur", "change"],
          },
        ],
      },
    };
  },
  methods: {
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      let file = this.$refs.file.files[0];
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let formData = new FormData();
          formData.append("articleTitle", this.form.articleTitle);
          formData.append("authorId", this.userId);
          formData.append("articleAuthor", this.form.articleAuthor);
          formData.append("articlePrice", this.form.articlePrice);
          formData.append("articleType", this.form.articleType);
          formData.append("articleAbstract", this.form.articleAbstract);
          formData.append("file", file);
          uploadArticleByForm(formData)
            .then((response) => {
              if (response.status_code != "0") {
                let loadingInstance = Loading.service({
                  fullscreen: true,
                  text: "文档查重中...",
                });
                clearTimeout(this.timer);
                this.timer = setTimeout(() => {
                  this.$nextTick(() => {
                    // 以服务的方式调用的 Loading 需要异步关闭
                    loadingInstance.close();
                  });
                  this.$message.success({
                    message: "上传成功",
                    showClose: true,
                    type: "success",
                  });
                  this.resetForm(formName);
                }, 3000);
              } else {
                this.$message.error({
                  message: "上传失败",
                  showClose: true,
                  type: "error",
                });
              }
            })
            .catch((response) => {
              this.$message.error({
                message: "上传失败",
                showClose: true,
                type: "error",
              });
            });
        } else {
          this.$message.error({
            message: "提交失败, 请检查表项",
            showClose: true,
            type: "error",
          });
          console.log("error submit!!");
          return false;
        }
      });
    },
  },
  mounted() {
    this.userId = localStorage.getItem("userId");
  },
};
</script>

<style>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
