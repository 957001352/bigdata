package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.service.TelemetryService;
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
public class TelemetryServiceImpl implements TelemetryService {
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    @Override
    public Result getTimeseries(Integer deviceId, String keys, Long startTs,Long endTs,Long interval,Integer limit,String aggStr) throws Exception {
        ProductDevices pd = productDevicesDao.findProductDevicesById(deviceId);
        ///api/plugins/telemetry/{entityType}/{entityId}/values/timeseries
        String api = tbBaseUrl+ Const.GETTIMESERIES + "/DEVICE/" + pd.getTbId()+"/values/timeseries";
        Map<String, String> params=new HashMap<String, String>();
        params.put("keys",keys);
        params.put("startTs",startTs.toString());
        params.put("endTs",endTs.toString());
        params.put("interval",interval.toString());
        params.put("limit",limit.toString());
        params.put("aggStr",aggStr);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), params);
        System.out.println("2222"+httpClientResult.getContent());
        return ResultUtils.success(httpClientResult.getContent());
    }

    @Override
    public Result getLatestTimeseries(Integer deviceId, String keys) throws Exception {

        return null;
    }

    @Override
    public Result getAttributesByScope(Integer deviceId) throws Exception {
        ProductDevices pd = productDevicesDao.findProductDevicesById(deviceId);
        //   /api/plugins/telemetry/{entityType}/{entityId}/values/attributes/{scope}{?keys}
        String api = tbBaseUrl+ Const.GETTIMESERIES + "/DEVICE/" + pd.getTbId()+"/values/attributes/SERVER_SCOPE";
        Map<String, Object> params=new HashMap<String, Object>();
        //params.put("keys",keys);
        HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);
        JSONObject json=new JSONObject();

        List list = JSON.parseObject(httpClientResult.getContent(), List.class);
        System.out.println("list-----------"+list);
        for(Object ll:list){
          Map map = JSON.parseObject(ll.toString(), Map.class);
          if(map.get("key").equals("active")){
              json.put("active",map.get("value"));
          }

        }
        System.out.println("json-----------"+json.toJSONString());
        return ResultUtils.success(json.toJSONString());
    }
}
