package com.dhlk.web.basicmodule.service.fbk;


import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.web.basicmodule.service.EventService;
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
import java.util.Map;

@Service
public class EventServiceFbk implements EventService {

    @Override
    public Result getAlarms(Integer deviceId, String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
