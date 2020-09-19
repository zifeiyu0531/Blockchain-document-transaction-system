package com.project.doc_trade.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {
    /**
     * 添加对象
     *
     * @param t 待添加的对象
     * @return 添加的行数
     */
    int add(T t);

    /**
     * 根据id删除对象
     *
     * @param id 待删除对象的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改对象
     *
     * @param t 待修改的对象
     * @return 修改行数
     */
    int update(T t);

    /**
     * 根据id查找对象
     *
     * @param id 待查找的对象id
     * @return 找到的对象
     */
    T findById(Serializable id);

    /**
     * 查找所有对象
     *
     * @return 所有对象的集合
     */
    List<T> findAll();
}
