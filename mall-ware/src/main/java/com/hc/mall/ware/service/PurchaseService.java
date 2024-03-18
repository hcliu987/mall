package com.hc.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.ware.entity.PurchaseEntity;
import com.hc.mall.ware.vo.MergerVo;
import com.hc.mall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-21 17:34:21
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive(Map<String, Object> params);

    void done(PurchaseDoneVo doneVo);

    void received(List<Long> ids);

    void mergePurchase(MergerVo mergeVo);
}

