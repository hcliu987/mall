package com.hc.mall.third.party.controller;

import com.hc.mall.third.party.component.SmsComponent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.hc.mall.common.utils.R;
import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/sms")
public class SmsSendController {
    @Resource
    private SmsComponent smsComponent;

    /**
     * 提供给别的服务进行调用
     * @param phone
     * @param code
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/sendCode")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {

        //发送验证码
//        smsComponent.sendCode(phone,code);
        System.out.println(phone+code);
        return R.ok();
    }
}
