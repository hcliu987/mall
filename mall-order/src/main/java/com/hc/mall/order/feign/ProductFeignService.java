package com.hc.mall.order.feign;

import com.hc.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-product")
public interface ProductFeignService {
    @RequestMapping("product/spuinfo/skuId/{skuId}")
    R getSpuBySkuId(@PathVariable("skuId") Long skuId);
    @RequestMapping("product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") long skuId);
}
