package com.hc.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.to.mq.SeckillOrderTo;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.order.entity.OrderEntity;
import com.hc.mall.order.vo.OrderConfirmVo;
import com.hc.mall.order.vo.OrderSubmitVo;
import com.hc.mall.order.vo.PayAsyncVo;
import com.hc.mall.order.vo.SubmitOrderResponseVo;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-21 17:30:05
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    void closeOrder(OrderEntity orderEntity);

    void handlerPayResult(PayAsyncVo payAsyncVo);

    void createSeckillOrder(SeckillOrderTo orderTo);
}

