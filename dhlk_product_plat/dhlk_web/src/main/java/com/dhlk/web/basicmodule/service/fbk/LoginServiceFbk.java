package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.web.basicmodule.service.LoginService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import utils.ResultUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceFbk implements LoginService {

    @Override
    public Result login(String loginName, String password,String kaptcha) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result logout() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result kaptcha() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getTbToken() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
