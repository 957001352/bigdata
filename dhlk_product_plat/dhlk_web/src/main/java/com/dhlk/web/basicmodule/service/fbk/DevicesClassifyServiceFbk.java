package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.web.basicmodule.service.DevicesClassifyService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

/**
 * 设备分类管理
 */
@Service
public class DevicesClassifyServiceFbk implements DevicesClassifyService {
    @Override
    public Result save(DevicesClassify devicesClassify) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
