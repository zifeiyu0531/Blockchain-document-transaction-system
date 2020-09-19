package com.project.doc_trade.entity;

// 用户余额实体类
public class UserRemain {

    private double userPoint;
    private double userRmb;

    public UserRemain(){}

    public UserRemain(double userPoint, double userRmb){
        this.userPoint = -1;
        this.userRmb = -1;
    }


    public double getuserPoint() {
        return userPoint;
    }

    public void setuserPoint(double userPoint) {
        this.userPoint = userPoint;
    }


    public double getuserRmb() {
        return userRmb;
    }

    public void setuserRmb(double userRmb) {
        this.userRmb = userRmb;
    }

    @Override
    public String toString() {
        return "UserRemain:{\n" +
                "userPoint:\t\t" + userPoint + "\n" +
                "userRmb:\t\t" + userRmb + "\n" +
                "}";
    }
}
