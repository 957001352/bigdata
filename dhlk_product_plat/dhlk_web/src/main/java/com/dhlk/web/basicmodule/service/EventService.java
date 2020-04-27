package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.EventServiceFbk;
import com.dhlk.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "basicmodule-service/event", fallback = EventServiceFbk.class)
public interface EventService {
    @GetMapping(value = "/getAlarms")
    public Result getAlarms(@RequestParam(value="deviceId")Integer deviceId,
                            @RequestParam(value="searchStatus",required = false) String searchStatus,
                            @RequestParam(value="status",required = false) String status,
                            @RequestParam(value="limit",required = true) int limit,
                            @RequestParam(value="startTime",required = false) Long startTime,
                            @RequestParam(value="endTime",required = false) Long endTime,
                            @RequestParam(value="ascOrder",required = false, defaultValue = "false") boolean ascOrder,
                            @RequestParam(value="offset",required = false) String offset,
                            @RequestParam(value="fetchOriginator",required = false) Boolean fetchOriginator) throws Exception;

    }
