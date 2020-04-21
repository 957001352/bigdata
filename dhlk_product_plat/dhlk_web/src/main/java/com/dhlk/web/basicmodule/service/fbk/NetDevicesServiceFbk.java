package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.web.basicmodule.service.NetDevicesService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class NetDevicesServiceFbk implements NetDevicesService {


    @Override
    public Result save(NetDevices netDevices) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) throws Exception{
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPruductDevicesList(Integer netDevicesId) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result isEnable(Integer id, Integer status) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}