package com.dhlk.basicmodule.service.service;

import domain.Result;
import org.springframework.web.bind.annotation.RequestParam;

public interface TelemetryService {
    public Result getTimeseries(Integer deviceId,String keys,Long startTs,Long endTs,Long interval,Integer limit,String aggStr) throws Exception;

    public Result getLatestTimeseries(Integer deviceId,String keys) throws Exception;

    public Result getAttributesByScope(Integer deviceId) throws Exception;
}
