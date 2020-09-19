package com.project.doc_trade.entity;

// 用户-文档对
public class User2Article {

    private int articleId;
    private int userId;

    public User2Article(){}

    public User2Article(int articleId, int userId){
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
        return "User2Article:{\n" +
                "articleId:\t\t" + articleId + "\n" +
                "userId:\t\t" + userId + "\n" +
                "}";
    }
}
