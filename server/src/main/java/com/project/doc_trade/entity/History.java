package com.project.doc_trade.entity;

// 历史实体类
public class History {

    private int userId;
    private double num;
    private int type;
    private String date;

    public History(){}

    public History(int userId, double num, int type, String date) {
        this.userId = userId;
        this.num = num;
        this.type = type;
        this.date = date;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History:{\n" +
                "userId:\t\t" + userId + "\n" +
                "num:\t\t" + num + "\n" +
                "type:\t\t" + type + "\n" +
                "date:\t\t" + date + "\n" +
                "}";
    }
}
