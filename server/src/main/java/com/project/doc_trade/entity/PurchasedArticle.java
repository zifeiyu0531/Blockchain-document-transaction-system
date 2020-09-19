package com.project.doc_trade.entity;

//文档订单实体类
public class PurchasedArticle extends User2Article {

    private int articleId;
    private int userId;

    public PurchasedArticle() {
    }

    public PurchasedArticle(int articleId, int userId) {
        this.articleId = articleId;
        this.userId = userId;
    }


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

    @Override
    public String toString() {
        return "PurchasedArticle:{\n" +
                "articleId:\t\t" + articleId + "\n" +
                "userId:\t\t" + userId + "\n" +
                "}";
    }
}
