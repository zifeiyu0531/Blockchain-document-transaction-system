package com.project.doc_trade.service.Impl;

import com.project.doc_trade.dao.HistoryDao;
import com.project.doc_trade.dao.PointOrderDao;
import com.project.doc_trade.dao.SalePointDao;
import com.project.doc_trade.dao.UserDao;
import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.service.PointService;
import com.project.doc_trade.util.UpdateRemain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 积分市场模块service实现类
@Service
public class PointServiceImpl implements PointService {

    @Autowired
    SalePointDao salePointDao;
    @Autowired
    UserDao userDao;
    @Autowired
    HistoryDao historyDao;
    @Autowired
    PointOrderDao pointOrderDao;
    @Autowired
    UpdateRemain updateRemain;

    /**
     * 购买零售积分
     *
     * @param pointRetail 积分购买信息对象
     * @throws MyException 自定义异常
     */
    @Override
    public void purchaseRetailPoint(PointRetail pointRetail) throws MyException {
        try {
            SalePoint salePoint = salePointDao.findById(pointRetail.getSalePointId());
            User buyUser = userDao.findById(pointRetail.getBuyUserId());
            if (buyUser == null) throw new MyException("用户查询失败");
            if (salePoint.getSalePointRemain() < pointRetail.getOrderPoint()) {
                throw new MyException("购买量超出上限");
            }
            double rmb = salePoint.getSalePointPrice() * pointRetail.getOrderPoint();
            updateRemain.updateRmb(buyUser, rmb, 0);
            updateRemain.updatePoint(buyUser, pointRetail.getOrderPoint(), 1);
            User saleUser = userDao.findById(salePoint.getSalePointUser());
            if (saleUser == null) throw new MyException("用户查询失败");
            updateRemain.updateRmb(saleUser, rmb, 1);
            double point = salePoint.getSalePointRemain() - pointRetail.getOrderPoint();
            if (point == 0) {
                if (salePointDao.delete(salePoint.getSalePointId()) != 1) {
                    throw new MyException("删除商品失败");
                }
            } else {
                salePoint.setSalePointRemain(point);
                if (salePointDao.updatePointRemain(salePoint) != 1) {
                    throw new MyException("更新积分剩余量失败");
                }
            }
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);
            if (pointOrderDao.add(new PointOrder(pointRetail.getOrderPoint(), rmb, buyUser.getUserId(), saleUser.getUserId(), dateStr)) != 1) {
                throw new MyException("订单创建失败");
            }
        } catch (Exception e) {
            throw new MyException("购买积分失败：" + e.getMessage());
        }
    }

    /**
     * 购买捆绑积分
     *
     * @param pointPack 积分购买信息对象
     * @throws MyException 自定义异常
     */
    @Override
    public void purchasePackPoint(PointPack pointPack) throws MyException {
        try {
            SalePoint salePoint = salePointDao.findById(pointPack.getSalePointId());
            User buyUser = userDao.findById(pointPack.getBuyUserId());
            if (buyUser == null) throw new MyException("用户查询失败");
            double rmb = salePoint.getSalePointPrice() * salePoint.getSalePointNum();
            updateRemain.updateRmb(buyUser, rmb, 0);
            updateRemain.updatePoint(buyUser, salePoint.getSalePointNum(), 1);
            User saleUser = userDao.findById(salePoint.getSalePointUser());
            if (saleUser == null) throw new MyException("用户查询失败");
            updateRemain.updateRmb(saleUser, rmb, 1);
            if (salePointDao.delete(salePoint.getSalePointId()) != 1) {
                throw new MyException("删除商品失败");
            }
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);
            if (pointOrderDao.add(new PointOrder(salePoint.getSalePointNum(), rmb, buyUser.getUserId(), saleUser.getUserId(), dateStr)) != 1) {
                throw new MyException("订单创建失败");
            }
        } catch (Exception e) {
            throw new MyException("购买积分失败：" + e.getMessage());
        }
    }

    /**
     * 获取积分市场的上架积分信息
     *
     * @return 上架积分信息列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<SalePointInfo> getSalePoint() throws MyException {
        return new ArrayList<SalePointInfo>();
    }

    /**
     * 根据筛选情况搜索积分
     *
     * @param salePointScreen 上架积分筛选对象
     * @return 根据筛选规则获取的上架积分信息列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<SalePointInfo> searchSalePointByScreen(SalePointScreen salePointScreen) throws MyException {
        try {
            List<SalePoint> salePointList;
            int start = (salePointScreen.getPageNum() - 1) * salePointScreen.getPageSize();
            if (salePointScreen.getSalePointType() == -1) {
                salePointList = salePointDao.findByScreenIgnoreType(salePointScreen, start);
                if (salePointList == null) {
                    throw new MyException("积分查询为空");
                }
            } else {
                salePointList = salePointDao.findByScreen(salePointScreen, start);
                if (salePointList == null) {
                    throw new MyException("积分查询为空");
                }
            }
            List<SalePointInfo> salePointInfoList = new ArrayList<>();
            for (SalePoint salePoint : salePointList) {
                User user = userDao.findById(salePoint.getSalePointUser());
                if (user == null) {
                    throw new MyException("用户查询为空");
                }
                SalePointInfo salePointInfo = new SalePointInfo();
                salePointInfo.setSalePointUserName(user.getUserName());
                salePointInfo.setSalePoint(salePoint);
                salePointInfoList.add(salePointInfo);
            }
            return salePointInfoList;
        } catch (Exception e) {
            throw new MyException("查询积分市场失败：" + e.getMessage());
        }
    }

    /**
     * 获取最近7天的积分交易信息
     *
     * @return 最近7天积分交易订单信息列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<PointOrder> getLastWeekOrder() throws MyException {
        try {
            return pointOrderDao.findLast10();
        } catch (Exception e) {
            throw new MyException("获取交易信息失败：" + e.getMessage());
        }
    }

    /**
     * 上架积分
     *
     * @param salePoint 待上架的积分信息对象
     * @throws MyException 自定义异常
     */
    @Override
    public void pointOnShelf(SalePoint salePoint) throws MyException {
        double salePointNum = salePoint.getSalePointNum();
        try {
            updateRemain.updatePoint(userDao.findById(salePoint.getSalePointUser()), salePointNum, 0);
            salePointDao.add(salePoint);
        } catch (Exception e) {
            throw new MyException("上架失败：" + e.getMessage());
        }
    }

    /**
     * 下架积分
     *
     * @param id 待下架的积分id
     * @throws MyException 自定义异常
     */
    @Override
    public void pointOffShelf(Serializable id) throws MyException {
        try {
            SalePoint salePoint = salePointDao.findById(id);
            if (salePoint == null) {
                throw new MyException("积分商品查询失败");
            }
            int userId = salePoint.getSalePointUser();
            salePointDao.delete(id);
            updateRemain.updatePoint(userDao.findById(userId), salePoint.getSalePointRemain(), 1);
        } catch (Exception e) {
            throw new MyException("下架失败：" + e.getMessage());
        }
    }
}
