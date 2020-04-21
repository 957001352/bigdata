package com.dhlk.basicmodule.service.controller;


import com.dhlk.basicmodule.service.service.LoginService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import systemconst.Const;
import utils.ResultUtils;

import javax.servlet.http.HttpServletRequest;


@Validated
@Api(value = "LoginController", description = "登录登出")
@RestController
public class LoginController {
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(@RequestParam("loginName") String loginName,
                              @RequestParam("password") String password, @RequestParam("kaptcha") String kaptcha) {
        Result result = loginService.login(loginName,password,kaptcha);
        return result;


    }
    @ApiOperation(value = "登出")
    @GetMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping("/kaptcha")
    public Result kaptcha() {
        return loginService.kaptcha();
    }

    @ApiOperation(value = "获取tb登录token")
    @GetMapping("/getTbToken")
    public Result getTbToken() {
        return ResultUtils.success(restTemplateUtil.getTbJwtToken());
    }
}

