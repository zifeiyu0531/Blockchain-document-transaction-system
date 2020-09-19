package com.project.doc_trade.dao;

import com.project.doc_trade.entity.User2Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CartDao extends BaseDao<User2Article> {
    /**
     * 添加购物车
     *
     * @param user2Article 待添加的购物车
     * @return 添加的行数
     */
    @Insert("INSERT INTO cart (`article_id`, `user_id`) VALUES (#{articleId}, #{userId})")
    int add(User2Article user2Article);

    /**
     * 根据id删除购物车
     *
     * @param id 待删除购物车的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 根据用户-文档删除购物车
     *
     * @param user2Article 用户-文档对象
     * @return 删除的行数
     */
    @Delete("DELETE FROM cart WHERE article_id = #{articleId} AND user_id = #{userId}")
    int deleteByUser2Article(User2Article user2Article);

    /**
     * 修改购物车
     *
     * @param user2Article 待修改的购物车
     * @return 修改行数
     */
    int update(User2Article user2Article);

    /**
     * 根据id查找购物车
     *
     * @param id 待查找的购物车id
     * @return 找到的购物车
     */
    User2Article findById(Serializable id);

    /**
     * 查找所有购物车
     *
     * @return 所有购物车的集合
     */
    List<User2Article> findAll();

    /**
     * 根据用户-文档对象查找购物车信息
     *
     * @param user2Article 用户-文档对象
     * @return 找到的购物车信息
     */
    @Select("SELECT * FROM cart WHERE article_id = #{articleId} AND user_id = #{userId}")
    User2Article findByUser2Article(User2Article user2Article);

    /**
     * 根据用户id获取购物车信息
     *
     * @param id 用户id
     * @return 获取到的购物车信息
     */
    @Select("SELECT * FROM cart WHERE user_id = #{id}")
    List<User2Article> findByUserId(Serializable id);
}
