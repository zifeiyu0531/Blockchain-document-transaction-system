package com.project.doc_trade.entity;

import java.util.Date;

//上架文档实体类
public class Article {

    private int articleId;
    private String articleTitle;
    private int authorId;
    private String articleAuthor;
    private String submissionDate;
    private double articlePrice;
    private String articleType;
    private String articleMd5;
    private String articleAbstract;
    private String articleUrl;
    private int articleDownload;
    private int articleStore;


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
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


    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
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


    public String getArticleMd5() {
        return articleMd5;
    }

    public void setArticleMd5(String articleMd5) {
        this.articleMd5 = articleMd5;
    }


    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }


    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }


    public int getArticleDownload() {
        return articleDownload;
    }

    public void setArticleDownload(int articleDownload) {
        this.articleDownload = articleDownload;
    }


    public int getArticleStore() {
        return articleStore;
    }

    public void setArticleStore(int articleStore) {
        this.articleStore = articleStore;
    }

    @Override
    public String toString() {
        return "Article:{\n" +
                "articleId:\t\t" + articleId + "\n" +
                "articleTitle:\t\t" + articleTitle + "\n" +
                "authorId:\t\t" + authorId + "\n" +
                "articleAuthor:\t\t" + articleAuthor + "\n" +
                "submissionDate:\t\t" + submissionDate + "\n" +
                "articlePrice:\t\t" + articlePrice + "\n" +
                "articleType:\t\t" + articleType + "\n" +
                "articleMd5:\t\t" + articleMd5 + "\n" +
                "articleAbstract:\t\t" + articleAbstract + "\n" +
                "articleUrl:\t\t" + articleUrl + "\n" +
                "articleDownload:\t\t" + articleDownload + "\n" +
                "articleStore:\t\t" + articleStore + "\n" +
                "}";
    }
}
