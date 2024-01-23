package com.hc.mall.member.dao;

import com.hc.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-22 17:15:23
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
