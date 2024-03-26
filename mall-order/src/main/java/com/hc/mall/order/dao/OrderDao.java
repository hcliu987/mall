package com.hc.mall.order.dao;

import com.hc.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单
 * 
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-21 17:30:05
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(@Param("orderSn") String orderSn, @Param("code") Integer code, @Param("alipay") Integer alipay);
}
