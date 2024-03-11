package com.hc.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.product.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-03-11 16:54:42
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

