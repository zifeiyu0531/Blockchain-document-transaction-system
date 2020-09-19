package com.project.doc_trade.controller;

import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.service.PointService;
import com.project.doc_trade.util.RtnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    /**
     * 购买零售积分 URL：/point/purchaseRetailPoint
     *
     * @param pointRetail 积分购买信息对象
     * @return 返回购买结果消息对象RtnMsg
     */
    @RequestMapping(value = "/purchaseRetailPoint", method = RequestMethod.POST)
    public RtnMsg purchaseRetailPoint(@RequestBody PointRetail pointRetail) {
        try {
            pointService.purchaseRetailPoint(pointRetail);
            return new RtnMsg(1, "积分购买成功");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 购买捆绑积分 URL：/point/purchasePackPoint
     *
     * @param pointPack 积分购买信息对象
     * @return 返回购买结果消息对象RtnMsg
     */
    @RequestMapping(value = "/purchasePackPoint", method = RequestMethod.POST)
    public RtnMsg purchasePackPoint(@RequestBody PointPack pointPack) {
        try {
            pointService.purchasePackPoint(pointPack);
            return new RtnMsg(1, "积分购买成功");
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 根据筛选情况搜索积分 URL：/point/searchSalePointByScreen
     *
     * @param salePointScreen 上架积分筛选对象
     * @return 根据筛选规则获取的上架积分信息列表对象
     */
    @RequestMapping(value = "/searchSalePointByScreen", method = RequestMethod.POST)
    public List<SalePointInfo> searchSalePointByScreen(@RequestBody SalePointScreen salePointScreen) {
        try {
            return pointService.searchSalePointByScreen(salePointScreen);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 获取最近7天的积分交易信息 URL：/point/getLastWeekOrder
     *
     * @return 最近7天积分交易订单信息列表对象
     */
    @RequestMapping(value = "/getLastWeekOrder", method = RequestMethod.GET)
    public List<PointOrder> getLastWeekOrder() {
        try {
            return pointService.getLastWeekOrder();
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 上架积分 URL：/point/pointOnShelf
     *
     * @param salePoint 待上架的积分信息对象
     * @return 上架消息返回对象RtnMsg
     */
    @RequestMapping(value = "/pointOnShelf", method = RequestMethod.POST)
    public RtnMsg pointOnShelf(@RequestBody SalePoint salePoint) {
        try {
            pointService.pointOnShelf(salePoint);
            return new RtnMsg(1, "上架成功");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 下架积分 URL：/point/pointOffShelf
     *
     * @param id 待下架的积分id
     * @return 下架消息返回对象RtnMsg
     */
    @RequestMapping(value = "/pointOffShelf", method = RequestMethod.GET)
    public RtnMsg pointOffShelf(@RequestParam(value = "id") Serializable id) {
        try {
            pointService.pointOffShelf(id);
            return new RtnMsg(1, "下架成功");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }
}
