package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.NetDevicesDao;
import com.dhlk.basicmodule.service.dao.NetDevicesSoftDao;
import com.dhlk.basicmodule.service.service.NetDevicesService;
import com.dhlk.basicmodule.service.service.NetDevicesSoftService;
import com.dhlk.basicmodule.service.service.RedisService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.basicmodule.NetDevices;
import com.dhlk.entity.basicmodule.ProductDevices;
import com.dhlk.entity.tb.AdditionalInfo;
import com.dhlk.entity.tb.Id;
import com.dhlk.entity.tb.TbProductDevices;
import domain.Result;
import enums.ResultEnum;
import exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RedisBasicService;
import systemconst.Const;
import utils.CheckUtils;
import utils.HttpClientResult;
import utils.HttpClientUtils;
import utils.ResultUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Description 网络设备管理
 * @Author lpsong
 * @Date 2020/3/12
 */
@Service
public class NetDevicesSerivceImpl implements NetDevicesService {
    @Autowired
    private NetDevicesDao netDevicesDao;

    @Autowired
    private NetDevicesSoftDao netDevicesSoftDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Value("${tb.baseUrl}")
    private String tbBaseUrl;

    @Override
    @Transactional
    public Result save(NetDevices netDevices) throws Exception {
        if(!CheckUtils.isNull(netDevices.getName())){
            List<NetDevices> list = netDevicesDao.findList(netDevices.getName(), null);
            if(list!=null && list.size()>0){
                return ResultUtils.error(1000,"设备名字重复");
            }
        }
        //jsonDescription设备描述信息
        JSONObject jsonDescription = new JSONObject();
        jsonDescription.put("pdFactoryId", netDevices.getFactoryId());
        //设备additionalInfo属性
        AdditionalInfo additionalInfo = null;
        /*
         * productDevices id为空 保存 id不为空 更新
         */
        if (CheckUtils.isNull(netDevices.getId())) {  //网络设备 "gateway":true
            additionalInfo = new AdditionalInfo(true, jsonDescription.toJSONString());

            //保存设备信息
            TbProductDevices tbProductDevices = new TbProductDevices(netDevices.getName(), netDevices.getTypeId().toString(), netDevices.getName(), additionalInfo);
            HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
            System.out.println("content-----------"+responseEntity.getContent()+"------------code------------"+responseEntity.getCode());
            //ResponseEntity<Map> responseEntity = restTemplateUtil.postRestTemplate(Const.TBSAVEDEVICE, tbProductDevices, Map.class, true);
            if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
                //保存设备数据到dhlk数据库
                Map map = HttpClientUtils.resultToMap(responseEntity);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                String tbId = mapId.get("id").toString();
                netDevices.setTbId(tbId);

                //把dhlk网络设备关联的生产设备的名字保存到对应的tb网关设备的共享属性中
                //saveGatewayRelationProducts(netDevices);

                try {
                    //设置工厂ID
                    netDevices.setFactoryId(redisService.findFactoryId());
                    //新增
                    Integer flag = netDevicesDao.insert(netDevices);
                    //插入软件版本信息
                    if(netDevices.getSoftList()!=null&&netDevices.getSoftList().size()>0){
                        netDevicesSoftDao.insertBatch(netDevices.getSoftList(),netDevices.getId());
                    }
                    //成功
                    return ResultUtils.success();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    //失败 删除保存到tb中的数据
                    HttpClientUtils.doDeleteHeaders(tbBaseUrl+Const.TBDELETEDEVICEBYID + "/" + netDevices.getTbId(),restTemplateUtil.getHeaders(true));
                    //restTemplateUtil.deleteRestTemplate(Const.TBDELETEDEVICEBYID + "/" + netDevices.getTbId(), Map.class, true);
                    return ResultUtils.failure();
                }
            } else {
                //保存设备数据到tb失败  返回保存失败信息
                return ResultUtils.failure();
            }
        } else {
            //更新
            //根据设备id查出tbId
            NetDevices tbND = netDevicesDao.findProductNetDevicesById(netDevices.getId());
            if (tbND!=null ) {
                //设备描述信息
                jsonDescription.put("pdId", netDevices.getId());
                additionalInfo = new AdditionalInfo(true, jsonDescription.toJSONString());
                //构造tb设备更新格式
                Id id = new Id(tbND.getTbId(), "DEVICE");
                //在更新tb之前备份数据
                NetDevices pdBack = netDevicesDao.findProductNetDevicesById(netDevices.getId());
                String api = Const.SELECTTBDEVICEBYID + "/" + pdBack.getTbId();
                //ResponseEntity<Map> resTbBack = restTemplateUtil.getRestTemplate(api, Map.class, true);
                HttpClientResult resTbBack = HttpClientUtils.doGet(tbBaseUrl+api, restTemplateUtil.getHeaders(true), null);
                System.out.println("resEntity---------------------" + resTbBack.getContent());
                TbProductDevices tbProductDevices = new TbProductDevices(id, tbND.getName(), tbND.getTypeId().toString(),tbND.getName(), additionalInfo);
                HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
                System.out.println("content-----------"+responseEntity.getContent()+"------------code------------"+responseEntity.getCode());
                //ResponseEntity<Map> responseEntity = restTemplateUtil.postRestTemplate(Const.TBSAVEDEVICE,tbProductDevices, Map.class, true);
                if (responseEntity.getCode() == 200) {//保存设备数据到tb成功
                    //保存设备数据到dhlk数据库
                    Map map = HttpClientUtils.resultToMap(responseEntity);
                    Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                    String tbId = mapId.get("id").toString();
                    netDevices.setTbId(tbId);

                    //把dhlk网络设备关联的生产设备的名字保存到对应的tb网关设备的共享属性中
                    //saveGatewayRelationProducts(netDevices);

                    try {
                        //更新
                        Integer flag = netDevicesDao.update(netDevices);
                        //更新软件版本信息，先删除，在插入
                        if(netDevices.getSoftList()!=null&&netDevices.getSoftList().size()>0){
                            netDevicesSoftDao.deleteByNetDevicesId(netDevices.getId());
                            netDevicesSoftDao.insertBatch(netDevices.getSoftList(),netDevices.getId());
                        }
                        //成功
                        return ResultUtils.success();
                    } catch (RuntimeException e) {
                        Map device = HttpClientUtils.resultToMap(resTbBack);
                        //Map<String,Object> device=resTbBack.getBody();
                        String name=device.get("name").toString();
                        String label=device.get("label").toString();
                        String type=device.get("type").toString();
                        String additionalInfoBackUpdate=device.get("additionalInfo").toString();
                        Map<String,Object> mapIdBack=(Map<String,Object>)device.get("id");
                        String idBack=mapIdBack.get("id").toString();
                        Id idBackUpdate=new Id(idBack,"DEVICE");
                        //设备additionalInfo属性
                        AdditionalInfo additionalInfoBack = new AdditionalInfo(false, additionalInfoBackUpdate);
                        TbProductDevices tbProductDevicesBack = new TbProductDevices(idBackUpdate,name, type,label, additionalInfoBack);
                        while(true){
                            //失败 还原tb中的数据
                            HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevicesBack));

                            //ResponseEntity<Map> responseEntityBack = restTemplateUtil.postRestTemplate(Const.TBSAVEDEVICE, tbProductDevicesBack, Map.class, true);
                            if(responseEntityBack.getCode()==200){
                                break;
                            }
                        }
                        return ResultUtils.failure();
                    }
                }else {
                    //更新设备数据到tb失败  返回保存失败信息
                    return ResultUtils.failure();
                }
            }else{
                return ResultUtils.failure();
            }
        }
    }
    @Override
    @Transactional
    public Result delete(String  ids) throws Exception {
        if (CheckUtils.isNull(ids)) {
            return ResultUtils.error(ResultEnum.PARAM_ISNULL);
        }
        Integer flag=0;
        //根据id查出对应的tb_id
        List<NetDevices> netDevicesList = netDevicesDao.findTbIdsListbyIds(Arrays.asList(ids.split(",")));
        //删除tb中的数据
        for (NetDevices nd : netDevicesList) {
            HttpClientResult httpClientResult = HttpClientUtils.doDeleteHeaders(tbBaseUrl + Const.TBDELETEDEVICEBYID + "/" + nd.getTbId(), restTemplateUtil.getHeaders(true));
            // ResponseEntity<Map> responseEntity = restTemplateUtil.deleteRestTemplate(Const.TBDELETEDEVICEBYID + "/" + pd.getTbId(), Map.class, true);
            if (httpClientResult.getCode() == 200) {
                //删除dhlk db中的数据
                Integer res = netDevicesDao.deleteById(nd.getId());
                if (res > 0) {
                    //删除软件信息
                    netDevicesSoftDao.deleteByNetDevicesId(nd.getId());
                    flag=1;
                    continue;
                } else {
                    flag=-1;
                    //还原tb中的数据
                    break;
                }
            } else {
                return ResultUtils.failure();
            }
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }


    @Override
    public Result findList(String name) {
        return ResultUtils.success(netDevicesDao.findList(name,redisService.findFactoryId()));
    }

    @Override
    public Result findPruductDevicesList(Integer netDevicesId) {
        return  ResultUtils.success(netDevicesDao.findPruductDevicesList(netDevicesId));
    }

    @Override
    public Result isEnable(Integer id, Integer status) throws MyException{
        if (!CheckUtils.isNumeric(id)) {
            return ResultUtils.failure();
        }
        if(!CheckUtils.checkStatus(status)){
            return ResultUtils.error("非法状态");
        }
        if (netDevicesDao.isEnable(id,status) > 0) {
            return  ResultUtils.success();
        }
        return ResultUtils.failure();
    }
    /*private void saveGatewayRelationProducts(NetDevices netDevices) throws Exception {
        //把dhlk网络设备关联的生产设备的名字保存到对应的tb网关设备的共享属性中
        List<ProductDevices> pruductDevicesList = netDevicesDao.findPruductDevicesList(netDevices.getId());
        List<String> list=new ArrayList<>();
        pruductDevicesList.stream().forEach(pruductDevices->list.add( pruductDevices.getName()));
        JSONObject jsonSharedArrribute = new JSONObject();
        jsonSharedArrribute.put("deviceList", JSON.toJSONString(list));
        HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + netDevices.getTbId() + "/SHARED_SCOPE", restTemplateUtil.getHeaders(true), jsonSharedArrribute.toJSONString());
        //ResponseEntity<Map> mapResponseEntity = restTemplateUtil.postRestTemplate(Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + netDevices.getTbId() + "/SHARED_SCOPE", jsonSharedArrribute, Map.class, true);
    }*/
}