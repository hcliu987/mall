package com.hc.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 会员收藏的商品
 *
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-22 17:15:23
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

