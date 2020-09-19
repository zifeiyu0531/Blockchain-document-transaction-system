package com.project.doc_trade.entity;

// 文档详情实体类
public class ArticleInfo {

    private int articleId;
    private String articleTitle;
    private String articleAuthor;
    private String submissionDate;
    private double articlePrice;
    private String articleType;
    private String articleAbstract;
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


    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
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
        return "ArticleInfo:{\n" +
                "articleId:\t\t" + articleId + "\n" +
                "articleTitle:\t\t" + articleTitle + "\n" +
                "articleAuthor:\t\t" + articleAuthor + "\n" +
                "submissionDate:\t\t" + submissionDate + "\n" +
                "articlePrice:\t\t" + articlePrice + "\n" +
                "articleType:\t\t" + articleType + "\n" +
                "articleAbstract:\t\t" + articleAbstract + "\n" +
                "articleDownload:\t\t" + articleDownload + "\n" +
                "}";
    }
}
