package com.hc.mall.order.feign;

import com.hc.mall.common.to.SkuHasStockVo;
import com.hc.mall.common.utils.R;
import com.hc.mall.order.vo.FareVo;
import com.hc.mall.order.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mall-ware")
public interface WareFeignService {
    @RequestMapping("ware/waresku/getSkuHasStocks")
    List<SkuHasStockVo> getSkuHasStocks(@RequestBody List<Long> ids);
    @RequestMapping("ware/wareinfo/fare/{addrId}")
    FareVo getFare(@PathVariable("addrId")Long addrId);
    @RequestMapping("ware/waresku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo items);
}
