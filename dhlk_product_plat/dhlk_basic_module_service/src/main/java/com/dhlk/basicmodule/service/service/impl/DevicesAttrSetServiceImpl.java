package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.DevicesAttrDetailDao;
import com.dhlk.basicmodule.service.dao.DevicesAttrSetDao;
import com.dhlk.basicmodule.service.dao.DevicesClassifyDetailDao;
import com.dhlk.basicmodule.service.service.DevicesAttrSetService;
import com.dhlk.basicmodule.service.service.RedisService;
import com.dhlk.entity.basicmodule.DevicesAttrDetail;
import com.dhlk.entity.basicmodule.DevicesAttrSet;
import domain.Result;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RedisBasicService;
import utils.CheckUtils;
import utils.ResultUtils;

import java.util.List;

/**
* @Author:         gchen
* @CreateDate:     2020/3/30 15:30
*/
@Service
@Transactional
public class DevicesAttrSetServiceImpl implements DevicesAttrSetService {

    @Autowired
    private DevicesAttrSetDao devicesAttrSetDao;
    @Autowired
    private DevicesAttrDetailDao devicesAttrDetailDao;
    @Autowired
    private DevicesClassifyDetailDao devicesClassifyDetailDao;
    @Autowired
    private RedisService redisService;
    @Override
    public Result save(DevicesAttrSet devicesAttrSet) {
        Integer flag = -1;
        if(devicesAttrSetDao.isRepeatAttr(devicesAttrSet) > 0){
            return ResultUtils.error("属性名称已存在");
        }
        if(CheckUtils.isNull(devicesAttrSet.getId())){ //如果集合id为空的话就存储，不为空就修改
            //设置工厂ID
            devicesAttrSet.setFactoryId(redisService.findFactoryId());
            flag = devicesAttrSetDao.insert(devicesAttrSet);
            if(!CheckUtils.isNull(devicesAttrSet.getAttrDetails())){ //判断属性集合中明细是否为空
                flag = devicesAttrDetailDao.insertDevicesAttrDetails(devicesAttrSet.getAttrDetails(),devicesAttrSet.getId());
            }
        }else{
            List<DevicesAttrDetail> attrDetails = devicesAttrSet.getAttrDetails();
            if(!CheckUtils.isNull(attrDetails)){
                //从数据库中取出对应属性集合的属性明细
                List<DevicesAttrDetail> BaseAttrDetails = devicesAttrDetailDao.findAttrDetailsByAttrSetId(devicesAttrSet.getId());
                //页面上删除了属性
                List<DevicesAttrDetail> subtractDelete = ListUtils.subtract(BaseAttrDetails, attrDetails);
                //判断属性明细是否与分类绑定
                if(subtractDelete != null && subtractDelete.size() > 0){
                    if(devicesClassifyDetailDao.findDevicesClassifyDetailByDetail(subtractDelete) > 0){
                        return ResultUtils.error("属性明细已绑定");
                    }
                    flag = devicesAttrDetailDao.delete(subtractDelete);
                }
                //页面上添加了属性
                List<DevicesAttrDetail> subtractSave = ListUtils.subtract(attrDetails, BaseAttrDetails);
                if(!CheckUtils.isNull(subtractSave) && subtractSave.size() > 0){
                    devicesAttrDetailDao.insertDevicesAttrDetails(subtractSave,devicesAttrSet.getId());
                }

                //页面上与数据中的交集
                List<DevicesAttrDetail> retain = ListUtils.retainAll(attrDetails, BaseAttrDetails);
                for (DevicesAttrDetail devicesAttrDetail:retain){ //循环修改属性明细
                    flag = devicesAttrDetailDao.update(devicesAttrDetail);
                }
            }
            flag = devicesAttrSetDao.update(devicesAttrSet);
        }
        return flag>0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(Integer id) {
        Integer flag = -1;
        if(devicesClassifyDetailDao.findDevicesClassifyDetailByAttrSetId(id) > 0){//检查属性集合是否与分类绑定
            return ResultUtils.error("请解除属性集合与分类绑定");
        }
        flag = devicesAttrDetailDao.deleteByAttrSetId(id);//删除属性集合下所有的属性明细
        flag = devicesAttrSetDao.delete(id);//删除属性集合
        return flag > 0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result findList(String name) {
        List<DevicesAttrSet> devicesAttrSets = devicesAttrSetDao.findList(name,redisService.findFactoryId());
        return ResultUtils.success(devicesAttrSets);
    }

}
