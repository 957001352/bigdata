package com.dhlk.web.basicmodule.service.fbk;


import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.basicmodule.service.HiveTableManagerService;
import com.dhlk.web.basicmodule.service.MetaTableService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import utils.ResultUtils;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/19
 */
@Service
public class HiveTableManagerServiceFbk implements HiveTableManagerService {

    @Override
    public Result createTable(Boolean conver) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result dropTable() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }

    @Override
    public Result findTableList() {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}