package com.hc.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.to.SkuReductionTo;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-01-26 18:58:43
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTo skuReductionTo);
}

