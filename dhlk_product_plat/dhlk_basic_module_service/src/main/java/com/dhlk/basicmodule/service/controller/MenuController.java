package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.MenuService;
import domain.Result;
import com.dhlk.entity.basicmodule.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 菜单管理
*/
@RestController
@RequestMapping(value = "/menu")
@Api(description = "菜单管理")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 保存
     * @param menu
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    @RequiresPermissions("menu:save")
    public Result save(@RequestBody Menu menu) {
        return menuService.save(menu);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    @RequiresPermissions("menu:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return menuService.delete(ids);
    }
    /**
    * 列表查询
     * @param parentId
     * @param pageNum
     * @param pageSize
    * @return
    */
    @ApiOperation("分页查询")
    @GetMapping("/findPageList")
    @RequiresPermissions("menu:view")
    public Result findPageList(@RequestParam(value = "parentId", required = false) Integer parentId,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  menuService.findPageList(parentId,pageNum, pageSize);
    }
    /**
     * 状态修改
     * @param id
     * @param status
     * @return
     */
    @ApiOperation("菜单状态修改")
    @PostMapping("/isEnable")
    @RequiresPermissions("menu:save")
    public Result isEnable(@RequestParam(value = "id", required = true)Integer id, @RequestParam(value = "status", required = true)Integer status){
        return  menuService.isEnable(id,status);
    }
    /**
     * 菜单树查询
     * @return
     */
    @ApiOperation("菜单树查询")
    @GetMapping("/findTreeList")
    @RequiresPermissions("menu:view")
    public Result findTreeList() {
        return menuService.findTreeList();
    }

    /**
     * 系统导航栏菜单查询
     * 根据用户id过滤
     * @param userId
     * @return
     */
    @ApiOperation("根据用户Id过滤菜单树")
    @GetMapping("/findTreeByUserId")
    @RequiresPermissions("menu:view")
    public Result findTreeByUserId(@RequestParam(value="userId", required = true)Integer userId){
        return menuService.findTreeList();
    }
    /**
     *获取资源列表
     * 根据角色roleId过滤
     * @param roleId
     * @return
     */
    @ApiOperation("根据角色roleId获取资源列表")
    @GetMapping("/getMenuCheckedListByRoleId")
    @RequiresPermissions("menu:view")
    public Result  getMenuCheckedListByRoleId(@RequestParam(value="roleId", required = true) Integer roleId){
        return menuService.getMenuCheckedListByRoleId(roleId);
    }
}
