package com.hc.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.mall.product.vo.AttrGroupWithAttrsVo;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.common.utils.Query;

import com.hc.mall.product.dao.AttrGroupDao;
import com.hc.mall.product.entity.AttrGroupEntity;
import com.hc.mall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper();
        if (catelogId != 0) {
            wrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
        }
        System.out.println(key);
        if (StringUtil.isNotBlank(key)) {
            // wrapper.eq(AttrGroupEntity::getCatelogId,key).or().like(AttrGroupEntity::getAttrGroupName);
            wrapper.and(
                    obj -> obj.eq(AttrGroupEntity::getCatelogId, key)
                            .or()
                            .like(AttrGroupEntity::getAttrGroupName, key)
            );
        }

        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {
        //查询此分类下所有分组
        List<AttrGroupEntity> attrGroupEntities = this.list(new LambdaQueryWrapper<AttrGroupEntity>().eq(AttrGroupEntity::getCatelogId, catelogId));
        //每个分组查询规格属性
        return attrGroupEntities.stream().map(item -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            Long groupId = item.getAttrGroupId();
            vo.setAttrGroupId(groupId);
            return vo;
        }).collect(Collectors.toList());
    }

}