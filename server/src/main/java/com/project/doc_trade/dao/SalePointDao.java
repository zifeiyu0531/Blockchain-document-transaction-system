package com.project.doc_trade.dao;

import com.project.doc_trade.entity.SalePoint;
import com.project.doc_trade.entity.SalePointScreen;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SalePointDao extends BaseDao<SalePoint> {
    /**
     * 添加上架积分
     *
     * @param salePoint 待添加的上架积分
     * @return 添加的行数
     */
    @Insert("INSERT INTO sale_point (`sale_point_num`, `sale_point_user`, `sale_point_type`, `sale_point_remain`, `sale_point_price`) " +
            "VALUES (#{salePointNum}, #{salePointUser}, #{salePointType}, #{salePointNum}, #{salePointPrice})")
    int add(SalePoint salePoint);

    /**
     * 根据id删除上架积分
     *
     * @param id 待删除上架积分的id
     * @return 删除的行数
     */
    @Delete("DELETE FROM sale_point WHERE sale_point_id = #{id}")
    int delete(Serializable id);

    /**
     * 修改上架积分
     *
     * @param salePoint 待修改的上架积分
     * @return 修改行数
     */
    int update(SalePoint salePoint);

    /**
     * 根据id查找上架积分
     *
     * @param id 待查找的上架积分id
     * @return 找到的上架积分
     */
    @Select("SELECT * FROM sale_point WHERE sale_point_id = #{id}")
    SalePoint findById(Serializable id);

    /**
     * 查找所有上架积分
     *
     * @return 所有上架积分的集合
     */
    List<SalePoint> findAll();

    /**
     * 根据用户id查找所有上架积分
     *
     * @param id 待查找的用户id
     * @return 找到的上架积分的集合
     */
    @Select("SELECT * FROM sale_point WHERE sale_point_user = #{id}")
    List<SalePoint> findByUserId(Serializable id);

    /**
     * 更新积分剩余量
     *
     * @param salePoint 待更新的积分对象
     * @return 影响行数
     */
    @Update("UPDATE sale_point SET sale_point_remain = #{salePointRemain} WHERE sale_point_id = #{salePointId}")
    int updatePointRemain(SalePoint salePoint);

    /**
     * 根据筛选条件查询积分信息
     *
     * @param salePointScreen 积分筛选条件
     * @return 符合条件的积分信息列表
     */
    @Select("SELECT * FROM sale_point " +
            "WHERE sale_point_num >= #{salePointScreen.salePointNumMin} AND sale_point_num <= #{salePointScreen.salePointNumMax} AND sale_point_price >= #{salePointScreen.salePointPriceMin} AND sale_point_price <= #{salePointScreen.salePointPriceMax} AND sale_point_type = #{salePointScreen.salePointType} " +
            "LIMIT #{start}, #{salePointScreen.pageSize}")
    List<SalePoint> findByScreen(SalePointScreen salePointScreen, int start);

    /**
     * 根据筛选条件查询积分信息（不考虑类型）
     *
     * @param salePointScreen 积分筛选条件
     * @return 符合条件的积分信息列表
     */
    @Select("SELECT * FROM sale_point " +
            "WHERE sale_point_num >= #{salePointScreen.salePointNumMin} AND sale_point_num <= #{salePointScreen.salePointNumMax} AND sale_point_price >= #{salePointScreen.salePointPriceMin} AND sale_point_price <= #{salePointScreen.salePointPriceMax}" +
            "LIMIT #{start}, #{salePointScreen.pageSize}")
    List<SalePoint> findByScreenIgnoreType(SalePointScreen salePointScreen, int start);
}
