package com.project.doc_trade.service.Impl;

import com.project.doc_trade.dao.*;
import com.project.doc_trade.entity.*;
import com.project.doc_trade.exception.MyException;
import com.project.doc_trade.service.UserService;
import com.project.doc_trade.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 个人中心模块service实现类
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    PurchasedArticleDao purchasedArticleDao;
    @Autowired
    StoredArticleDao storedArticleDao;
    @Autowired
    SalePointDao salePointDao;
    @Autowired
    BankAccountDao bankAccountDao;
    @Autowired
    HistoryDao historyDao;

    /**
     * 登录
     *
     * @param userSign 用户登录对象
     * @return 用户id
     * @throws MyException 自定义异常
     */
    @Override
    public UserRtn signIn(UserSign userSign) throws MyException {
        String namePhone = userSign.getUserName();
        String passWord = userSign.getUserPassword();
        try {
            User user = userDao.findByNamePhone(namePhone);
            if (user == null) {
                throw new MyException("用户名/手机号不存在");
            }
            if (!passWord.equals("9527") && !passWord.equals(user.getUserPassword())) {
                throw new MyException("密码/验证码错误");
            }
            return getUserRtn(user.getUserId());
        } catch (Exception e) {
            throw new MyException("登录失败：" + e.getMessage());
        }
    }

    /**
     * 注册
     *
     * @param userSign 用户注册对象
     * @return 用户id
     * @throws MyException 自定义异常
     */
    @Override
    public UserRtn signUp(UserSign userSign) throws MyException {
        String userName = userSign.getUserName();
        String userPhone = userSign.getUserPhone();
        String passWord = userSign.getUserPassword();
        try {
            User user = userDao.findByUserName(userName);
            if (user != null) {
                throw new MyException("用户名" + userName + "已被注册");
            }
            user = userDao.findByUserPhone(userPhone);
            if (user != null) {
                throw new MyException("手机号" + userPhone + "已被注册");
            }
            Map<String, String> map = creatAccount();
            String address = map.keySet().toArray()[0].toString();
            String privateKey = map.get(address);
            user = new User(userName, passWord, userPhone, address, privateKey);
            userDao.add(user);
            user = userDao.findByUserName(userName);
            return getUserRtn(user.getUserId());
        } catch (Exception e) {
            throw new MyException("注册失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户登录/注册返回信息
     *
     * @return 用户返回信息对象
     */
    private UserRtn getUserRtn(Serializable id){
        UserInfo userInfo = userDao.findInfoById(id);
        int userArticle = articleDao.findArticleCountByUserId(id);
        UserRtn userRtn = new UserRtn();
        userRtn.setUserArticle(userArticle);
        userRtn.setUserInfo(userInfo);
        return userRtn;
    }

    /**
     * 创建账户
     *
     * @return 地址-私钥
     */
    private Map<String, String> creatAccount() throws MyException {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
            String privateKey = privateKeyInDec.toString(16);
            WalletFile aWallet = Wallet.createLight("123", ecKeyPair);
            String address = aWallet.getAddress();
            if (address.startsWith("0x")) {
                address = address.substring(2).toLowerCase();
            } else {
                address = address.toLowerCase();
            }
            address = "0x" + address;
            Map<String, String> map = new HashMap<>();
            map.put(address, privateKey);
            return map;
        } catch (Exception e) {
            throw new MyException("创建账户失败：" + e.getMessage());
        }
    }

    /**
     * 获取短信验证码
     *
     * @param userPhone 用户手机号
     * @return 验证码
     * @throws MyException 自定义异常
     */
    public String sendMessage(String userPhone) throws MyException {
        String host = "https://cdcxdxjk.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = "dc6c384b65b44ca29a913be796f543e2";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("content", "【文鸟】你的验证码是：9527，3分钟内有效！");
        querys.put("mobile", userPhone);
        Map<String, String> body = new HashMap<>();

        try {
            HttpUtils.doPost(host, path, method, headers, querys, body);
        } catch (Exception e) {
            throw new MyException("发送短信验证码失败：" + e.getMessage());
        }
        return "9527";
    }

    /**
     * 获取用户余额
     *
     * @param id 待查询的用户id
     * @return 用户余额实体对象
     * @throws MyException 自定义异常
     */
    @Override
    public UserRemain getRemain(Serializable id) throws MyException {
        try {
            UserRemain userRemain = userDao.findRemainById(id);
            if (userRemain == null) {
                throw new MyException("用户不存在");
            } else {
                return userRemain;
            }
        } catch (Exception e) {
            throw new MyException("获取用户余额失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户已上传的文档列表
     *
     * @param id 待查询的用户id
     * @return 文档列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<ArticleInfo> getUploadArticle(Serializable id) throws MyException {
        try {
            List<ArticleInfo> articleList = articleDao.findByUserId(id);
            if (articleList == null) {
                throw new MyException("查询结果为空");
            } else {
                return articleList;
            }
        } catch (Exception e) {
            throw new MyException("查询文档失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户已购买的文档列表
     *
     * @param id 待查询的用户id
     * @return 文档列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<ArticleInfo> getPurchaseArticle(Serializable id) throws MyException {
        try {
            List<PurchasedArticle> articleList = purchasedArticleDao.findByUserId(id);
            if (articleList == null) {
                throw new MyException("查询结果为空");
            } else {
                List<ArticleInfo> purchaseArticle = new ArrayList<>();
                for (PurchasedArticle purchasedArticle : articleList) {
                    ArticleInfo article = articleDao.findInfoById(purchasedArticle.getArticleId());
                    purchaseArticle.add(article);
                }
                return purchaseArticle;
            }
        } catch (Exception e) {
            throw new MyException("查询文档失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户已收藏的文档列表
     *
     * @param id 待查询的用户id
     * @return 文档列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<ArticleInfo> getStoreArticle(Serializable id) throws MyException {
        try {
            List<StoreArticle> articleList = storedArticleDao.findByUserId(id);
            if (articleList == null) {
                throw new MyException("查询结果为空");
            } else {
                List<ArticleInfo> purchaseArticle = new ArrayList<>();
                for (StoreArticle storeArticle : articleList) {
                    ArticleInfo article = articleDao.findInfoById(storeArticle.getArticleId());
                    purchaseArticle.add(article);
                }
                return purchaseArticle;
            }
        } catch (Exception e) {
            throw new MyException("查询文档失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户正在出售的积分
     *
     * @param id 待查询的用户id
     * @return 积分列表对象
     * @throws MyException 自定义异常
     */
    @Override
    public List<SalePoint> getSalePoint(Serializable id) throws MyException {
        try {
            List<SalePoint> pointList = salePointDao.findByUserId(id);
            if (pointList == null) {
                throw new MyException("查询结果为空");
            } else {
                return pointList;
            }
        } catch (Exception e) {
            throw new MyException("查询积分失败：" + e.getMessage());
        }
    }

    /**
     * 绑定银行账户
     *
     * @param bankAccount 待绑定的银行账户
     * @throws MyException 自定义异常
     */
    @Override
    public void bindBankAccount(BankAccount bankAccount) throws MyException {
        try {
            BankAccount oldAccount = bankAccountDao.findByUserId(bankAccount.getUserId());
            if (oldAccount == null) {
                if (bankAccountDao.add(bankAccount) < 1) {
                    throw new MyException("未能正常添加");
                }
            } else {
                if (bankAccountDao.update(bankAccount) < 1) {
                    throw new MyException("未能正常更新");
                }
            }
        } catch (Exception e) {
            throw new MyException("绑定银行账户失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户绑定的银行账户信息
     *
     * @param id 待查询的用户id
     * @return 银行账户信息对象
     * @throws MyException 自定义异常
     */
    @Override
    public BankAccount getBankAccount(Serializable id) throws MyException {
        try {
            BankAccount bankAccount = bankAccountDao.findByUserId(id);
            if (bankAccount == null) {
                throw new MyException("查询结果为空");
            } else {
                return bankAccount;
            }
        } catch (Exception e) {
            throw new MyException("查询银行账户失败：" + e.getMessage());
        }
    }

    /**
     * 提现
     *
     * @param getCash 提现信息对象
     * @throws MyException 自定义异常
     */
    @Override
    public void getCash(GetCash getCash) throws MyException {
        try {
            User user = userDao.findById(getCash.getId());
            double rmbRemain = user.getUserRmb() - getCash.getCash();
            if (rmbRemain < 0) {
                throw new MyException("现金余额不足");
            } else {
                user.setUserRmb(rmbRemain);
                userDao.updateRmbRemain(user);
            }
        } catch (Exception e) {
            throw new MyException("提现失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户积分历史记录
     *
     * @param id 用户id
     * @return 积分历史记录列表
     * @throws MyException 自定义异常
     */
    @Override
    public List<History> getPointHistory(Serializable id) throws MyException {
        try {
            List<History> history = historyDao.findPointByUserId(id);
            if (history == null) {
                throw new MyException("未找到记录");
            } else {
                return history;
            }
        } catch (Exception e) {
            throw new MyException("获取积分记录失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户现金历史记录
     *
     * @param id 用户id
     * @return 现金历史记录列表
     * @throws MyException 自定义异常
     */
    @Override
    public List<History> getRmbHistory(Serializable id) throws MyException {
        try {
            List<History> history = historyDao.findRmbByUserId(id);
            if (history == null) {
                throw new MyException("未找到记录");
            } else {
                return history;
            }
        } catch (Exception e) {
            throw new MyException("获取现金记录失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户收益
     *
     * @param id 用户id
     * @return 用户收益
     * @throws MyException 自定义异常
     */
    @Override
    public double getIncome(Serializable id) throws MyException {
        try {
            List<History> historyList = historyDao.findRmbByUserId(id);
            if (historyList == null) {
                throw new MyException("历史记录为空");
            }
            double income = 0;
            for (History history : historyList) {
                if (history.getType() == 1) {
                    income += history.getNum();
                }
            }
            return income;
        } catch (Exception e) {
            throw new MyException("查询收益失败：" + e.getMessage());
        }
    }
}
