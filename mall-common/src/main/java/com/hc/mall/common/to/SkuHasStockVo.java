package com.hc.mall.common.to;

import lombok.Data;

@Data
public class SkuHasStockVo {
    private Long skuId;
    private boolean hasStock;

    public boolean getHasStock() {
        return this.hasStock;
    }
}
