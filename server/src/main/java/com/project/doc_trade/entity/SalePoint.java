package com.project.doc_trade.entity;

//上架积分实体类
public class SalePoint {

    private int salePointId;
    private double salePointNum;
    private int salePointUser;
    private int salePointType;
    private double salePointRemain;
    private double salePointPrice;


    public int getSalePointId() {
        return salePointId;
    }

    public void setSalePointId(int salePointId) {
        this.salePointId = salePointId;
    }

    public double getSalePointNum() {
        return salePointNum;
    }

    public void setSalePointNum(double salePointNum) {
        this.salePointNum = salePointNum;
    }


    public int getSalePointUser() {
        return salePointUser;
    }

    public void setSalePointUser(int salePointUser) {
        this.salePointUser = salePointUser;
    }


    public int getSalePointType() {
        return salePointType;
    }

    public void setSalePointType(int salePointType) {
        this.salePointType = salePointType;
    }


    public double getSalePointRemain() {
        return salePointRemain;
    }

    public void setSalePointRemain(double salePointRemain) {
        this.salePointRemain = salePointRemain;
    }


    public double getSalePointPrice() {
        return salePointPrice;
    }

    public void setSalePointPrice(double salePointPrice) {
        this.salePointPrice = salePointPrice;
    }

    @Override
    public String toString() {
        return "SalePoint:{\n" +
                "salePointNum:\t\t" + salePointNum + "\n" +
                "salePointUser:\t\t" + salePointUser + "\n" +
                "salePointType:\t\t" + salePointType + "\n" +
                "salePointRemain:\t\t" + salePointRemain + "\n" +
                "salePointPrice:\t\t" + salePointPrice + "\n" +
                "}";
    }
}
