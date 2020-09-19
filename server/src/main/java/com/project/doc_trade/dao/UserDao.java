package com.project.doc_trade.dao;

import com.project.doc_trade.entity.User;
import com.project.doc_trade.entity.UserInfo;
import com.project.doc_trade.entity.UserRemain;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserDao extends BaseDao<User> {
    /**
     * 添加用户
     *
     * @param user 待添加的用户
     * @return 添加的行数
     */
    @Insert("INSERT INTO user (`user_name`, `user_password`, `user_phone`, `public_key`, `private_key`)" +
            "VALUES (#{userName}, #{userPassword}, #{userPhone}, #{publicKey}, #{privateKey})")
    int add(User user);

    /**
     * 根据id删除用户
     *
     * @param id 待删除用户的id
     * @return 删除的行数
     */
    int delete(Serializable id);

    /**
     * 修改用户
     *
     * @param user 待修改的用户
     * @return 修改行数
     */
    int update(User user);

    /**
     * 根据id查找用户
     *
     * @param id 待查找的用户id
     * @return 找到的用户
     */
    @Select("SELECT * FROM user WHERE user_id = #{id}")
    User findById(Serializable id);

    /**
     * 根据id查找用户信息
     *
     * @param id 待查找的用户id
     * @return 找到的用户
     */
    @Select("SELECT * FROM user WHERE user_id = #{id}")
    UserInfo findInfoById(Serializable id);

    /**
     * 查找所有用户
     *
     * @return 所有用户的集合
     */
    List<User> findAll();

    /**
     * 根据用户名查找用户
     *
     * @param userName 待查找的用户名
     * @return 找到的用户
     */
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User findByUserName(String userName);

    /**
     * 根据手机号查找用户
     *
     * @param userPhone 待查找的用户手机号
     * @return 找到的用户
     */
    @Select("SELECT * FROM user WHERE user_phone = #{userPhone}")
    User findByUserPhone(String userPhone);

    /**
     * 根据用户名手机号查找用户
     *
     * @param namePhone 手机号/用户名
     * @return 找到的用户
     */
    @Select("SELECT * FROM user WHERE user_name = #{namePhone} OR user_phone = #{namePhone}")
    User findByNamePhone(String namePhone);

    /**
     * 根据id查找余额
     *
     * @param id 待查找的用户id
     * @return 找到的用户余额
     */
    @Select("SELECT * FROM user WHERE user_id = #{id}")
    UserRemain findRemainById(Serializable id);

    /**
     * 更新积分余额
     *
     * @param user 待修改的用户
     * @return 修改行数
     */
    @Update("UPDATE user SET user_point = #{userPoint} WHERE user_id = #{userId}")
    int updatePointRemain(User user);

    /**
     * 更新现金余额
     * @param user 待修改的用户
     * @return 修改行数
     */
    @Update("UPDATE user SET user_rmb = #{userRmb} WHERE user_id = #{userId}")
    int updateRmbRemain(User user);
}
