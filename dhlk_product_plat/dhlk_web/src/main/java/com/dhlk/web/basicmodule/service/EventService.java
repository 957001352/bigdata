package com.dhlk.web.basicmodule.service;

import domain.Result;

public interface EventService {
    public Result getAlarms(Integer deviceId, String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception;
}
