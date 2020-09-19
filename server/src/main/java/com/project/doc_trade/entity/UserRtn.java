package com.project.doc_trade.entity;

public class UserRtn {

    private UserInfo userInfo;
    private int userArticle;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    public int getUserArticle() {
        return userArticle;
    }

    public void setUserArticle(int userArticle) {
        this.userArticle = userArticle;
    }


    @Override
    public String toString() {
        return "UserRtn:{\n" +
                "userInfo:\t\t" + userInfo.toString() + "\n" +
                "userArticle:\t\t" + userArticle + "\n" +
                "}";
    }
}
