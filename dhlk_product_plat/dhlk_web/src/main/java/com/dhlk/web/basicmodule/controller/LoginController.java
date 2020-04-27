package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.LoginService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* 登录
*/
@RestController
@Api(description = "登录登出")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @ApiOperation(value = "登录")
    @PostMapping(value = "/login",produces = "application/json")
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("password") String password ,
                        @RequestParam("kaptcha") String kaptcha) {
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
        return loginService.getTbToken();
    }

}
