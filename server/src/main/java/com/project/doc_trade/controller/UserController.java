package com.project.doc_trade.controller;

import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.service.UserService;
import com.project.doc_trade.util.RtnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户登录 URL：/user/signIn
     *
     * @param userSign 用户登录对象
     * @return 返回自定义消息类型RtnMsg(status_code, message)
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public UserRtn signIn(@RequestBody UserSign userSign) {
        try {
            return userService.signIn(userSign);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new UserRtn();
        }
    }

    /**
     * 用户注册 URL：/user/signUp
     *
     * @param userSign 用户注册对象
     * @return 返回自定义消息类型RtnMsg(status_code, message)
     */
    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public UserRtn signUp(@RequestBody UserSign userSign) {
        try {
            return userService.signUp(userSign);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new UserRtn();
        }
    }

    /**
     * 获取短信验证码 URL：/user/sendMessage
     *
     * @param userPhone 用户手机号
     * @return 验证码
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public String sendMessage(@RequestParam(value = "userPhone") String userPhone) {
        try {
            return userService.sendMessage(userPhone);
        } catch (MyException e) {
            return "发送失败";
        }
    }

    /**
     * 查询余额 URL：/user/getRemain
     *
     * @param id 待查询的用户id
     * @return 返回用户余额对象UserRemain()
     */
    @RequestMapping(value = "/getRemain", method = RequestMethod.GET)
    public UserRemain getRemain(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getRemain(id);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new UserRemain(-1, -1);
        }
    }

    /**
     * 查询已上传的文档 URL：/user/getUploadArticle
     *
     * @param id 待查询的用户id
     * @return 返回文档对象列表List<ArticleInfo>
     */
    @RequestMapping(value = "/getUploadArticle", method = RequestMethod.GET)
    public List<ArticleInfo> getUploadArticle(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getUploadArticle(id);
        } catch (MyException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 查询已购买的文档 URL：/user/getPurchaseArticle
     *
     * @param id 待查询的用户id
     * @return 返回文档对象列表List<ArticleInfo>
     */
    @RequestMapping(value = "/getPurchaseArticle", method = RequestMethod.GET)
    public List<ArticleInfo> getPurchaseArticle(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getPurchaseArticle(id);
        } catch (MyException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 查询已收藏的文档 URL：/user/getStoreArticle
     *
     * @param id 待查询的用户id
     * @return 返回文档对象列表List<ArticleInfo>
     */
    @RequestMapping(value = "/getStoreArticle", method = RequestMethod.GET)
    public List<ArticleInfo> getStoreArticle(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getStoreArticle(id);
        } catch (MyException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 查询正在出售的积分 URL：/user/getSalePoint
     *
     * @param id 待查询的用户id
     * @return 返回积分对象列表List<SalePoint>
     */
    @RequestMapping(value = "/getSalePoint", method = RequestMethod.GET)
    public List<SalePoint> getSalePoint(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getSalePoint(id);
        } catch (MyException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 绑定银行账户 URL：/user/bindBankAccount
     *
     * @param bankAccount 待绑定的银行账户信息
     * @return 返回自定义消息类型RtnMsg(status_code, message)
     */
    @RequestMapping(value = "/bindBankAccount", method = RequestMethod.POST)
    public RtnMsg bindBankAccount(@RequestBody BankAccount bankAccount) {
        try {
            userService.bindBankAccount(bankAccount);
            return new RtnMsg(1, "绑定成功！");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 根据id获取银行账户 URL：/user/getBankAccount
     *
     * @param id 用户id
     * @return 返回银行账户信息BankAccount
     */
    @RequestMapping(value = "/getBankAccount", method = RequestMethod.GET)
    public BankAccount getBankAccount(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getBankAccount(id);
        } catch (MyException e) {
            return new BankAccount();
        }
    }

    /**
     * 提现 URL：/user/getCash
     *
     * @param getCash 提现对象
     * @return 返回自定义消息类型RtnMsg(status_code, message)
     */
    @RequestMapping(value = "/getCash", method = RequestMethod.POST)
    public RtnMsg getCash(@RequestBody GetCash getCash) {
        try {
            userService.getCash(getCash);
            return new RtnMsg(1, "提现成功");
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }

    /**
     * 获取用户积分历史记录 URL：/user/getPointHistory
     *
     * @param id 用户id
     * @return 积分历史记录列表
     */
    @RequestMapping(value = "/getPointHistory", method = RequestMethod.GET)
    public List<History> getPointHistory(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getPointHistory(id);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 获取用户现金历史记录 URL：/user/getRmbHistory
     *
     * @param id 用户id
     * @return 现金历史记录列表
     */
    @RequestMapping(value = "/getRmbHistory", method = RequestMethod.GET)
    public List<History> getRmbHistory(@RequestParam(value = "id") Serializable id) {
        try {
            return userService.getRmbHistory(id);
        } catch (MyException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * 获取用户收益 URL：/user/getIncome
     *
     * @param id 用户id
     * @return 用户收益信息RtnMsg
     */
    @RequestMapping(value = "/getIncome", method = RequestMethod.GET)
    public RtnMsg getIncome(@RequestParam(value = "id") Serializable id) {
        try {
            return new RtnMsg(1, String.valueOf(userService.getIncome(id)));
        } catch (MyException e) {
            return new RtnMsg(0, e.getMessage());
        }
    }
}
