package com.project.doc_trade.dao;

import com.project.doc_trade.entity.PointOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface PointOrderDao extends BaseDao<PointOrder> {
    /**
     * 添加积分订单
     *
     * @param pointOrder 待添加的积分订单
     * @return 添加的行数
     */
    @Insert("INSERT INTO point_order (`order_point`, `order_price`, `buy_user_id`, `sale_user_id`, `order_date`) " +
            "VALUES (#{orderPoint}, #{orderPrice}, #{buyUserId}, #{saleUserId}, #{orderDate})")
    int add(PointOrder pointOrder);

    /**
     * 根据id删除积分订单
     *
     * @param id 待删除积分订单的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改积分订单
     *
     * @param pointOrder 待修改的积分订单
     * @return 修改行数
     */
    int update(PointOrder pointOrder);

    /**
     * 根据id查找积分订单
     *
     * @param id 待查找的积分订单id
     * @return 找到的积分订单
     */
    PointOrder findById(Serializable id);

    /**
     * 查找所有积分订单
     *
     * @return 所有积分订单的集合
     */
    List<PointOrder> findAll();

    /**
     * 查找最新的10个订单
     *
     * @return 所有积分订单的集合
     */
    @Select("SELECT * FROM point_order ORDER BY order_id DESC LIMIT 0, 50")
    List<PointOrder> findLast10();

    /**
     * 根据用户id查找所有积分订单
     *
     * @param id 待查找的用户id
     * @return 找到的积分订单的集合
     */
    List<PointOrder> findByUserId(Serializable id);
}
