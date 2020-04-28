package com.dhlk.web.basicmodule.service;

import com.dhlk.web.basicmodule.service.fbk.TelemetryServiceFbk;
import com.dhlk.domain.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "basicmodule-service/telemetry", fallback = TelemetryServiceFbk.class)
public interface TelemetryService {

    @GetMapping(value = "/getAttributesByScope")
    public Result getAttributesByScope(@RequestParam(value="tbId",required = false) String tbId) throws Exception;
    /**
     * 获取设备的历史遥测数据
     *
     * @param deviceId
     * @param keys
     * @return
     */
    @ApiOperation("获取设备的历史遥测数据")
    @GetMapping(value = "/getTimeseries")
    public Result getTimeseries(@RequestParam(value="deviceId")Integer deviceId,
                                @RequestParam(name = "keys") String keys,
                                @RequestParam(name = "startTs", defaultValue = "0") Long startTs,
                                @RequestParam(name = "endTs", defaultValue = "0") Long endTs,
                                @RequestParam(name = "interval", defaultValue = "0") Long interval,
                                @RequestParam(name = "limit", defaultValue = "100") Integer limit,
                                @RequestParam(name = "agg", defaultValue = "NONE") String agg) throws Exception ;

}
