package com.project.doc_trade.dao;

import com.project.doc_trade.entity.Article;
import com.project.doc_trade.entity.ArticleInfo;
import com.project.doc_trade.entity.ArticleUpload;
import com.project.doc_trade.entity.User2Article;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface ArticleDao extends BaseDao<Article> {
    /**
     * 添加文档
     *
     * @param article 待添加的文档
     * @return 添加的行数
     */
    int add(Article article);

    /**
     * 根据id删除文档
     *
     * @param id 待删除文档的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改文档
     *
     * @param article 待修改的文档
     * @return 修改行数
     */
    int update(Article article);

    /**
     * 根据id查找文档
     *
     * @param id 待查找的文档id
     * @return 找到的文档
     */
    @Select("SELECT * FROM article WHERE article_id = #{id}")
    Article findById(Serializable id);

    /**
     * 根据id查找文档信息
     *
     * @param id 待查找的文档id
     * @return 找到的文档信息
     */
    @Select("SELECT * FROM article WHERE article_id = #{id}")
    ArticleInfo findInfoById(Serializable id);

    /**
     * 查找所有文档
     *
     * @return 所有文档的集合
     */
    List<Article> findAll();

    /**
     * 根据关键词查找文档
     *
     * @param keyword 待查找的文档关键词
     * @return 找到的文档集合
     */
    List<Article> findByKeyword(String keyword);

    /**
     * 根据类型查找文档
     *
     * @param type 待查找的文档类型
     * @return 找到的文档集合
     */
    List<Article> findByType(String type);

    /**
     * 根据用户id查找文档
     *
     * @param id 待查找的用户id
     * @return 找到的文档集合
     */
    @Select("SELECT * FROM article WHERE author_id = #{id}")
    List<ArticleInfo> findByUserId(Serializable id);

    /**
     * 根据热门程度查找文档
     *
     * @return 找到的文档集合
     */
    @Select("SELECT * FROM article ORDER BY article_download + article_store DESC LIMIT 10")
    List<ArticleInfo> findByPopular();

    /**
     * 获取数据表内总数据量
     *
     * @return 数据量
     */
    @Select("SELECT COUNT(*) FROM article")
    int getCounts();

    /**
     * 根据用户-文档对象查找文档bank_id
     *
     * @param user2Article 用户-文档对象
     * @return 找到的文档
     */
    @Select("SELECT * FROM article WHERE author_id = #{userId} AND article_id = #{articleId}")
    Article findByUser2Article(User2Article user2Article);

    /**
     * 根据文档id查找url
     *
     * @param id 文档id
     * @return 文档url地址
     */
    @Select("SELECT article_url FROM article WHERE article_id = #{id}")
    String findUrlById(Serializable id);

    /**
     * 上传文档
     *
     * @param articleUpload 文档上传对象
     * @param date          上传日期
     * @param url           存储地址
     * @return 改变行数
     */
    @Insert("INSERT INTO article (`article_title`, `author_id`, `article_author`, `submission_date`, `article_price`, `article_type`, `article_md5`, `article_abstract`, `article_url`) " +
            "VALUES (#{articleUpload.articleTitle}, #{articleUpload.authorId}, #{articleUpload.articleAuthor}, #{date}, #{articleUpload.articlePrice}, #{articleUpload.articleType}, #{vec}, #{articleUpload.articleAbstract}, #{url})")
    int uploadArticle(ArticleUpload articleUpload, String vec, String date, String url);

    /**
     * 根据id更新下载次数
     *
     * @param id 文档id
     * @return 改变行数
     */
    @Update("UPDATE article SET article_download = article_download + 1 WHERE article_id = #{id}")
    int updateDownloadById(Serializable id);

    /**
     * 根据id更新收藏次数
     *
     * @param id 文档id
     * @return 改变行数
     */
    @Update("UPDATE article SET article_store = article_store + 1 WHERE article_id = #{id}")
    int updateStoreById(Serializable id);

    /**
     * 查找所有文档向量
     *
     * @return 找到的向量列表
     */
    @Select("SELECT article_md5 FROM article")
    List<String> findAllVec();

    /**
     * 根据向量查找文档
     *
     * @param vec 文档向量
     * @return 找到的文档对象
     */
    @Select("SELECT * FROM article WHERE article_md5 = #{vec}")
    Article findByVec(String vec);

    @Select("SELECT COUNT(*) FROM article WHERE author_id = #{id}")
    int findArticleCountByUserId(Serializable id);
}
