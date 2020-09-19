package com.project.doc_trade.util;

// 异常返回类型
public class RtnMsg {

    private int status_code;
    private String message;

    public RtnMsg(int status_code, String message) {
        this.status_code = status_code;
        this.message = message;
    }


    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RtnMsg:{\n" +
                "status_code:\t\t" + status_code + "\n" +
                "message:\t\t" + message + "\n" +
                "}";
    }
}