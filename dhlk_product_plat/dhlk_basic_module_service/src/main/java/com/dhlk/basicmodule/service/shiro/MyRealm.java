package com.dhlk.basicmodule.service.shiro;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.service.RedisService;
import com.dhlk.basicmodule.service.service.UserService;
import com.dhlk.entity.basicmodule.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RedisBasicService;
import systemconst.Const;
import utils.CheckUtils;

import java.util.Set;

@Service
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RedisService redisService;


    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        // 获取用户角色集
//        Set<String> roleSet = userServiceImpl.getRolesByLoginName(loginName);
//        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        String permissionSetJson = (String) redisService.get(Const.PERMISSIONS_CACHE_PREFIX+loginName);
        Set permissionSet = JSON.parseObject(permissionSetJson, Set.class);
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException{
        // 这里的 token是从 JWTFilter 的 executeLogin 方法传递过来的
        String token = (String) auth.getCredentials();

        String redisInfo = (String) redisService.get(Const.TOKEN_CACHE_PREFIX + token);

        if(CheckUtils.isNull(redisInfo)){
            throw new AuthenticationException("token错误");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
