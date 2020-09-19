package com.project.doc_trade.entity;

//用户实体类
public class User {

    private int userId;
    private String userName;
    private String userPhone;
    private String userPassword;
    private double userPoint;
    private double userRmb;
    private String publicKey;
    private String privateKey;

    public User(){}

    public User(String userName, String userPassword, String userPhone, String publicKey, String privateKey){
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

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


    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    public double getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(double userPoint) {
        this.userPoint = userPoint;
    }


    public double getUserRmb() {
        return userRmb;
    }

    public void setUserRmb(double userRmb) {
        this.userRmb = userRmb;
    }


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "User:{\n" +
                "userId:\t\t" + userId + "\n" +
                "userName:\t\t" + userName + "\n" +
                "userPassword:\t\t" + userPassword + "\n" +
                "userPhone:\t\t" + userPhone + "\n" +
                "userPoint:\t\t" + userPoint + "\n" +
                "userRmb:\t\t" + userRmb + "\n" +
                "publicKey:\t\t" + publicKey + "\n" +
                "privateKey:\t\t" + privateKey + "\n" +
                "}";
    }
}
