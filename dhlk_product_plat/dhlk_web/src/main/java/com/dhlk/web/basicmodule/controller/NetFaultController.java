package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.NetFault;
import com.dhlk.web.basicmodule.service.NetFaultService;
import com.dhlk.domain.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 网络设备故障
 * @Author lpsong
 * @Date 2020/4/20
 */
@RestController
@RequestMapping(value = "/netFault")
@Api(description = "网络设备故障")
public class NetFaultController {

    @Autowired
    private NetFaultService netFaultService;


    @PostMapping(value = "/save")
    public Result save(@RequestBody NetFault netFault) {
        return netFaultService.save(netFault);
    }



    @PostMapping(value = "/dealFault")
    public Result dealFault(@RequestParam(value = "id") Integer id,
                            @RequestParam(value = "status") Integer status) {
        return netFaultService.dealFault(id,status);
    }
    /**
     *列表查询
     * @param status
     * @return
     */
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "tbId") String tbId,
                           @RequestParam(value = "status", required = false) Integer status) {
        return  netFaultService.findList(tbId,status);
    }
}