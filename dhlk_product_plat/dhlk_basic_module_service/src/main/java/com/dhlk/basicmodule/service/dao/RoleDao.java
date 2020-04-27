package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Role;
import com.dhlk.entity.basicmodule.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleDao {

    Integer insert(Role role);

    Integer update(Role role);

    Integer deleteRoleByIds(List<String> ids);

    Role selectRoleById(Integer id);

    List<Role> findList(String name,String note);

    Role selectRoleByName(String name);

    Integer isRepeatName(Role role);

    List<Role> selectRoleByUserId(Integer userId);

    List<Role> selectableRoleByUserId(Integer userId);

    List<User> selectUserByRoleId(Integer roleId);
}
