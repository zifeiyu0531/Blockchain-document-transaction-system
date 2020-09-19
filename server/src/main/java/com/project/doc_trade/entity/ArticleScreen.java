package com.project.doc_trade.entity;

// 文档搜索实体类
public class ArticleScreen {

    private int pageSize;
    private int pageNum;
    private String keyWord;
    private String type;


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


    public String getKeyWord(){
        return keyWord;
    }

    public void setKeyWord(String keyWord){
        this.keyWord = keyWord;
    }


    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }


    @Override
    public String toString() {
        return "ArticleScreen:{\n" +
                "pageSize:\t\t" + pageSize + "\n" +
                "pageNum:\t\t" + pageNum + "\n" +
                "keyWord:\t\t" + keyWord + "\n" +
                "type:\t\t" + type + "\n" +
                "}";
    }
}
