package com.project.doc_trade.entity;

// 用于登录/注册返回的用户信息实体类
public class UserInfo {

    private int userId;
    private String userName;
    private String userPhone;
    private double userPoint;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public double getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(double userPoint) {
        this.userPoint = userPoint;
    }

    @Override
    public String toString() {
        return "UserInfo:{\n" +
                "userId:\t\t" + userId + "\n" +
                "userName:\t\t" + userName + "\n" +
                "userPhone:\t\t" + userPhone + "\n" +
                "userPoint:\t\t" + userPoint + "\n" +
                "}";
    }
}
