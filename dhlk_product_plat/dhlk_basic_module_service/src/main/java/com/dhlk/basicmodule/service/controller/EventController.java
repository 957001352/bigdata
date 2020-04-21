package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.EventService;
import com.dhlk.basicmodule.service.service.TelemetryService;
import domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "EventController", description = "事件")
@RequestMapping(value = "/event")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @ApiOperation("获取设备的警告信息")
    @PostMapping(value = "/getAttributesByScope")
    public Result getAlarms(@RequestParam(value="deviceId")Integer deviceId,
                            @RequestParam(value="searchStatus",required = false) String searchStatus,
                            @RequestParam(value="status",required = false) String status,
                            @RequestParam(value="limit",required = true) int limit,
                            @RequestParam(value="startTime",required = false) Long startTime,
                            @RequestParam(value="endTime",required = false) Long endTime,
                            @RequestParam(value="ascOrder",required = false, defaultValue = "false") boolean ascOrder,
                            @RequestParam(value="offset",required = false) String offset,
                            @RequestParam(value="fetchOriginator",required = false) Boolean fetchOriginator) throws Exception{
        return eventService.getAlarms(deviceId,searchStatus,status,limit,startTime,endTime,ascOrder,offset,fetchOriginator);
    }
}
