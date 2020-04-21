package com.dhlk.web.dm.service.fbk;

import com.dhlk.entity.dm.DmNetType;
import com.dhlk.web.dm.service.DmNetTypeService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class DmNetTypeServiceFbk implements DmNetTypeService {


    @Override
    public Result save(DmNetType dmNetType) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findList(String name) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}