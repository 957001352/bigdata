package com.dhlk.web.basicmodule.service.fbk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.web.basicmodule.service.TelemetryService;
import domain.Result;
import enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import systemconst.Const;
import utils.HttpClientResult;
import utils.HttpClientUtils;
import utils.ResultUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelemetryServiceFbk implements TelemetryService {

    @Override
    public Result getTimeseries(Integer deviceId, String keys, Long startTs, Long endTs, Long interval, Integer limit, String agg) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }


    @Override
    public Result getAttributesByScope(String tbId) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
