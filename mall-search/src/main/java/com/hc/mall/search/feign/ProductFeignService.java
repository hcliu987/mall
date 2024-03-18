package com.hc.mall.search.feign;

import com.hc.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-product")
public interface ProductFeignService {
    @RequestMapping("product/attr/info/{attrID}")
    R info(@PathVariable("attrId") Long attrId);
}
