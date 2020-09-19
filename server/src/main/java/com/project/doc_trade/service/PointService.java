package com.project.doc_trade.service;

import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;

import java.io.Serializable;
import java.util.List;

// 积分市场service类
public interface PointService {
    /**
     * 购买零售积分
     *
     * @param pointRetail 积分购买信息对象
     * @throws MyException 自定义异常
     */
    void purchaseRetailPoint(PointRetail pointRetail) throws MyException;

    /**
     * 购买捆绑积分
     *
     * @param pointPack 积分购买信息对象
     * @throws MyException 自定义异常
     */
    void purchasePackPoint(PointPack pointPack) throws MyException;

    /**
     * 获取积分市场的上架积分信息
     *
     * @return 上架积分信息列表对象
     * @throws MyException 自定义异常
     */
    List<SalePointInfo> getSalePoint() throws MyException;

    /**
     * 根据筛选情况搜索积分
     *
     * @param salePointScreen 上架积分筛选对象
     * @return 根据筛选规则获取的上架积分信息列表对象
     * @throws MyException 自定义异常
     */
    List<SalePointInfo> searchSalePointByScreen(SalePointScreen salePointScreen) throws MyException;

    /**
     * 获取最近7天的积分交易信息
     *
     * @return 最近7天积分交易订单信息列表对象
     * @throws MyException 自定义异常
     */
    List<PointOrder> getLastWeekOrder() throws MyException;

    /**
     * 上架积分
     *
     * @param salePoint 待上架的积分信息对象
     * @throws MyException 自定义异常
     */
    void pointOnShelf(SalePoint salePoint) throws MyException;

    /**
     * 下架积分
     *
     * @param id 待下架的积分id
     * @throws MyException 自定义异常
     */
    void pointOffShelf(Serializable id) throws MyException;
}
