package com.project.doc_trade.util;

import java.util.List;

// 搜索结果返回类型
public class ListRtn<T> {

    private int nums;
    private List<T> list;

    public ListRtn(int nums, List<T> list) {
        this.nums = nums;
        this.list = list;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListRtn:{\n" +
                "nums:\t\t" + nums + "\n" +
                "list:\t\t" + list.toString() + "\n" +
                "}";
    }
}
