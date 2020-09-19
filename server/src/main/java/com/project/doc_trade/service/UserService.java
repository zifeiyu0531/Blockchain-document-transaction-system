package com.project.doc_trade.service;

import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

// 个人中心模块service类
public interface UserService {
    /**
     * 登录
     *
     * @param userSign 用户登录对象
     * @return 用户id
     * @throws MyException 自定义异常
     */
    UserRtn signIn(UserSign userSign) throws MyException;

    /**
     * 注册
     *
     * @param userSign 用户注册对象
     * @return 用户id
     * @throws MyException 自定义异常
     */
    UserRtn signUp(UserSign userSign) throws MyException;

    /**
     * 获取短信验证码
     *
     * @param userPhone 用户手机号
     * @return 验证码
     * @throws MyException 自定义异常
     */
    String sendMessage(String userPhone) throws MyException;

    /**
     * 获取用户余额
     *
     * @param id 待查询的用户id
     * @return 用户余额实体对象
     * @throws MyException 自定义异常
     */
    UserRemain getRemain(Serializable id) throws MyException;

    /**
     * 获取用户已上传的文档列表
     *
     * @param id 待查询的用户id
     * @return 文档列表对象
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> getUploadArticle(Serializable id) throws MyException;

    /**
     * 获取用户已购买的文档列表
     *
     * @param id 待查询的用户id
     * @return 文档列表对象
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> getPurchaseArticle(Serializable id) throws MyException;

    /**
     * 获取用户已收藏的文档列表
     *
     * @param id 待查询的用户id
     * @return 文档列表对象
     * @throws MyException 自定义异常
     */
    List<ArticleInfo> getStoreArticle(Serializable id) throws MyException;

    /**
     * 获取用户正在出售的积分
     *
     * @param id 待查询的用户id
     * @return 积分列表对象
     * @throws MyException 自定义异常
     */
    List<SalePoint> getSalePoint(Serializable id) throws MyException;

    /**
     * 绑定银行账户
     *
     * @param bankAccount 待绑定的银行账户信息
     * @throws MyException 自定义异常
     */
    void bindBankAccount(BankAccount bankAccount) throws MyException;

    /**
     * 获取用户绑定的银行账户信息
     *
     * @param id 待查询的用户id
     * @return 银行账户信息对象
     * @throws MyException 自定义异常
     */
    BankAccount getBankAccount(Serializable id) throws MyException;

    /**
     * 提现
     *
     * @param getCash 提现信息对象
     * @throws MyException 自定义异常
     */
    void getCash(GetCash getCash) throws MyException;

    /**
     * 获取用户积分历史记录
     *
     * @param id 用户id
     * @return 积分历史记录列表
     * @throws MyException 自定义异常
     */
    List<History> getPointHistory(Serializable id) throws MyException;

    /**
     * 获取用户现金历史记录
     *
     * @param id 用户id
     * @return 现金历史记录列表
     * @throws MyException 自定义异常
     */
    List<History> getRmbHistory(Serializable id) throws MyException;

    /**
     * 获取用户收益
     *
     * @param id 用户id
     * @return 用户收益
     * @throws MyException 自定义异常
     */
    double getIncome(Serializable id) throws MyException;
}