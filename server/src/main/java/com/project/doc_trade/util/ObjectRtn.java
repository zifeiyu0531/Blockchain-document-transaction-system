package com.project.doc_trade.util;

// 搜索结果返回类型
public class ObjectRtn<T> {

    private int nums;
    private T obj;

    public ObjectRtn(int nums, T obj) {
        this.nums = nums;
        this.obj = obj;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }


    public T getList() {
        return obj;
    }

    public void setList(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ObjectRtn:{\n" +
                "nums:\t\t" + nums + "\n" +
                "obj:\t\t" + obj.toString() + "\n" +
                "}";
    }
}
