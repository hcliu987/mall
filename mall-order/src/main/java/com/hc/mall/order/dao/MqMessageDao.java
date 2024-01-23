package com.hc.mall.order.dao;

import com.hc.mall.order.entity.MqMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-21 17:30:06
 */
@Mapper
public interface MqMessageDao extends BaseMapper<MqMessageEntity> {
	
}
