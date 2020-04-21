package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.entity.basicmodule.Org;
import com.dhlk.entity.basicmodule.User;
import com.dhlk.entity.basicmodule.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface UserDao {

    Integer insert(User user);

    Integer update(User user);

    Integer delete(String[] id);

    List<User> findList(String name);

    User loginCheck(@Param("loginName") String loginName,@Param("password") String passWord);

    Integer isEnable(@Param("id") Integer id, @Param("status") Integer status);

    /*
    * 判断登录账号是否重复
     * @param loginName
    * @return
    */
    Integer isRepeatLoginName(User user);

    /*
     * 根据登陆账号查找用户
     * @param loginName
     * @return
     */
    User findUserByLoginName(String loginName);

    /*
     * 根据用户名查角色
     * @param loginName
     * @return
     */
    Set<String> findRolesByLoginName(String loginName);

    /*
     * 根据用户名查权限
     * @param loginName
     * @return
     */
    List<Menu> findMenusByLoginName(String loginName);

    /*
     * 根据机构id查询机构下的用户
     * @param orgId
     * @return
     */
    List<User> findUserByOrgId(Integer orgId);



}
