package com.dhlk.web.hive.controller;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.basicmodule.service.HiveTableManagerService;
import com.dhlk.web.hive.service.HiveService;
import domain.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/19
 */
@RestController
@RequestMapping("hiveManager")
public class HiveController {
    @Autowired
    private HiveService hiveService;//hive服务中hive表管理
    @Autowired
    private HiveTableManagerService hiveTableManagerService;//mysql数据库中hive表管理


    @ApiOperation("hive数据表查询")
    @GetMapping("/findHiveTableList")
    public Result findHiveTableList() {
        return hiveService.findTableList();
    }

    @GetMapping(value = "/createTable")
    public Result createTable(@RequestParam(value = "conver") Boolean conver){
            Result result=hiveTableManagerService.createTable(conver);
            List<MetaTable> dataMap = (List<MetaTable>) result.getData();
            hiveService.createTable(dataMap);
        return result;
    }

    @GetMapping(value = "/dropTable")
    public Result dropTable(){
        Result result=hiveTableManagerService.dropTable();
        List<MetaTable> dataMap = (List<MetaTable>) result.getData();
        hiveService.dropTable(dataMap);
        return result;
    }
}