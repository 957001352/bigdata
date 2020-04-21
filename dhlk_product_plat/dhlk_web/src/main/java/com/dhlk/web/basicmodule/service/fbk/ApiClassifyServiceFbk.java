package com.dhlk.web.basicmodule.service.fbk;

import com.dhlk.entity.api.ApiClassify;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.web.basicmodule.service.ApiClassifyService;
import com.dhlk.web.basicmodule.service.ProductDevicesService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.ResultUtils;

/**
 * 生产设备管理
 **/
@Service
public class ApiClassifyServiceFbk implements ApiClassifyService {


    @Override
    public Result save(ApiClassify apiClassify) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result delete(String ids) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTreeList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result importExcel(MultipartFile file) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}