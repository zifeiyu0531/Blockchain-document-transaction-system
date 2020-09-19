package com.project.doc_trade.service;

import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.util.ListRtn;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

// 文档市场service类
public interface ArticleService {
    /**
     * 筛选文档信息
     *
     * @param articleScreen 筛选条件
     * @return 搜索到的文档信息列表ListRtn<ArticleInfo>
     * @throws MyException 自定义异常
     */
    ListRtn<ArticleInfo> getArticleByScreen(ArticleScreen articleScreen) throws MyException;

    /**
     * 按关键词搜索文档
     *
     * @param keyword 在搜索栏中输入的关键词
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> searchArticleKeyword(String keyword) throws MyException;

    /**
     * 按类型搜索文档
     *
     * @param type 选择的文档类型
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> searchArticleType(String type) throws MyException;

    /**
     * 获取向用户推荐的文档
     *
     * @param id 用户id
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> getRecommendArticle(Serializable id) throws MyException;

    /**
     * 获取最热门的10篇文档
     *
     * @return 搜索到的文档列表
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> getPopularArticle() throws MyException;

    /**
     * 获取文档详情
     *
     * @param user2Article 用户-文档对象
     * @return 文档详情
     * @throws MyException 自定义异常
     */
    ArticleDetail getArticleDetail(User2Article user2Article) throws MyException;

    /**
     * 购买文档
     *
     * @param purchasedArticle 购买信息对象
     * @throws MyException 自定义异常
     */
    void purchaseArticle(PurchasedArticle purchasedArticle) throws MyException;

    /**
     * 添加购物车
     *
     * @param user2Article 用户-文档对象
     * @throws MyException 自定义异常
     */
    void addCart(User2Article user2Article) throws MyException;

    /**
     * 删除购物车
     *
     * @param user2Article 用户-文档对象
     * @throws MyException 自定义异常
     */
    void deleteCart(User2Article user2Article) throws MyException;

    /**
     * 查看购物车
     *
     * @param id 用户id
     * @return 购物车内文档信息
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> getCart(Serializable id) throws MyException;

    /**
     * 处理购物车
     *
     * @param cart 购物车对象
     * @throws MyException 自定义异常
     */
    void dealCart(Cart cart) throws MyException;

    /**
     * 下载文档
     *
     * @param response HTTP请求response对象
     * @param id       文档id
     * @return 文档下载情况
     * @throws MyException 自定义异常
     */
    String downloadArticle(HttpServletResponse response, Serializable id) throws MyException;

    /**
     * 上传文档
     *
     * @param articleUpload 文档上传对象
     * @throws MyException 自定义异常
     */
    void uploadArticle(ArticleUpload articleUpload) throws MyException;

    /**
     * 收藏文档
     *
     * @param storeArticle 收藏文档对象
     * @throws MyException 自定义异常
     */
    void storeArticle(StoreArticle storeArticle) throws MyException;

    /**
     * 取消收藏
     *
     * @param storeArticle 收藏文档对象
     * @throws MyException 自定义异常
     */
    void storeCancel(StoreArticle storeArticle) throws MyException;

    /**
     * 文档评分
     *
     * @param articleRate 文档评分对象
     * @throws MyException 自定义异常
     */
    void rateArticle(ArticleRate articleRate) throws MyException;
}
