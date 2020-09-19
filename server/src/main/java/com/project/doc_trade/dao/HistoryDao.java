package com.project.doc_trade.dao;

import com.project.doc_trade.entity.History;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface HistoryDao extends BaseDao<History> {
    /**
     * 添加历史
     *
     * @param history 待添加的历史
     * @return 添加的行数
     */
    int add(History history);

    /**
     * 根据id删除历史
     *
     * @param id 待删除历史的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改历史
     *
     * @param history 待修改的历史
     * @return 修改行数
     */
    int update(History history);

    /**
     * 根据id查找历史
     *
     * @param id 待查找的历史id
     * @return 找到的历史
     */
    History findById(Serializable id);

    /**
     * 查找所有历史
     *
     * @return 所有历史的集合
     */
    List<History> findAll();

    /**
     * 根据用户id查找积分历史
     *
     * @param id 用户id
     * @return 积分历史对象列表
     */
    @Select("SELECT * FROM point_history WHERE user_id = #{id}")
    List<History> findPointByUserId(Serializable id);

    /**
     * 根据用户id查找现金历史
     *
     * @param id 用户id
     * @return 现金历史对象列表
     */
    @Select("SELECT * FROM rmb_history WHERE user_id = #{id}")
    List<History> findRmbByUserId(Serializable id);

    /**
     * 添加积分历史记录
     *
     * @param history 积分历史对象
     * @return 影响行数
     */
    @Insert("INSERT INTO point_history (`user_id`, `num`, `type`, `date`) " +
            "VALUES (#{userId}, #{num}, #{type}, #{date})")
    int addPoint(History history);

    /**
     * 添加现金历史记录
     *
     * @param history 现金历史对象
     * @return 影响行数
     */
    @Insert("INSERT INTO rmb_history (`user_id`, `num`, `type`, `date`) " +
            "VALUES (#{userId}, #{num}, #{type}, #{date})")
    int addRmb(History history);
}
