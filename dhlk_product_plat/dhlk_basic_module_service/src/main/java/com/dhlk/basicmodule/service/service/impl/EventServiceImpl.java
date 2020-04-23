package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.service.EventService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.ProductDevices;
import domain.Result;
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
public class EventServiceImpl implements EventService {
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    public Result getAlarms(Integer deviceId ,String searchStatus, String status, int limit, Long startTime, Long endTime, boolean ascOrder, String offset, Boolean fetchOriginator) throws Exception {
        ProductDevices pd = productDevicesDao.findProductDevicesById(deviceId);
        //   /api/alarm/{entityType}/{entityId}{?searchStatus,status,limit,startTime,endTime,ascOrder,offset,fetchOriginator}
        String api = tbBaseUrl+ Const.GETALARMS + "/DEVICE/" + pd.getTbId();
        Map<String, String> params=new HashMap<String, String>();
        params.put("searchStatus",searchStatus);
        params.put("limit",""+limit);
        if(startTime!=null && endTime!=null){
            params.put("startTime",startTime.toString());
            params.put("endTime",endTime.toString());
        }
        params.put("ascOrder",""+ascOrder);
        params.put("offset",offset);
        params.put("fetchOriginator",fetchOriginator.toString());
        HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), params);
        System.out.println("httpClientResult-----------"+httpClientResult.getContent());
        return ResultUtils.success(JSON.parse(httpClientResult.getContent()));
    }
}
