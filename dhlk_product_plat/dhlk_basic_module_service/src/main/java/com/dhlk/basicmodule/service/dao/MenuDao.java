package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.entity.basicmodule.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface MenuDao {

    Integer insert(Menu menu);

    Integer update(Menu menu);

    Integer delete(Integer id);

    Menu selectMenuById(Integer id);

    Menu selectMenuByCode(String code);

    Integer deleteMenuByIds(List<String> ids);

    List<Menu> findList(Integer parentId, String code, String name, String url, Integer status);

    Integer isEnable(@Param("id") Integer id, @Param("status") Integer status);

    /*
     * 判断菜单code是否重复
     */
    Integer isRepeatCode(Menu menu);


    List<Menu> findTreeByUserId(Integer userId);

    List<Menu> getMenuCheckedList(Integer roleId);

}
