package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.NetDevicesService;
import com.dhlk.entity.basicmodule.NetDevices;
import domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 网络设备管理
*/
@RestController
@RequestMapping(value = "/netDevices")
public class NetDevicesController {
    @Autowired
    private NetDevicesService netDevicesService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("netDevices:save")
    public Result save(@RequestBody NetDevices netDevices) throws Exception {
        return netDevicesService.save(netDevices);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("netDevices:delete")
    public Result delete(@RequestParam(value = "ids") String ids) throws Exception {
        return netDevicesService.delete(ids);
    }

    /**
    *列表查询
     * @param name
    * @return
    */
    @GetMapping("/findList")
    @RequiresPermissions("netDevices:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  netDevicesService.findList(name);
    }
    /*
    * 网络设备关联的生产设备查询
     * @param netDevicesId
    * @return
    */
    @GetMapping("/findPruductDevicesList")
    @RequiresPermissions("netDevices:view")
    public Result findPruductDevicesList(@RequestParam(value = "netDevicesId", required = true) Integer netDevicesId) {
        return  netDevicesService.findPruductDevicesList(netDevicesId);
    }
    /**
     * 状态修改启用禁用
     */
    @PostMapping(value = "/isEnable")
    @RequiresPermissions("metaTable:isEnable")
    public Result isEnable(@RequestParam(value = "id", required = true) Integer id,
                           @RequestParam(value = "status", required = true) Integer status) {
        return netDevicesService.isEnable(id,status);
    }
}
