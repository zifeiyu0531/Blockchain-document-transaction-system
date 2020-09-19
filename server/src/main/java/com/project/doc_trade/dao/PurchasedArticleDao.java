package com.project.doc_trade.dao;

import com.project.doc_trade.entity.PurchasedArticle;
import com.project.doc_trade.entity.User2Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface PurchasedArticleDao extends BaseDao<PurchasedArticle> {
    /**
     * 添加文档订单
     *
     * @param purchasedArticle 待添加的文档订单
     * @return 添加的行数
     */
    @Insert("INSERT INTO purchased_article (`article_id`, `user_id`)" +
            "VALUES (#{articleId}, #{userId})")
    int add(PurchasedArticle purchasedArticle);

    /**
     * 根据id删除文档订单
     *
     * @param id 待删除文档订单的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改文档订单
     *
     * @param purchasedArticle 待修改的文档订单
     * @return 修改行数
     */
    int update(PurchasedArticle purchasedArticle);

    /**
     * 根据id查找文档订单
     *
     * @param id 待查找的文档订单id
     * @return 找到的文档订单
     */
    PurchasedArticle findById(Serializable id);

    /**
     * 查找所有文档订单
     *
     * @return 所有文档订单的集合
     */
    List<PurchasedArticle> findAll();

    /**
     * 根据用户id查找所有文档订单
     *
     * @param id 待查找的用户id
     * @return 找到的文档订单的集合
     */
    @Select("SELECT * FROM purchased_article WHERE user_id = #{id}")
    List<PurchasedArticle> findByUserId(Serializable id);

    /**
     * 根据用户-文档对象查找订单
     *
     * @param user2Article 用户-文档对象
     * @return 找到的文档订单
     */
    @Select("SELECT * FROM purchased_article WHERE user_id = #{userId} AND article_id = #{articleId}")
    PurchasedArticle findByUser2Article(User2Article user2Article);
}
