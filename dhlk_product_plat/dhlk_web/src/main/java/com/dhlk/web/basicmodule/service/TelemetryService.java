package com.dhlk.web.basicmodule.service;

import domain.Result;

public interface TelemetryService {
    public Result getTimeseries(Integer deviceId, String keys, Long startTs, Long endTs, Long interval, Integer limit, String aggStr) throws Exception;

    public Result getLatestTimeseries(Integer deviceId, String keys) throws Exception;

    public Result getAttributesByScope(Integer deviceId) throws Exception;
}
