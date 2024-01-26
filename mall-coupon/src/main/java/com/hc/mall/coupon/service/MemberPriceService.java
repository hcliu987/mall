package com.hc.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.common.utils.PageUtils;
import com.hc.mall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-01-26 18:58:43
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

