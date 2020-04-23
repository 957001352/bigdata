package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.TelemetryService;
import domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "TelemetryController", description = "遥测数据")
@RequestMapping(value = "/telemetry")
@RestController
public class TelemetryController {
    @Autowired
    private TelemetryService telemetryService;

    /**
     * 获取设备的服务端属性
     *
     * @param tbId
     * @return
     */
    @ApiOperation("获取设备的服务端属性")
    @GetMapping(value = "/getAttributesByScope")
    //@RequiresPermissions("menu:save")
    public Result getAttributesByScope(@RequestParam(value="tbId") String tbId) throws Exception {
        return telemetryService.getAttributesByScope(tbId);
    }
    /**
     * 获取设备的历史遥测数据
     *
     * @param deviceId
     * @param keys
     * @return
     */
    @ApiOperation("获取设备的历史遥测数据")
    @GetMapping(value = "/getTimeseries")
    //@RequiresPermissions("menu:save")
    public Result getTimeseries(@RequestParam(value="deviceId")Integer deviceId,
                                @RequestParam(name = "keys") String keys,
                                @RequestParam(name = "startTs", defaultValue = "0") Long startTs,
                                @RequestParam(name = "endTs", defaultValue = "0") Long endTs,
                                @RequestParam(name = "interval", defaultValue = "0") Long interval,
                                @RequestParam(name = "limit", defaultValue = "100") Integer limit,
                                @RequestParam(name = "agg", defaultValue = "NONE") String agg) throws Exception {
        return telemetryService.getTimeseries(deviceId,keys,startTs,endTs,interval,limit,agg);
    }
}
