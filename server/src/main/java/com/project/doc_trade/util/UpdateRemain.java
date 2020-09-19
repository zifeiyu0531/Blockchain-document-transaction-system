package com.project.doc_trade.util;

import com.project.doc_trade.dao.HistoryDao;
import com.project.doc_trade.dao.UserDao;
import com.project.doc_trade.entity.Contract;
import com.project.doc_trade.entity.History;
import com.project.doc_trade.entity.User;
import com.project.doc_trade.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class UpdateRemain {

    @Autowired
    UserDao userDao;
    @Autowired
    HistoryDao historyDao;

    /**
     * 更新用户现金信息
     *
     * @param user 待更新用户
     * @param rmb  现金金额
     * @param type 更新类型：0：支出，1：收入
     * @throws MyException 自定义异常
     */
    public void updateRmb(User user, double rmb, int type) throws MyException {
        double remain;
        if (type == 0) {
            remain = user.getUserRmb() - rmb;
        } else if (type == 1) {
            remain = user.getUserRmb() + rmb;
        } else {
            throw new MyException("参数错误");
        }
        if (remain < 0) {
            throw new MyException("现金余额不足");
        }
        user.setUserRmb(remain);
        if (userDao.updateRmbRemain(user) != 1) {
            throw new MyException("用户记录更新失败");
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        if (historyDao.addRmb(new History(user.getUserId(), rmb, type, dateStr)) != 1) {
            throw new MyException("现金记录失败");
        }
    }

    /**
     * 更新用户积分信息
     *
     * @param user  待更新用户
     * @param point 积分金额
     * @param type  更新类型：0：支出，1：收入
     * @throws MyException 自定义异常
     */
    public void updatePoint(User user, double point, int type) throws MyException {
        double remain;
        String address = user.getPublicKey();
        BigInteger counts = new BigInteger(String.valueOf((int)point));
        try{
            if (type == 0) {
                remain = user.getUserPoint() - point;
                TransactionReceipt transactionReceipt = Contract.point_sol_tokenERC20.transferFrom(address, Contract.adminAddress, counts).send();
                System.out.println(transactionReceipt);
            } else if (type == 1) {
                remain = user.getUserPoint() + point;
                TransactionReceipt transactionReceipt = Contract.point_sol_tokenERC20.transferFrom(Contract.adminAddress, address, counts).send();
                System.out.println(transactionReceipt);
            } else {
                throw new MyException("参数错误");
            }
        }catch (Exception e){
            throw new MyException("记录交易信息失败："+e.getMessage());
        }
        if (remain < 0) {
            throw new MyException("积分余额不足");
        }
        user.setUserPoint(remain);
        if (userDao.updatePointRemain(user) != 1) {
            throw new MyException("用户记录更新失败");
        }
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = formatter.format(date);
        if (historyDao.addPoint(new History(user.getUserId(), point, type, dateStr)) != 1) {
            throw new MyException("积分记录失败");
        }
    }
}
