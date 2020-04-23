package com.dhlk.basicmodule.service.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.dao.DevicesClassifyDao;
import com.dhlk.basicmodule.service.dao.OrgDao;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.dao.UserDao;
import com.dhlk.basicmodule.service.service.ProductDevicesService;
import com.dhlk.basicmodule.service.service.RedisService;
import com.dhlk.basicmodule.service.util.RestTemplateUtil;
import com.dhlk.entity.api.ApiClassify;
import com.dhlk.entity.basicmodule.*;
import com.dhlk.entity.tb.AdditionalInfo;
import com.dhlk.entity.tb.Id;
import com.dhlk.entity.tb.TbProductDevices;
import com.dhlk.entity.tb.credentials.DeviceCredentials;
import com.dhlk.entity.tb.credentials.DeviceId;
import domain.Result;
import domain.Tree;
import enums.ResultEnum;
import exceptions.MyException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import service.RedisBasicService;
import systemconst.Const;
import utils.*;

import java.util.*;

/**
 * @Description 生产设备管理
 * @Author lpsong
 * @Date 2020/3/12
 */
@Service
public class ProductDevicesSerivceImpl implements ProductDevicesService {
    @Autowired
    private ProductDevicesDao productDevicesDao;

    @Autowired
    private DevicesClassifyDao devicesClassifyDao;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrgDao orgDao;

    @Value("${tb.baseUrl}")
    private String tbBaseUrl;
    @Value("${attachment.path}")
    private String attachmentPath;
    /*
    保存到tb的数据格式
    {
        "name": "dhlk111",
        "type": "dhlk222",
        "label": "dhlk333",
        "additionalInfo": {
            "gateway": true,
            "description": "dhlk444"
        }
    }
    更新到tb的数据格式
    {
        "id": {
            "entityType": "DEVICE",
            "id": "acdefe50-6da8-11ea-8392-6dbee2348266"
        },
        "additionalInfo": {
            "gateway": true,
            "description": "dhlk444"
        },
        "name": "机器lllvvv",
        "type": "dhlkvvv",
        "label": "002lllvvv"
    }
    */
    @Override
    public Result save(ProductDevices productDevices) throws Exception {
        if(!CheckUtils.isNull(productDevices.getName())){
            List<ProductDevices> list = productDevicesDao.findList(productDevices.getName(), null, null);
            if(list!=null && list.size()>0){
                return ResultUtils.error(1000,"设备名字重复");
            }
        }
        //默认不是网关设备
        //jsonDescription设备描述信息
        JSONObject jsonDescription = new JSONObject();
        jsonDescription.put("pdOrgId", productDevices.getOrgId());
        //设备additionalInfo属性
        /*JSONObject json = new JSONObject();
        json.put("gateway", true);
        json.put("description", jsonDescription);*/
        AdditionalInfo additionalInfo = null;
        /*
         * productDevices id为空 保存 id不为空 更新
         */
        if (CheckUtils.isNull(productDevices.getId())) {
            additionalInfo = new AdditionalInfo(false, jsonDescription.toJSONString());

            //保存设备信息
            TbProductDevices tbProductDevices = new TbProductDevices(productDevices.getName(), productDevices.getClassifyId().toString(), productDevices.getName(), additionalInfo);
            HttpClientResult httpClientResult = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
            // ResponseEntity<Map> responseEntity = restTemplateUtil.postRestTemplate(Const.TBSAVEDEVICE, tbProductDevices, Map.class, true);
            if (httpClientResult.getCode() == 200) {//保存设备数据到tb成功
                //保存设备数据到dhlk数据库
                Map map = HttpClientUtils.resultToMap(httpClientResult);
                Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                String tbId = mapId.get("id").toString();
                productDevices.setTbId(tbId);

                //把dhlk设备属性保存到对应的tb设备的共享属性中
                List<String> list=devicesClassifyDao.findAttrByClassifyById(productDevices.getClassifyId());
                JSONObject jsonSharedArrribute = new JSONObject();
                jsonSharedArrribute.put("attributeList", JSON.toJSONString(list));
                HttpClientResult attrClientResult = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + tbId + "/SHARED_SCOPE", restTemplateUtil.getHeaders(true), jsonSharedArrribute.toJSONString());

                System.out.println("content-----------"+attrClientResult.getContent()+"------------code------------"+attrClientResult.getCode());
               // ResponseEntity<Map> mapResponseEntity = restTemplateUtil.postRestTemplate(Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + tbId + "/SHARED_SCOPE", jsonSharedArrribute, Map.class, true);
                try {
                    //设置工厂ID
                    productDevices.setFactoryId(redisService.findFactoryId());
                    //生成tb设备凭证
                    String credentialsId= RandomStringUtils.randomAlphanumeric(20);
                    System.out.println("credentialsId>>>>>>>>>>>>>>>>"+credentialsId);
                    //保存tb设备凭证
                    saveOrUpdateDeviceCredentialsByTbDeviceId(tbId,credentialsId);
                    //设置tb设备凭证
                    productDevices.setCredentials(credentialsId);
                    //新增
                    Integer flag = productDevicesDao.insert(productDevices);
                    //成功
                    return ResultUtils.success();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    //失败 删除保存到tb中的数据
                    HttpClientUtils.doDeleteHeaders(tbBaseUrl+Const.TBDELETEDEVICEBYID + "/" + productDevices.getTbId(),restTemplateUtil.getHeaders(true));
                    //restTemplateUtil.deleteRestTemplate(Const.TBDELETEDEVICEBYID + "/" + productDevices.getTbId(), Map.class, true);
                    return ResultUtils.failure();
                }
            } else {
                //保存设备数据到tb失败  返回保存失败信息
                return ResultUtils.failure();
            }
        } else {
            //更新
            //根据设备id查出tbId
            List<ProductDevices> tbIds = productDevicesDao.findTbIdsListbyIds(Arrays.asList(productDevices.getId().toString()));
            if (!tbIds.isEmpty()) {
                //设备描述信息
                jsonDescription.put("pdId", productDevices.getId());
                additionalInfo = new AdditionalInfo(false, jsonDescription.toJSONString());
                //构造tb设备更新格式
                Id id = new Id(tbIds.get(0).getTbId(), "DEVICE");
                //在更新tb之前备份数据
                ProductDevices pdBack = productDevicesDao.findProductDevicesById(productDevices.getId());
                String api = tbBaseUrl+Const.SELECTTBDEVICEBYID + "/" + pdBack.getTbId();
                HttpClientResult httpClientResult = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);
                //ResponseEntity<Map> resTbBack = restTemplateUtil.getRestTemplate(api, Map.class, true);
                //System.out.println("resEntity---------------------" + resTbBack.getBody().toString());
                System.out.println("content-----------"+httpClientResult.getContent()+"------------code------------"+httpClientResult.getCode());

                TbProductDevices tbProductDevices = new TbProductDevices(id, productDevices.getName(), productDevices.getClassifyId().toString(),productDevices.getName(), additionalInfo);

                HttpClientResult responseEntity = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevices));
                // ResponseEntity<Map> responseEntity = restTemplateUtil.postRestTemplate(Const.TBSAVEDEVICE,tbProductDevices, Map.class, true);
                if (responseEntity.getCode()== 200) {//保存设备数据到tb成功
                    //保存设备数据到dhlk数据库
                    Map map = HttpClientUtils.resultToMap(responseEntity);
                    //Map<String, Object> map = responseEntity.getBody();
                    Map<String, Object> mapId = (Map<String, Object>) map.get("id");
                    String tbId = mapId.get("id").toString();
                    productDevices.setTbId(tbId);
                    saveAttrToTb(productDevices);
                    try {
                        //更新
                        Integer flag = productDevicesDao.update(productDevices);
                        //更新tb设备凭证
                        if(CheckUtils.isNull(productDevices.getCredentials())){
                            saveOrUpdateDeviceCredentialsByTbDeviceId(tbId,productDevices.getCredentials());
                        }
                        //成功
                        return ResultUtils.success();
                    } catch (RuntimeException e) {
                        Map device = HttpClientUtils.resultToMap(httpClientResult);
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
                           // ResponseEntity<Map> responseEntityBack = restTemplateUtil.postRestTemplate(Const.TBSAVEDEVICE, tbProductDevicesBack, Map.class, true);
                            HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(tbBaseUrl + Const.TBSAVEDEVICE, restTemplateUtil.getHeaders(true), JSON.toJSONString(tbProductDevicesBack));

                            if(responseEntityBack.getCode()==200){
                                break;
                            }
                        }
                        return ResultUtils.failure();
                    }
                }

            }
            return null;
        }
    }

    @Override
    public Result delete(String ids) throws Exception {
        if (CheckUtils.isNull(ids)) {
            return ResultUtils.error(ResultEnum.PARAM_ISNULL);
        }
        Integer flag=0;
        //根据id查出对应的tb_id
        List<ProductDevices> productDevicesList = productDevicesDao.findTbIdsListbyIds(Arrays.asList(ids.split(",")));
        //删除tb中的数据
        for (ProductDevices pd : productDevicesList) {
            HttpClientResult httpClientResult = HttpClientUtils.doDeleteHeaders(tbBaseUrl + Const.TBDELETEDEVICEBYID + "/" + pd.getTbId(), restTemplateUtil.getHeaders(true));
            // ResponseEntity<Map> responseEntity = restTemplateUtil.deleteRestTemplate(Const.TBDELETEDEVICEBYID + "/" + pd.getTbId(), Map.class, true);
            if (httpClientResult.getCode() == 200) {
                //删除dhlk db中的数据
                Integer res = productDevicesDao.deleteById(pd.getId());
                if (res > 0) {
                    flag=1;
                    continue;
                } else {
                    //还原tb中的数据
                    flag=-1;
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
        return ResultUtils.success(productDevicesDao.findList(name,redisService.findFactoryId().toString(),attachmentPath));
    }

    @Override
    public Result findTbDeviceByDhlkId(Integer id) throws Exception {
        ProductDevices productDevices = productDevicesDao.findProductDevicesById(id);
        String api = tbBaseUrl+Const.SELECTTBDEVICEBYID + "/" + productDevices.getTbId();
        HttpClientResult mapResponseEntity = HttpClientUtils.doGet(api, restTemplateUtil.getHeaders(true), null);
       // ResponseEntity<String> mapResponseEntity = restTemplateUtil.getRestTemplate(api, String.class, true);
        //restTemplateUtil.restTemplateExchange(api, HttpMethod.GET, null, String.class,true);
        System.out.println("mapResponseEntity---------------------" + mapResponseEntity.getContent());
        return ResultUtils.success(mapResponseEntity.getContent());
    }

    @Override
    public Result deleteById(Integer id) {
        if (!CheckUtils.isNull(id)) {
            return ResultUtils.error(ResultEnum.PARAM_ISNULL);
        }
        Integer res = productDevicesDao.deleteById(id);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result findAttrByClassifyById(String classifyId){
        //获取dhlk设备属性
        List<String> list= devicesClassifyDao.findAttrByClassifyById(classifyId);
        return ResultUtils.success(list);
    }


    private void saveAttrToTb(ProductDevices productDevices) throws Exception {
        //把dhlk设备属性保存到对应的tb设备的共享属性中
        List<String> list=devicesClassifyDao.findAttrByClassifyById(productDevices.getClassifyId());
        JSONObject jsonSharedArrribute = new JSONObject();
        jsonSharedArrribute.put("attributeList", JSON.toJSONString(list));
        String url=tbBaseUrl+Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + productDevices.getTbId() + "/SHARED_SCOPE";
        HttpClientResult responseEntityBack = HttpClientUtils.doPostStringParams(url, restTemplateUtil.getHeaders(true),jsonSharedArrribute.toJSONString());
       // ResponseEntity<Map> mapResponseEntity = restTemplateUtil.postRestTemplate(Const.SAVEDEVICESHAREDATTRIBUTE + "/DEVICE/" + productDevices.getTbId() + "/SHARED_SCOPE", jsonSharedArrribute, Map.class, true);

    }
    private Result saveOrUpdateDeviceCredentialsByTbDeviceId(String tbDeviceId,String credentialId) throws Exception {
        //根据tb设备id获取tb设备凭据id
        HttpClientResult httpClientResult = HttpClientUtils.doGet(tbBaseUrl + "/api/device/" + tbDeviceId + "/credentials", restTemplateUtil.getHeaders(true), null);
        System.out.println("content-----------"+httpClientResult.getContent()+"------------code------------"+httpClientResult.getCode());
        //ResponseEntity<Map> iDResponseEntity = restTemplateUtil.getRestTemplate("/api/device/"+tbDeviceId+"/credentials", Map.class, true);
        //保存设备数据到dhlk数据库
        //Map<String, Object> map = iDResponseEntity.getBody();
        Map map = HttpClientUtils.resultToMap(httpClientResult);
        Map<String, Object> mapId = (Map<String, Object>) map.get("id");
        String credentialsId = mapId.get("id").toString();
        com.dhlk.entity.tb.credentials.Id id=new com.dhlk.entity.tb.credentials.Id(credentialsId);
        DeviceId deviceId=new DeviceId(tbDeviceId,"DEVICE");
        DeviceCredentials deviceCredentials=new DeviceCredentials(id,deviceId,"ACCESS_TOKEN",credentialId);
        System.out.println(JSON.toJSONString(deviceCredentials));

        HttpClientResult responseHttpClientResult = HttpClientUtils.doPostStringParams(tbBaseUrl + "/api/device/credentials", restTemplateUtil.getHeaders(true), JSON.toJSONString(deviceCredentials));
        System.out.println("content-----------"+httpClientResult.getContent()+"------------code------------"+httpClientResult.getCode());

       // ResponseEntity<String> responseEntity = restTemplateUtil.postRestTemplate("/api/device/credentials", deviceCredentials, String.class, true);
        //System.out.println(responseEntity.getBody());
        return ResultUtils.success(responseHttpClientResult.getContent());
    }



    @Override
    public Result findTreeList(){
        List<Org> orgs=orgDao.treeList(0);
        List<ProductDevicesTree> treeList = new ArrayList<>();
        //遍历机构数，查询机构下人数和绑定的生产设备
        for (Org org:orgs) {
            //查询该机构下人数
            List<User> userByOrgIds = userDao.findUserByOrgId(org.getId());
            //查询该机构及下级机构是否有生产设备
            Integer c = productDevicesDao.findProductDevicesCountByOrgId(org.getId());
            org.setStaffNum(userByOrgIds.size());
            ProductDevicesTree tree = new ProductDevicesTree();
            tree.setId(org.getId().toString());
            tree.setParentId(org.getParentId().toString());
            tree.setTitle(org.getName());
            tree.setStaffNum(org.getStaffNum());
            //当c>0说明该机构或其下级机构绑定了生产设备
            if(c>0){
                tree.setComponent("isShow");
            }
            treeList.add(tree);
            treeList=this.productDevicesToTree(treeList,tree.getId());
        }
        List<ProductDevicesTree> treeLi = new ArrayList<ProductDevicesTree>();
        for (ProductDevicesTree tree : treeList) {
            if (tree.getParentId().equals("0")) {
                tree.initChildren();
                treeBuilder(tree, treeList);
                if(!CheckUtils.isNull(tree.getComponent())&&tree.getComponent().equals("isShow")) {
                    treeLi.add(tree);
                }
            }
        }
        return ResultUtils.success(treeLi);
    }
    /**
    *  查询该机构下生产设备
     * @param trees
     * @param orgId
    * @return
    */
    private List<ProductDevicesTree> productDevicesToTree(List<ProductDevicesTree> trees,String orgId) {
        List<ProductDevices> productDevices = productDevicesDao.findProductDevicesByOrgId(orgId,attachmentPath);
        if(productDevices!=null&&productDevices.size()>0){
            for (ProductDevices pd : productDevices) {
                ProductDevicesTree tree = new ProductDevicesTree();
                tree.setComponent("isShow");
                tree.setId(pd.getTbId());
                tree.setParentId(orgId);
                tree.setTitle(pd.getName());
                tree.setName(pd.getName());
                tree.setClassifyName(pd.getClassifyName());
                tree.setClassifySet(pd.getClassifySet());
                tree.setNetDevicesList(pd.getNetDevicesList());
                trees.add(tree);
            }
        }
        return trees;
    }
    //递归组装树机构
    private void treeBuilder(ProductDevicesTree tree, List<ProductDevicesTree> trees) {
        for (ProductDevicesTree child : trees) {
            if (child.getChildren() == null){
                child.initChildren();
            }
            if (tree.getId().equals(child.getParentId())) {
                tree.setHasChildren(true);
                child.setHasParent(true);
                if(!CheckUtils.isNull(child.getComponent())&&child.getComponent().equals("isShow")){
                    treeBuilder(child, trees);
                    tree.getChildren().add(child);
                }
            }
        }
    }

}