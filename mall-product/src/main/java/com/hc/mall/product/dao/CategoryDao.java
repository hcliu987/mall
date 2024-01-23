package com.hc.mall.product.dao;

import com.hc.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-22 17:35:07
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
