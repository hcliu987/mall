package com.hc.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.member.entity.MemberEntity;
import com.hc.mall.member.vo.MemberLoginVo;
import com.hc.mall.member.vo.MemberRegisterVo;
import com.hc.mall.member.vo.SocialUser;

import java.util.Map;

/**
 * 会员
 *
 * @author hcliu
 * @email hc.liu987@gmail.com
 * @date 2024-01-22 17:15:23
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MemberEntity login(MemberLoginVo vo);

    MemberEntity login(SocialUser socialUser);

    void register(MemberRegisterVo registerVo);
}

