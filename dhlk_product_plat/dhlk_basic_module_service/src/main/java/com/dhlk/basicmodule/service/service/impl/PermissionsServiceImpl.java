package com.dhlk.basicmodule.service.service.impl;


import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.dao.PermissionsDao;
import com.dhlk.basicmodule.service.dao.RoleDao;
import com.dhlk.basicmodule.service.dao.UserDao;
import com.dhlk.basicmodule.service.service.PermissionsService;
import com.dhlk.basicmodule.service.service.RedisService;
import com.dhlk.basicmodule.service.service.UserService;
import com.dhlk.entity.basicmodule.User;
import domain.Result;
import com.dhlk.entity.basicmodule.Permissions;
import enums.ResultEnum;
import enums.SystemEnums;
import exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import systemconst.Const;
import utils.CheckUtils;
import utils.ResultUtils;

import java.util.*;

/*
 * Content: 权限管理
 * Author:jlv
 * Date:2020/3/26
 */
@Transactional
@Service
public class PermissionsServiceImpl implements PermissionsService {
    @Autowired
    private PermissionsDao permissionsDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Override
    public Result insert(Integer roleId, String menuIds) {
        if (CheckUtils.isNull(roleId) || CheckUtils.isNull(menuIds)) {
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        //默认超级管理员的角色id为1
        if (roleId == 1) {
            return ResultUtils.error("超级管理员权限禁止修改!");
        } else {
            //先根据角色id删除对应的权限
            List<String> list = Arrays.asList(roleId.toString());
            Integer res = permissionsDao.deleteByRoleIds(list);
            //保存角色拥有的权限
            if (res >= 0) {
                List<Permissions> permissionsList = new ArrayList<>();
                Arrays.asList(menuIds.split(",")).stream().forEach(s -> permissionsList.add(new Permissions(roleId, Integer.parseInt(s))));
                Integer res1 = permissionsDao.insert(permissionsList);
                if (res1 > 0) {
                    List<User> users = roleDao.selectUserByRoleId(roleId);
                    for (User user: users) {
                        //检查缓存中是否有该用户权限信息，如果有就更新
                        if(user!=null && redisService.hasKey(Const.PERMISSIONS_CACHE_PREFIX+user.getLoginName())){
                            Map<String, Set> permissions = userService.getPermissionsByLoginName(user.getLoginName());
                            redisService.set(Const.PERMISSIONS_CACHE_PREFIX+user.getLoginName(), JSON.toJSONString(permissions.get("perms")),Const.TOKEN_LOSE_TIME);
                        }
                    }
                    return ResultUtils.success();
                }
            }
        }
        return ResultUtils.failure();
    }

    @Override
    public Result deleteByRoleIds(String roleIds) {
        if(CheckUtils.isNull(roleIds)){
            return ResultUtils.error("参数不能为空");
        }
        List<String> list= Arrays.asList(roleIds.split(","));
        Integer res = permissionsDao.deleteByRoleIds(list);
        return res>0?ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public boolean selectListByRoleIds(String roleIds)  {
        if(CheckUtils.isNull(roleIds)){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        List<String> list= Arrays.asList(roleIds.split(","));
        return permissionsDao.selectListByRoleIds(list).size()>0?true:false;
    }
}
