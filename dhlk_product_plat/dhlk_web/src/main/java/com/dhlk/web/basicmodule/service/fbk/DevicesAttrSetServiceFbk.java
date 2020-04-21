package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.basicmodule.DevicesAttrSet;
import com.dhlk.web.basicmodule.service.DevicesAttrSetService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

/**
 * 设备属性管理
 */
@Service
public class DevicesAttrSetServiceFbk implements DevicesAttrSetService {
    @Override
    public Result save(DevicesAttrSet devicesAttrSet) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(Integer id) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
