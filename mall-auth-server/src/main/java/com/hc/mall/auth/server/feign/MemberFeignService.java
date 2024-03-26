package com.hc.mall.auth.server.feign;


import com.hc.mall.auth.server.feign.fallback.MemberFallbackService;
import com.hc.mall.auth.server.vo.SocialUser;
import com.hc.mall.auth.server.vo.UserLoginVo;
import com.hc.mall.auth.server.vo.UserRegisterVo;
import com.hc.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mall-member",fallback = MemberFallbackService.class)
public interface MemberFeignService {
    @PostMapping("member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    @RequestMapping("member/member/login")
    R login(@RequestBody UserLoginVo loginVo);

    @RequestMapping("member/member/oauth2/login")
    R login(@RequestBody SocialUser socialUser);
}
