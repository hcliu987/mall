package com.hc.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-03-11 16:54:43
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

