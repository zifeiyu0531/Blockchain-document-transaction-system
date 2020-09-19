package com.project.doc_trade.entity;

// 银行账户实体类
public class BankAccount {

    private int userId;
    private String bankAccount;
    private String bankId;
    private String phoneNumber;
    private String idCardNumber;
    private String realName;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "BankAccount:{\n" +
                "userId:\t\t" + userId + "\n" +
                "bankAccount:\t\t" + bankAccount + "\n" +
                "bankId:\t\t" + bankId + "\n" +
                "phoneNumber:\t\t" + phoneNumber + "\n" +
                "idCardNumber:\t\t" + idCardNumber + "\n" +
                "realName:\t\t" + realName + "\n" +
                "}";
    }
}
