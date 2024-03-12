package com.hc.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.product.entity.AttrAttrgroupRelationEntity;
import com.hc.mall.product.entity.AttrEntity;
import com.hc.mall.product.vo.AttrRespVo;
import com.hc.mall.product.vo.AttrVo;

import java.util.List;
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

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrAttrgroupRelationEntity[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    void saveRelationBatch(List<AttrAttrgroupRelationEntity> relationEntities);
}

