package com.project.doc_trade.controller;

import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.service.ArticleService;
import com.project.doc_trade.util.ListRtn;
import com.project.doc_trade.util.RtnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 文档搜索 URL：/article/getArticleByScreen
     *
     * @param articleScreen 文档搜索内容
     * @return 返回文档搜索结果列表ListRtn<ArticleInfo>
     */
    @RequestMapping(value = "/getArticleByScreen", method = RequestMethod.POST)
    public ListRtn<ArticleInfo> getArticleByScreen(@RequestBody ArticleScreen articleScreen) {
        try {
            return articleService.getArticleByScreen(articleScreen);
        } catch (MyException e) {
            return new ListRtn<>(0, new ArrayList<>());
        }
    }

    /**
     * 获取推荐文档 URL：/article/getRecommendArticle
     *
     * @param id 待推荐的用户id
     * @return 返回文档推荐结果列表List<ArticleInfo>
     */
    @RequestMapping(value = "/getRecommendArticle", method = RequestMethod.GET)
    public List<ArticleInfo> getRecommendArticle(@RequestParam(value = "id") Serializable id) {
        try {
            return articleService.getRecommendArticle(id);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 获取最热门的10篇文档 URL：/article/getPopularArticle
     *
     * @return 返回文档结果列表List<ArticleInfo>
     */
    @RequestMapping(value = "/getPopularArticle", method = RequestMethod.GET)
    public List<ArticleInfo> getPopularArticle() {
        try {
            return articleService.getPopularArticle();
        } catch (MyException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取文档详情 URL：/article/getArticleDetail
     *
     * @param article_id 文档id
     * @param user_id    用户id
     * @return 返回文档详情ArticleDetail对象
     */
    @RequestMapping(value = "/getArticleDetail", method = RequestMethod.GET)
    public ArticleDetail getArticleDetail(@RequestParam(value = "article_id") int article_id, @RequestParam(value = "user_id") int user_id) {
        try {
            return articleService.getArticleDetail(new User2Article(article_id, user_id));
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArticleDetail();
        }
    }

    /**
     * 购买文档 URL：/article/purchaseArticle
     *
     * @param article_id 文档id
     * @param user_id    用户id
     * @return 返回购买结果消息对象RtnMsg
     */
    @RequestMapping(value = "/purchaseArticle", method = RequestMethod.GET)
    public RtnMsg purchaseArticle(@RequestParam(value = "article_id") int article_id, @RequestParam(value = "user_id") int user_id) {
        try {
            articleService.purchaseArticle(new PurchasedArticle(article_id, user_id));
            return new RtnMsg(1, "文档购买成功!");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 添加购物车 URL：/article/addCart
     *
     * @param article_id 文档id
     * @param user_id    用户id
     * @return 返回添加购物车结果消息对象RtnMsg
     */
    @RequestMapping(value = "/addCart", method = RequestMethod.GET)
    public RtnMsg addCart(@RequestParam(value = "article_id") int article_id, @RequestParam(value = "user_id") int user_id) {
        try {
            articleService.addCart(new User2Article(article_id, user_id));
            return new RtnMsg(1, "添加购物车成功!");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 删除购物车 URL：/article/deleteCart
     *
     * @param article_id 文档id
     * @param user_id    用户id
     * @return 返回删除购物车结果消息对象RtnMsg
     */
    @RequestMapping(value = "/deleteCart", method = RequestMethod.GET)
    public RtnMsg deleteCart(@RequestParam(value = "article_id") int article_id, @RequestParam(value = "user_id") int user_id) {
        try {
            articleService.deleteCart(new User2Article(article_id, user_id));
            return new RtnMsg(1, "删除购物车成功!");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 查看购物车 URL：/article/getCart
     *
     * @param id 用户id
     * @return 购物车内文档信息
     */
    @RequestMapping(value = "/getCart", method = RequestMethod.GET)
    public List<ArticleInfo> getCart(@RequestParam(value = "id") Serializable id) {
        try {
            return articleService.getCart(id);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 处理购物车 URL：/article/dealCart
     *
     * @param cart 购物车对象
     * @return 返回处理结果消息对象RtnMsg
     */
    @RequestMapping(value = "/dealCart", method = RequestMethod.POST)
    public RtnMsg dealCart(@RequestBody Cart cart) {
        try {
            articleService.dealCart(cart);
            return new RtnMsg(1, "处理购物车成功!");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 下载文档 URL：/article/downloadArticle
     *
     * @param response 响应对象
     * @param id       待下载的文档id
     * @return 返回下载结果消息对象RtnMsg
     */
    @RequestMapping(value = "/downloadArticle", method = RequestMethod.GET)
    public String downloadArticle(HttpServletResponse response, @RequestParam(value = "id") Serializable id) {
        try {
            return articleService.downloadArticle(response, id);
        } catch (MyException e) {
            return "download fail";
        }
    }

    /**
     * 上传文档 URL：/article/uploadArticle
     *
     * @param articleTitle    标题
     * @param authorId        id
     * @param articleAuthor   作者
     * @param articlePrice    价格
     * @param articleType     类型
     * @param articleAbstract 摘要
     * @param file            文件
     * @return 自定义返回对象
     */
    @RequestMapping(value = "/uploadArticle", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
    public RtnMsg uploadArticle(
            @RequestParam("articleTitle") String articleTitle,
            @RequestParam("authorId") int authorId,
            @RequestParam("articleAuthor") String articleAuthor,
            @RequestParam("articlePrice") double articlePrice,
            @RequestParam("articleType") String articleType,
            @RequestParam("articleAbstract") String articleAbstract,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            ArticleUpload articleUpload = new ArticleUpload(articleTitle,
                    authorId,
                    articleAuthor,
                    articlePrice,
                    articleType,
                    articleAbstract,
                    file
            );
            articleService.uploadArticle(articleUpload);
            return new RtnMsg(1, "文档上传成功");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 收藏文档 URL：/article/storeArticle
     *
     * @param article_id 文档id
     * @param user_id    用户id
     * @return 返回收藏结果消息对象RtnMsg
     */
    @RequestMapping(value = "/storeArticle", method = RequestMethod.GET)
    public RtnMsg storeArticle(@RequestParam(value = "article_id") int article_id, @RequestParam(value = "user_id") int user_id) {
        try {
            articleService.storeArticle(new StoreArticle(article_id, user_id));
            return new RtnMsg(1, "文档收藏成功!");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 取消收藏 URL：/article/storeCancel
     *
     * @param article_id 文档id
     * @param user_id    用户id
     * @return 返回取消收藏结果消息对象RtnMsg
     */
    @RequestMapping(value = "/storeCancel", method = RequestMethod.GET)
    public RtnMsg storeCancel(@RequestParam(value = "article_id") int article_id, @RequestParam(value = "user_id") int user_id) {
        try {
            articleService.storeCancel(new StoreArticle(article_id, user_id));
            return new RtnMsg(1, "取消收藏成功!");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 文档评分 URL：/article/rateArticle
     *
     * @param articleRate 文档评分对象
     * @return 返回评分结果消息对象RtnMsg
     */
    @RequestMapping(value = "/articleRate", method = RequestMethod.POST)
    public RtnMsg rateArticle(@RequestBody ArticleRate articleRate) {
        try {
            articleService.rateArticle(articleRate);
            return new RtnMsg(1, "评分成功");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }
}
