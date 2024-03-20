package com.hc.mall.seckill.service;

import com.hc.mall.seckill.to.SeckillSkuRedisTo;

public interface SecKillService {
    SeckillSkuRedisTo getSeckillSkuInfo(Long skuId);
}
