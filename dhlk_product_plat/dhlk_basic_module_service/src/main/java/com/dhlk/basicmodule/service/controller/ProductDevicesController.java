package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.ProductDevicesService;
import com.dhlk.entity.basicmodule.ProductDevices;
import domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 生产设备管理
*/
@RestController
@RequestMapping(value = "/productDevices")
public class ProductDevicesController {
    @Autowired
    private ProductDevicesService productDevicesService;



    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("productDevices:save")
    public Result save(@RequestBody ProductDevices productDevices) throws Exception {
        return productDevicesService.save(productDevices);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("productDevices:delete")
    public Result delete(@RequestParam(value = "ids") String ids) throws Exception {
        return productDevicesService.delete(ids);
    }

    /**
    * 列表查询
    */
    @GetMapping("/findList")
    @RequiresPermissions("productDevices:view")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  productDevicesService.findList(name);
    }
}
