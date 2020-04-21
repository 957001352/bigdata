package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.ProductDevicesServiceFbk;
import domain.Result;
import com.dhlk.entity.basicmodule.ProductDevices;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 生产设备管理
 **/
@FeignClient(value = "basicmodule-service/productDevices", fallback = ProductDevicesServiceFbk.class)
public interface ProductDevicesService {

    /**
     * 保存
     * @param productDevices
     * @return
     */
    @PostMapping(value = "/save")
    Result save(@RequestBody ProductDevices productDevices) throws Exception;



    /**
     * delete
     * @param ids
     * @return
     */
    @GetMapping(value = "/delete")
    Result delete(@RequestParam(value = "ids", required = true) String ids)throws Exception;


    /**
     * 列表查询
     */
    @GetMapping(value = "/findList")
    Result findList(@RequestParam(value = "name", required = false) String name);
}
