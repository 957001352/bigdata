package com.dhlk.basicmodule.service.util;

import com.alibaba.fastjson.JSON;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.service.RedisService;
import com.dhlk.systemconst.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/24
 */
@Component
public class AuthUserUtil {
    @Autowired
    private RedisService redisService;
    @Autowired
    private HttpServletRequest request;

    public User currentUser() {
        String token = request.getHeader(Const.TOKEN_HEADER);
        String jsonUser = (String) redisService.get(Const.TOKEN_CACHE_PREFIX + token);
        return JSON.parseObject(jsonUser, User.class);
    }

    public Integer findFactoryId() {
        User user=this.currentUser();
        if(user!=null){
            return user.getFactory().getId();
        }
        return null;
    }
}