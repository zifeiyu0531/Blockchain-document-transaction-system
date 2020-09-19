package com.project.doc_trade.entity;

// 文档详情实体类
public class ArticleDetail {

    private boolean isAuthor;
    private boolean hasPurchased;
    private boolean hasStored;
    private ArticleInfo articleInfo;
    private String articleContext;


    public boolean getIsAuthor() {
        return isAuthor;
    }

    public void setIsAuthor(boolean isAuthor) {
        this.isAuthor = isAuthor;
    }


    public boolean getHasPurchased() {
        return hasPurchased;
    }

    public void setHasPurchased(boolean hasPurchased) {
        this.hasPurchased = hasPurchased;
    }


    public boolean getHasStored() { return hasStored; }

    public void setHasStored(boolean hasStored) {
        this.hasStored = hasStored;
    }


    public ArticleInfo getArticleInfo() { return articleInfo; }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }


    public String getArticleContext() {
        return articleContext;
    }

    public void setArticleContext(String articleContext) {
        this.articleContext = articleContext;
    }

    @Override
    public String toString() {
        return "ArticleDetail:{\n" +
                "isAuthor:\t\t" + isAuthor + "\n" +
                "hasPurchased:\t\t" + hasPurchased + "\n" +
                "hasStored:\t\t" + hasStored + "\n" +
                "articleInfo:\t\t" + articleInfo.toString() + "\n" +
                "articleContext:\t\t" + articleContext + "\n" +
                "}";
    }
}
