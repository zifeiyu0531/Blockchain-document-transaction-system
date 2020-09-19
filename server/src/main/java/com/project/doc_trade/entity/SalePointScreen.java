package com.project.doc_trade.entity;

// 上架积分筛选实体类
public class SalePointScreen {

    private int pageSize;
    private int pageNum;
    private int salePointType;
    private double salePointNumMin;
    private double salePointNumMax;
    private double salePointPriceMin;
    private double salePointPriceMax;


    public int getPageSize(){
        return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize = pageSize;
    }


    public int getPageNum(){
        return pageNum;
    }

    public void setPageNum(int pageNum){
        this.pageNum = pageNum;
    }


    public int getSalePointType() {
        return salePointType;
    }

    public void setSalePointType(int salePointType) {
        this.salePointType = salePointType;
    }


    public double getSalePointNumMin() {
        return salePointNumMin;
    }

    public void setSalePointNumMin(double salePointNumMin) {
        this.salePointNumMin = salePointNumMin;
    }


    public double getSalePointNumMax() {
        return salePointNumMax;
    }

    public void setSalePointNumMax(double salePointNumMax) {
        this.salePointNumMax = salePointNumMax;
    }


    public double getSalePointPriceMin() {
        return salePointPriceMin;
    }

    public void setSalePointPriceMin(double salePointPriceMin) {
        this.salePointPriceMin = salePointPriceMin;
    }


    public double getSalePointPriceMax() {
        return salePointPriceMax;
    }

    public void setSalePointPriceMax(double salePointPriceMax) {
        this.salePointPriceMax = salePointPriceMax;
    }

    @Override
    public String toString() {
        return "SalePointScreen:{\n" +
                "salePointType:\t\t" + salePointType + "\n" +
                "salePointNumMin:\t\t" + salePointNumMin + "\n" +
                "salePointNumMax:\t\t" + salePointNumMax + "\n" +
                "salePointPriceMin:\t\t" + salePointPriceMin + "\n" +
                "salePointPriceMax:\t\t" + salePointPriceMax + "\n" +
                "}";
    }
}
