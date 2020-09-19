package com.project.doc_trade.entity;

import org.springframework.web.multipart.MultipartFile;

// 文档上传实体类
public class ArticleUpload {

    private String articleTitle;
    private int authorId;
    private String articleAuthor;
    private double articlePrice;
    private String articleType;
    private String articleAbstract;
    private MultipartFile file;

    public ArticleUpload() {
    }

    public ArticleUpload(
            String articleTitle,
            int authorId,
            String articleAuthor,
            double articlePrice,
            String articleType,
            String articleAbstract,
            MultipartFile file
    ) {
        this.articleTitle = articleTitle;
        this.authorId = authorId;
        this.articleAuthor = articleAuthor;
        this.articlePrice = articlePrice;
        this.articleType = articleType;
        this.articleAbstract = articleAbstract;
        this.file = file;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }


    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }


    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }


    public double getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(double articlePrice) {
        this.articlePrice = articlePrice;
    }


    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    @Override
    public String toString() {
        return "ArticleUpload:{\n" +
                "articleTitle:\t\t" + articleTitle + "\n" +
                "authorId:\t\t" + authorId + "\n" +
                "articleAuthor:\t\t" + articleAuthor + "\n" +
                "articlePrice:\t\t" + articlePrice + "\n" +
                "articleType:\t\t" + articleType + "\n" +
                "articleAbstract:\t\t" + articleAbstract + "\n" +
                "}";
    }
}
