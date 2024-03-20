package com.hc.mall.product.web;

import com.hc.mall.product.service.SkuInfoService;
import com.hc.mall.product.vo.SkuItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class ItemController {

    @Resource
    SkuInfoService skuInfoService;

    @GetMapping("/{skuID}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        System.out.println("准备查询:" + skuId + "的详情");
        SkuItemVo vos = skuInfoService.item(skuId);
        model.addAttribute("item",vos);
        return "item";
    }
}
