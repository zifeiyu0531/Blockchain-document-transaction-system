package com.project.doc_trade.entity;

// 上架积分信息实体类
public class SalePointInfo {

    private SalePoint salePoint;
    private String salePointUserName;


    public String getSalePointUserName() {
        return salePointUserName;
    }

    public void setSalePointUserName(String salePointUserName) { this.salePointUserName = salePointUserName; }


    public SalePoint getSalePoint() {
        return salePoint;
    }

    public void setSalePoint(SalePoint salePoint) { this.salePoint = salePoint; }

    @Override
    public String toString() {
        return "SalePointInfo:{\n" +
                "salePoint:\t\t" + salePoint.toString() + "\n" +
                "salePointUserName:\t\t" + salePointUserName + "\n" +
                "}";
    }
}
