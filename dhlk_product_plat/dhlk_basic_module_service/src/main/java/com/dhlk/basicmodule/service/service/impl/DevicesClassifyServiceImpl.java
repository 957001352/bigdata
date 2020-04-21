package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DevicesClassifyDao;
import com.dhlk.basicmodule.service.dao.DevicesClassifyDetailDao;
import com.dhlk.basicmodule.service.dao.ProductDevicesDao;
import com.dhlk.basicmodule.service.service.DevicesClassifyService;
import com.dhlk.basicmodule.service.service.RedisService;
import com.dhlk.entity.basicmodule.DevicesClassify;
import com.dhlk.entity.basicmodule.DevicesClassifyDetail;
import com.dhlk.entity.basicmodule.ProductDevices;
import domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RedisBasicService;
import utils.CheckUtils;
import utils.ResultUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
* @Author:         gchen
* @CreateDate:     2020/3/31 10:19
*/
@Service
@Transactional
public class DevicesClassifyServiceImpl implements DevicesClassifyService {
    @Autowired
    private DevicesClassifyDao devicesClassifyDao;
    @Autowired
    private DevicesClassifyDetailDao devicesClassifyDetailDao;
    @Autowired
    private ProductDevicesDao productDevicesDao;
    @Autowired
    private RedisService redisService;
    @Value("${attachment.path}")
    private String attachmentPath;
    @Override
    public Result save(DevicesClassify devicesClassify) {
        if(CheckUtils.isNull(devicesClassify.getClassifyName())){
            return ResultUtils.error("分类名称不能为空");
        }
        Integer flag = -1;
        if(devicesClassifyDao.isRepeatName(devicesClassify)>0){
            return ResultUtils.error("分类名称不能重复");
        }
        if(devicesClassify.getId() == null){
            return ResultUtils.error("请上传文件获取ID");
        }
        if(devicesClassifyDao.findDevicesClassifyById(devicesClassify.getId()) == null){ //如果分类id为空的话就存储，不为空就修改
            //设置工厂ID
            devicesClassify.setFactoryId(redisService.findFactoryId());
            if(devicesClassify.getClassifyDetails() != null && devicesClassify.getClassifyDetails().size() > 0){ //判断分类中分类属性明细是否为空
                flag = devicesClassifyDetailDao.insertDevicesClassifyDetails(devicesClassify.getClassifyDetails(),devicesClassify.getId());
            }
            flag = devicesClassifyDao.insert(devicesClassify);
        }else{
            if(devicesClassify.getClassifyDetails() != null && devicesClassify.getClassifyDetails().size() > 0){ //判断分类中分类属性明细是否为空
                flag = devicesClassifyDetailDao.deleteByDevicesClassifyId(devicesClassify.getId());
                flag = devicesClassifyDetailDao.insertDevicesClassifyDetails(devicesClassify.getClassifyDetails(),devicesClassify.getId());
            }
            flag = devicesClassifyDao.update(devicesClassify);
        }
        return flag>0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(String id) {
        Integer flag = -1;
        if(productDevicesDao.isBoundClassify(id) > 0){//检查设备类型是否与设备绑定
            return ResultUtils.error("请解除设备类型绑定");
        }
        flag = devicesClassifyDetailDao.deleteByDevicesClassifyId(id);//删除属性集合下所有的属性明细
        flag = devicesClassifyDao.delete(id);//删除设备分类
        return flag > 0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result findList(String classifyName) {
        List<DevicesClassify> list = devicesClassifyDao.findList(classifyName,redisService.findFactoryId(),attachmentPath);
        list.stream().forEach(devicesClassify -> devicesClassify.setNameCount(devicesClassify.getAttrSet().size()));
        return ResultUtils.success(list);
    }
}
