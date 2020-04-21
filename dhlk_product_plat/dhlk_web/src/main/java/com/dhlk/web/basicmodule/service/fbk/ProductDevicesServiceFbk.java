package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.web.basicmodule.service.ProductDevicesService;
import domain.Result;
import com.dhlk.entity.basicmodule.ProductDevices;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class ProductDevicesServiceFbk implements ProductDevicesService {


    @Override
    public Result save(ProductDevices productDevices) throws Exception{
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids)throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

}