package com.project.doc_trade.entity;

// 打包积分实体类
public class PointPack {

    private int salePointId;
    private int buyUserId;


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

    @Override
    public String toString() {
        return "PointRetail:{\n" +
                "salePointId:\t\t" + salePointId + "\n" +
                "buyUserId:\t\t" + buyUserId + "\n" +
                "}";
    }
}
