package com.project.doc_trade.entity;

import java.util.Date;

//积分订单实体类
public class PointOrder {

    private int orderId;
    private double orderPoint;
    private double orderPrice;
    private int buyUserId;
    private int saleUserId;
    private String orderDate;

    public PointOrder(){}

    public PointOrder(double orderPoint, double orderPrice, int buyUserId, int saleUserId, String orderDate) {
        this.orderPoint = orderPoint;
        this.orderPrice = orderPrice;
        this.buyUserId = buyUserId;
        this.saleUserId = saleUserId;
        this.orderDate = orderDate;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public double getOrderPoint() {
        return orderPoint;
    }

    public void setOrderPoint(double orderPoint) {
        this.orderPoint = orderPoint;
    }


    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }


    public int getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(int buyUserId) {
        this.buyUserId = buyUserId;
    }


    public int getSaleUserId() {
        return saleUserId;
    }

    public void setSaleUserId(int saleUserId) {
        this.saleUserId = saleUserId;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "PointOrder:{\n" +
                "orderId:\t\t" + orderId + "\n" +
                "orderPoint:\t\t" + orderPoint + "\n" +
                "orderPrice:\t\t" + orderPrice + "\n" +
                "buyUserId:\t\t" + buyUserId + "\n" +
                "saleUserId:\t\t" + saleUserId + "\n" +
                "orderDate:\t\t" + orderDate + "\n" +
                "}";
    }
}
