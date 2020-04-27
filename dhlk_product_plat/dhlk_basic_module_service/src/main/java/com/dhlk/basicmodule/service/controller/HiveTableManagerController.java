package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.HiveTableManagerService;
import com.dhlk.domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* hive数据表管理
*/
@RestController
@RequestMapping(value = "/hiveTableManager")
public class HiveTableManagerController {
    @Autowired
    private HiveTableManagerService hiveTableManagerService;

    /**
     * 创建表
     */
    @GetMapping(value = "/createTable")
    //@RequiresPermissions("hiveTableManager:createTable")
    public Result createTime(@RequestParam(value = "conver") Boolean conver) {
        return hiveTableManagerService.createTable(conver);
    }
    /**
     * 删除表
     */
    @GetMapping(value = "/dropTable")
    @RequiresPermissions("hiveTableManager:dropTable")
    public Result dropTable() {
        return hiveTableManagerService.dropTable();
    }

    /**
    * 查询所有表
    */
    @GetMapping("/findTableList")
    @RequiresPermissions("dhlk:view")
    public Result findTableList() {
        return  hiveTableManagerService.findTableList();
    }
}
