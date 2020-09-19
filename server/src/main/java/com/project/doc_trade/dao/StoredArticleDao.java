package com.project.doc_trade.dao;

import com.project.doc_trade.entity.PurchasedArticle;
import com.project.doc_trade.entity.StoreArticle;
import com.project.doc_trade.entity.User2Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface StoredArticleDao extends BaseDao<StoreArticle> {
    /**
     * 添加收藏文档
     *
     * @param storeArticle 待添加的收藏文档
     * @return 添加的行数
     */
    @Insert("INSERT INTO stored_article (`article_id`, `user_id`)" +
            "VALUES (#{articleId}, #{userId})")
    int add(StoreArticle storeArticle);

    /**
     * 根据id删除收藏文档
     *
     * @param id 待删除收藏文档的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 根据收藏信息删除文档
     *
     * @param storeArticle 收藏信息
     * @return 删除的行数
     */
    @Delete("DELETE FROM stored_article WHERE article_id = #{articleId} AND user_id = #{userId}")
    int deleteByInfo(StoreArticle storeArticle);

    /**
     * 修改收藏文档
     *
     * @param storeArticle 待修改的收藏文档
     * @return 修改行数
     */
    int update(StoreArticle storeArticle);

    /**
     * 根据id查找收藏文档
     *
     * @param id 待查找的收藏文档id
     * @return 找到的收藏文档
     */
    StoreArticle findById(Serializable id);

    /**
     * 查找所有收藏文档
     *
     * @return 所有收藏文档的集合
     */
    List<StoreArticle> findAll();

    /**
     * 根据用户id查找所有收藏文档
     *
     * @param id 待查找的用户id
     * @return 找到的收藏文档的集合
     */
    @Select("SELECT * FROM stored_article WHERE user_id = #{id}")
    List<StoreArticle> findByUserId(Serializable id);

    /**
     * 根据用户-文档对象查找收藏
     *
     * @param user2Article 用户-文档对象
     * @return 找到的收藏
     */
    @Select("SELECT * FROM stored_article WHERE user_id = #{userId} AND article_id = #{articleId}")
    StoreArticle findByUser2Article(User2Article user2Article);
}
