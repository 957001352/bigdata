package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.Menu;
import com.dhlk.web.basicmodule.service.MenuService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

/**
 * 菜单管理
 */
@Service
public class MenuServiceFbk implements MenuService {

    @Override
    public Result save(Menu menu) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Menu selectMenuById(Integer id) {
        return null;
    }

    @Override
    public Menu selectMenuByCode(String code) {
        return null;
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result isEnable(Integer id, Integer status) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public boolean isRepeatCode(Menu menu) {
        return false;
    }

    @Override
    public Result findTreeList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTreeByUserId(Integer userId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result getMenuCheckedListByRoleId(Integer roleId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
