package com.hc.mall.auth.server.feign.fallback;

import com.hc.mall.auth.server.feign.MemberFeignService;
import com.hc.mall.auth.server.vo.SocialUser;
import com.hc.mall.auth.server.vo.UserLoginVo;
import com.hc.mall.auth.server.vo.UserRegisterVo;
import com.hc.mall.common.exception.BizCodeEnume;
import com.hc.mall.common.utils.R;

public class MemberFallbackService implements MemberFeignService {
    @Override
    public R register(UserRegisterVo registerVo) {
        return R.error(BizCodeEnume.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnume.READ_TIME_OUT_EXCEPTION.getMsg());
    }

    @Override
    public R login(UserLoginVo loginVo) {
        return R.error(BizCodeEnume.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnume.READ_TIME_OUT_EXCEPTION.getMsg());
    }

    @Override
    public R login(SocialUser socialUser) {
        return R.error(BizCodeEnume.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnume.READ_TIME_OUT_EXCEPTION.getMsg());
    }
}
