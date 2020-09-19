package com.project.doc_trade.entity;

import java.util.ArrayList;
import java.util.List;

// 购物车实体类
public class Cart {

    private List<PurchasedArticle> purchasedList;


    public List<PurchasedArticle> getPurchasedList() {
        return purchasedList;
    }

    public void setPurchasedList(List<PurchasedArticle> purchasedList) {
        this.purchasedList = purchasedList;
    }

    @Override
    public String toString() {
        return "Cart:{\n" +
                "purchasedList:\t\t" + purchasedList.toString() + "\n" +
                "}";
    }
}
