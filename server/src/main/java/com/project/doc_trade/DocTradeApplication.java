package com.project.doc_trade;

import com.project.doc_trade.entity.Contract;
import com.project.doc_trade.filter.EncodingFilter;
import com.project.doc_trade.util.Point_sol_TokenERC20;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

@MapperScan("com.project.doc_trade.dao")
@SpringBootApplication
public class DocTradeApplication {

    @Bean
    public FilterRegistrationBean setFilter(){
        FilterRegistrationBean<EncodingFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new EncodingFilter());
        filterBean.setName("FilterController");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }

    public static void main(String[] args) {
        contractDeploy();
        SpringApplication.run(DocTradeApplication.class, args);
    }

    /**
     * 部署智能合约
     */
    private static void contractDeploy(){
        try{
            Web3j web3j = Web3j.build(new HttpService());
            Credentials credentials = WalletUtils.loadCredentials(
                    "root",
                    "../block/chain/keystore/UTC--2020-08-16T11-14-02.415818100Z--afd85267b8f8e07e74001fa6307bfda6375278f1");

            // 部署合约
            Point_sol_TokenERC20 point_sol_tokenERC20
                    = Point_sol_TokenERC20.deploy(web3j, credentials, BigInteger.valueOf(200000), BigInteger.valueOf(20000000), BigInteger.valueOf(100000000), "Point", "PT").send();

            // 合约地址
            String address = point_sol_tokenERC20.getContractAddress();

            // 加载合约
            Contract.point_sol_tokenERC20 = Point_sol_TokenERC20.load(
                    address,
                    web3j,
                    credentials,
                    BigInteger.valueOf(200000),
                    BigInteger.valueOf(20000000));

            System.out.println(Contract.point_sol_tokenERC20.isValid());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
