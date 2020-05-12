package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhlk.basicmodule.service.dao.OrgDao;
import com.dhlk.basicmodule.service.dao.UserDao;
import com.dhlk.basicmodule.service.dao.UserRoleDao;
import com.dhlk.basicmodule.service.service.UserService;
import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.entity.basicmodule.UserRole;
import com.dhlk.domain.Result;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.enums.ResultEnum;
import com.dhlk.service.RedisService;
import com.dhlk.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dhlk.systemconst.Const;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RedisService redisService;
    @Override
    public Result save(User user) {
        Integer flag = 0;

        if(userDao.isRepeatLoginName(user) > 0) {
            return ResultUtils.error(ResultEnum.HAVE_SAMENAME.getStateInfo());
        }
        //获得用户的角色，用逗号分割得到多个角色id,
        String[] roleIds = null;
        if(!CheckUtils.isNull(user.getRoleIds())){
            roleIds = user.getRoleIds().split(",");
        }
        if(!CheckUtils.checkId(user.getId())){
            //对密码进行加密存入数据库中
            user.setPassword(EncryUtils.md5(user.getPassword(),user.getLoginName()));
            flag = userDao.insert(user);
            //将用户的角色存入数据库中
            flag = getListByArray(user, roleIds);
        }else{
            //如果修改了密码就对密码进行加密修改，没有就不加密
            if(user.getPassword()!=null){
                user.setPassword(EncryUtils.md5(user.getPassword(),user.getLoginName()));
            }
            //先将用户的角色全部删除
            flag = userRoleDao.deleteByUserId(user.getId());
            //将用户角色存入数据库中
            flag = getListByArray(user, roleIds);
            flag = userDao.update(user);
            //检查缓存中是否有该用户权限信息，如果有就更新
            updatePermissions(user.getLoginName());
        }
        if(flag>0){
            String headChar = PinyinUtils.getHeadChar(PinyinUtils.toHanyuPinyin(user.getName()));
            user.setGroup(headChar);
            return ResultUtils.success(user);
        }else{
            return ResultUtils.failure();
        }
    }

    @Override
    public Result delete(String ids) {
        if(CheckUtils.isNull(ids)){
            return ResultUtils.error(ResultEnum.PARAM_ERR.getStateInfo());
        }
        String[] split = ids.split(",");
        if(userDao.delete(split) > 0){
            userRoleDao.deleteByUserIds(split);
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findList(String name) {
        List<User> users = userDao.findList(name);
        for (User user:users) {
            String headChar = PinyinUtils.getHeadChar(PinyinUtils.toHanyuPinyin(user.getName()));
            user.setGroup(headChar);
        }
        Map<String, List<User>> groupBy = users.stream().collect(Collectors.groupingBy(User::getGroup));
        return ResultUtils.success(groupBy);
    }

    @Override
    public Result isEnable(Integer id, Integer status) {
        if(!CheckUtils.checkId(id)){
            return ResultUtils.error(ResultEnum.PARAM_ERR.getStateInfo());
        }
        if(!CheckUtils.checkStatus(status)){
            return ResultUtils.error(ResultEnum.PARAM_ERR.getStateInfo());
        }
        if(status == Const.STATUS_OK){
            status = Const.STATUS_BAN;
        }else if(status == Const.STATUS_BAN){
            status = Const.STATUS_OK;
        }
        if(userDao.isEnable(id,status) > 0){
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result updatePassword(Integer id, String password) {
        if(!CheckUtils.checkId(id)){
            return ResultUtils.error(ResultEnum.PARAM_ERR.getStateInfo());
        }
        User user = new User();
        user.setId(id);
        user.setPassword(EncryUtils.md5(password,user.getLoginName()));
        Integer result = userDao.update(user);
        return result < 0?ResultUtils.failure():ResultUtils.success();
    }

    @Override
    public User getUserByLoginName(String loginName) {
        User user = userDao.findUserByLoginName(loginName);
        return user;
    }

    @Override
    public Set<String> getRolesByLoginName(String loginName) {

        return userDao.findRolesByLoginName(loginName);
    }

    @Override
    public Map<String, Set> getPermissionsByLoginName(String loginName) {
        List<Menu> menus = userDao.findMenusByLoginName(loginName);
        Set<String> perms = new HashSet<>();
        Set<String> codes = new HashSet<>();
        if(!CheckUtils.isNull(menus)){
            menus.stream().forEach(menu -> {
                perms.add(menu.getPerms());
                codes.add(menu.getCode());
            });
        }
        Map<String,Set> map = new HashMap();
        map.put("perms",perms);
        map.put("codes",codes);
        return map;
    }


    /**
    * 查询用户所在机构
    * @author      gchen
    * @param userId
    * @date        2020/4/2 15:10
    */
    @Override
    public Result findOrg(Integer userId) {
        if(!CheckUtils.checkId(userId)){
            return ResultUtils.error("id输入错误");
        }
        return ResultUtils.success(orgDao.findOrgByUserId(userId));
    }

    /**
    * 级联修改用户角色和用户信息
    * @author      gchen
    * @return
    * @date        2020/3/20 15:54
    */
    private Integer getListByArray(User user,String[] roleIds){

        if(!CheckUtils.isNull(roleIds) && roleIds != null){
            int flag = -1;
            //将用户的角色存入数据库中
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId:roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(Integer.parseInt(roleId));
                userRoles.add(userRole);
            }
            flag = userRoleDao.saveUserRoles(userRoles);
            return flag;
        }
        return 1;
    }



    //更新redis权限缓存
    public void updatePermissions(String loginName){
        if(redisService.hasKey(Const.PERMISSIONS_CACHE_PREFIX+loginName)){
            Map<String, Set> permissions = getPermissionsByLoginName(loginName);
            redisService.set(Const.PERMISSIONS_CACHE_PREFIX+loginName, JSON.toJSONString(permissions.get("perms")),Const.TOKEN_LOSE_TIME);
        }
    }
}
