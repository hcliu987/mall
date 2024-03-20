package com.hc.mall.auth.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hc.mall.auth.server.feign.MemberFeignService;
import com.hc.mall.auth.server.feign.ThirdPartFeignService;
import com.hc.mall.auth.server.vo.UserLoginVo;
import com.hc.mall.auth.server.vo.UserRegisterVo;
import com.hc.mall.common.constant.AuthServerConstant;
import com.hc.mall.common.exception.BizCodeEnume;
import com.hc.mall.common.utils.R;
import com.hc.mall.common.vo.MemberResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    @Autowired
    private ThirdPartFeignService thirdPartFeignService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MemberFeignService memberFeignService;


    @PostMapping(value = "/login")
    public String login(UserLoginVo vo, RedirectAttributes attributes, HttpSession session) {
        R r = memberFeignService.login(vo);
        if (r.getCode() == 0) {
            MemberResponseVo data = r.getData("data", new TypeReference<MemberResponseVo>() {
            });
            session.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://gulimall.com";
        } else {
            Map<String,String> errors=new HashMap<>();
            errors.put("msg",r.getData("msg",new TypeReference<String>(){}));
            attributes.addFlashAttribute("errors",errors);
            return     "redirect:http://auth.gulimall.com/login.html";
        }
    }

    @GetMapping(value = "/login.html")
    public String loginPage(UserLoginVo vo, RedirectAttributes attributes, HttpSession session) {
       if (session.getAttribute(AuthServerConstant.LOGIN_USER)!=null){
           return "redirect:http://gulimall.com/";
       }else {
           return "login";
       }
    }

    @ResponseBody
    @GetMapping(value = "/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {
        //接口防刷
        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            //活动存入redis时间,用当前时间减去redis的时间，通过比较redis的时间和现在的时间
            long currentTime = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - currentTime < 6000) {
                //60s内不能再发
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        //2、验证码的再次效验 redis.存key-phone,value-code
//        String code = UUID.randomUUID().toString().substring(0, 5);
//        String redisValue = code+"_"+System.currentTimeMillis();
        int code = (int) ((Math.random() * 9 + 1) * 100000);//短信
        //redis的存值 6位数+当前系统时间 防止1分钟不到不断刷新
        String codeNum = String.valueOf(code);
        String redisStorage = codeNum + "_" + System.currentTimeMillis();
        //存入redis并指定过期时间10min，十分钟内验证码有效
        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, redisStorage, 10, TimeUnit.MINUTES);
        thirdPartFeignService.sendCode(phone, codeNum);
        return R.ok();

    }

    @GetMapping(value = "/reg.html")
    public String regPage(HttpSession session) {
        if (session.getAttribute(AuthServerConstant.LOGIN_USER) != null) {
            return "redirect:http://gulimall.com";
        }

        return "reg";

    }


    @PostMapping(value = "/register")
    public String register(@Valid UserRegisterVo vo, BindingResult result,
                           RedirectAttributes attributes) {
        Map<String, String> errors = new HashMap<>();
        if (result.hasErrors()) {
            errors = result.getFieldErrors().stream().collect(Collectors.toMap(
                    FieldError::getField, FieldError::getDefaultMessage
            ));
            //  flash是一闪而过，此数据只取一次
            attributes.addFlashAttribute("errors", errors);
            //效验出错，重定向到注册页面。不用转发是为了防止刷新时重复提交表单
            // 不用return reg是因为本来就在注册页面点击发送了这个注册请求，要重定向清空表单

            return "redirect:http://auth.gulimall.com/reg.html";
        } else {
            //2.若JSR303校验通过
            //判断验证码是否正确
            String code = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
            if (!StringUtils.isEmpty(code) && vo.getCode().equalsIgnoreCase(code.split("_")[0])) {
                //2.1.1 使得验证后的验证码失效
                stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getCode());
                //2.1.2 远程调用会员服务注册
                R r = memberFeignService.register(vo);
                if (r.getCode() == 0) {
                    //调用成功
                    return "redirect:http://auth.gulimall.com/login.html";
                } else {
                    //调用失败
                    String msg = (String) r.get("msg");
                    errors.put("msg", msg);
                    attributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            } else {
                //2.2验证码错误
                errors.put("code", "验证码错误");
                attributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.gulimall.com/reg.html";
            }
        }
    }
}
