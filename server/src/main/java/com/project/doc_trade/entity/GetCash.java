package com.project.doc_trade.entity;

// 提现实体
public class GetCash {

    private int id;
    private double cash;
    private String bankAccount;

    public int getId(){return this.id;}

    public void setId(int id){this.id = id;}


    public double getCash(){return this.cash;}

    public void setCash(double cash){this.cash = cash;}


    public String getBankAccount(){return this.bankAccount;}

    public void setBankAccount(String bankAccount){this.bankAccount = bankAccount;}

    @Override
    public String toString() {
        return "GetCash:{\n" +
                "id:\t\t" + id + "\n" +
                "cash:\t\t" + cash + "\n" +
                "}";
    }
}
