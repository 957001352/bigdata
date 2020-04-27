package com.dhlk.web.basicmodule.service.fbk;


import com.dhlk.web.basicmodule.service.EventService;
import com.dhlk.domain.Result;
import com.dhlk.enums.ResultEnum;
import org.springframework.stereotype.Service;
import com.dhlk.utils.ResultUtils;

@Service
public class EventServiceFbk implements EventService {

    @Override
    public Result getAlarms(Integer deviceId, String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
