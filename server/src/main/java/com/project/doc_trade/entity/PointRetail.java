package com.project.doc_trade.entity;

// 零售积分实体类
public class PointRetail {

    private int salePointId;
    private int buyUserId;
    private double orderPoint;


    public int getSalePointId() {
        return salePointId;
    }

    public void setSalePointId(int salePointId) {
        this.salePointId = salePointId;
    }


    public int getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(int buyUserId) {
        this.buyUserId = buyUserId;
    }


    public double getOrderPoint() {
        return orderPoint;
    }

    public void setOrderPoint(double orderPoint) {
        this.orderPoint = orderPoint;
    }

    @Override
    public String toString() {
        return "PointRetail:{\n" +
                "salePointId:\t\t" + salePointId + "\n" +
                "buyUserId:\t\t" + buyUserId + "\n" +
                "orderPoint:\t\t" + orderPoint + "\n" +
                "}";
    }
}
