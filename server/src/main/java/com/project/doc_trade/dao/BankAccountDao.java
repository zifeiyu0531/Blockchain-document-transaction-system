package com.project.doc_trade.dao;

import com.project.doc_trade.entity.BankAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface BankAccountDao extends BaseDao<BankAccount> {
    /**
     * 添加银行账户
     *
     * @param bankAccount 待添加的银行账户
     * @return 添加的行数
     */
    @Insert("INSERT INTO bank_account(`user_id`, `bank_account`, `bank_id`, `phone_number`, `id_card_number`, `real_name`) " +
            "VALUES (#{userId}, #{bankAccount}, #{bankId}, #{phoneNumber}, #{idCardNumber}, #{realName})")
    int add(BankAccount bankAccount);

    /**
     * 根据id删除银行账户
     *
     * @param id 待删除银行账户的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改银行账户
     *
     * @param bankAccount 待修改的银行账户
     * @return 修改行数
     */
    @Update("UPDATE bank_account SET " +
            "bank_account = #{bankAccount}, bank_id = #{bankId}, phone_number = #{phoneNumber}, id_card_number = #{idCardNumber}, real_name = #{realName} " +
            "WHERE user_id = #{userId}")
    int update(BankAccount bankAccount);

    /**
     * 根据id查找银行账户
     *
     * @param id 待查找的银行账户id
     * @return 找到的银行账户
     */
    BankAccount findById(Serializable id);

    /**
     * 查找所有银行账户
     *
     * @return 所有银行账户的集合
     */
    List<BankAccount> findAll();

    /**
     * 根据用户id查找银行账户
     *
     * @param id 待查找的用户id
     * @return 找到的银行账户
     */
    @Select("SELECT * FROM bank_account WHERE user_id = #{id}")
    BankAccount findByUserId(Serializable id);
}
