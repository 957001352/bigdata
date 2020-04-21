package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.LoginServiceFbk;
import domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户登录
 * @Author gchen
 * @Date 2020/3/11
 */
@FeignClient(value = "basicmodule-service/", fallback = LoginServiceFbk.class)
public interface LoginService {
    @PostMapping(value = "/login")
    Result login(@RequestParam("loginName") String loginName,
                @RequestParam("password") String password,@RequestParam("kaptcha") String kaptcha);

    @GetMapping("/logout")
    Result logout();

    @GetMapping("/kaptcha")
    public Result kaptcha() ;
}
