package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.ProductDevicesService;
import domain.Result;
import com.dhlk.entity.basicmodule.ProductDevices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 版本        修改时间        作者        修改内容
 * V1.0        ------        songlp       原始版本
 * 文件说明: 柜位管理接口
 **/
@RestController
@RequestMapping(value = "/productDevices")
@Api(description = "生产设备管理")
public class ProductDevicesController {
    @Autowired
    private ProductDevicesService productDevicesService;

    /**
     * 列表查询
     * @return
     */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  productDevicesService.findList(name);
    }
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestBody ProductDevices productDevices) throws Exception {
        return productDevicesService.save(productDevices);
    }

    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) throws Exception {
        return productDevicesService.delete(ids);
    }
}
