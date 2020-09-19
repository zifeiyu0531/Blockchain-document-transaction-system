package com.project.doc_trade.entity;

// 文档评分实体类
public class ArticleRate {

    private int articleId;
    private int userId;
    private int rate;


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ArticleRate:{\n" +
                "articleId:\t\t" + articleId + "\n" +
                "userId:\t\t" + userId + "\n" +
                "rate:\t\t" + rate + "\n" +
                "}";
    }
}
