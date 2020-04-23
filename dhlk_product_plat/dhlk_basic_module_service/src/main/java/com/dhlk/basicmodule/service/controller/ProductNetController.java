package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.ProductNetService;
import domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 生产和网络设备关系配置
*/
@RestController
@RequestMapping(value = "/productNet")
public class ProductNetController {
    @Autowired
    private ProductNetService productNetService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("productNet:save")
    public Result save(@RequestParam(value = "netId") String netId,
                       @RequestParam(value = "productIds", required = false) String productIds,
                       @RequestParam(value = "type") Integer type) throws Exception {
        return productNetService.save(netId,productIds,type);
    }
    /**
     * 删除
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("productNet:delete")
    public Result delete(@RequestParam(value = "id") String id) {
        return productNetService.delete(id);
    }

    /**
    * 列表查询
    * @return
    */
    @GetMapping("/findList")
    @RequiresPermissions("productNet:view")
    public Result findList(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "type", required = false) Integer type) {
        return  productNetService.findList(name,type);
    }

    @GetMapping("/findBiList")
    @RequiresPermissions("productNet:view")
    public Result findBiList(@RequestParam(value = "netId", required = false) Integer netId) {
        return  productNetService.findBiList(netId);
    }
    @GetMapping("/findComputerList")
    @RequiresPermissions("productNet:view")
    public Result findComputerList(@RequestParam(value = "netId", required = false) Integer netId) {
        return  productNetService.findComputerList(netId);
    }


    @GetMapping("/findNotBiList")
    @RequiresPermissions("productNet:view")
    public Result findNotBiList() {
        return  productNetService.findNotBiList();
    }

    @GetMapping("/findNotComputerList")
    @RequiresPermissions("productNet:view")
    public Result findNotComputerList() {
        return  productNetService.findNotComputerList();
    }
}
