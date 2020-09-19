package com.project.doc_trade.entity;

//用户登录实体类
public class UserSign {

    private String userName;
    private String userPhone;
    private String userPassword;
    private String userCode;

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


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public String toString() {
        return "UserSign:{\n" +
                "userName:\t\t" + userName + "\n" +
                "userPhone:\t\t" + userPhone + "\n" +
                "userPassword:\t\t" + userPassword + "\n" +
                "}";
    }
}
