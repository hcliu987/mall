package com.hc.mall.auth.server.feign;

import org.springframework.cloud.openfeign.FeignClient;
import com.hc.mall.common.utils.R;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
@FeignClient("mall-third-party")
public interface ThirdPartFeignService {

    @GetMapping(value = "/sms/sendCode")
    R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
