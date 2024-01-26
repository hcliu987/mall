package com.hc.mall.coupon.dao;

import com.hc.mall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-01-26 18:58:43
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
