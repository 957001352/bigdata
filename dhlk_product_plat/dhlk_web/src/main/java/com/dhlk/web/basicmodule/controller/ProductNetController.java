package com.dhlk.web.basicmodule.controller;

import com.dhlk.web.basicmodule.service.ProductNetService;
import domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* 生产和网络设备关系配置
*/
@RestController
@RequestMapping(value = "/productNet")
@Api(description = "配置管理")
public class ProductNetController {
    @Autowired
    private ProductNetService productNetService;

    /**
     * 保存
     * @param
     * @return
     */
    @ApiOperation("新增/修改")
    @PostMapping(value = "/save")
    public Result save(@RequestParam(value = "netId") String netId,
                       @RequestParam(value = "productIds") String productIds,
                       @RequestParam(value = "type") Integer type) {
        return productNetService.save(netId,productIds,type);
    }
    /**
     * 列表查询
     * @return
     */
    @ApiOperation("列表查询")
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "type") Integer type) {
        return  productNetService.findList(name,type);
    }

    /**
     * 删除
     * @param id
     * @return result
     */
    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") String id) {
        return productNetService.delete(id);
    }
    @ApiOperation("BI控制器查询")
    @GetMapping("/findBiList")
    public Result findBiList(@RequestParam(value = "netId", required = false) Integer netId) {
        return  productNetService.findBiList(netId);
    }
    @ApiOperation("一体机查询")
    @GetMapping("/findComputerList")
    public Result findComputerList(@RequestParam(value = "netId", required = false) Integer netId) {
        return  productNetService.findComputerList(netId);
    }

    @ApiOperation("未绑定BI控制器查询")
    @GetMapping("/findNotBiList")
    public Result findNotBiList() {
        return  productNetService.findNotBiList();
    }
    @ApiOperation("未绑定一体机查询")
    @GetMapping("/findNotComputerList")
    public Result findNotComputerList() {
        return  productNetService.findNotComputerList();
    }
}
