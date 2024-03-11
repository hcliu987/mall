package com.hc.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-03-11 16:54:43
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

