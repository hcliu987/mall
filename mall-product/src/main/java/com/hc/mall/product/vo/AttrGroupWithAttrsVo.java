package com.hc.mall.product.vo;

import com.hc.mall.product.entity.AttrEntity;
import com.hc.mall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo extends AttrGroupEntity {
    private List<AttrEntity> attrs;
}
