package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.ApiListDao;
import com.dhlk.basicmodule.service.service.ApiListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.dhlk.domain.Result;
import com.dhlk.entity.api.ApiList;
import com.dhlk.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dhlk.utils.CheckUtils;
import com.dhlk.utils.ResultUtils;

import java.util.Arrays;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/13
 */
@Service
public class ApiListServiceImpl implements ApiListService {
    @Autowired
    private ApiListDao apiListDao;
    @Override
    public Result save(ApiList apiList) throws MyException {
        if(CheckUtils.isNull(apiList.getTitle())){
            return  ResultUtils.error("接口名称不能为空");
        }
        if(apiListDao.isRepeatTitle(apiList)>0){
           return  ResultUtils.error("接口名称重复");
        }
        //新增
        Integer flag=0;
        if (CheckUtils.isNull(apiList.getId())) {
            flag=apiListDao.insert(apiList);
        }else{//修改
            flag=apiListDao.update(apiList);
        }
        return flag > 0 ? ResultUtils.success() : ResultUtils.failure();
    }

    @Override
    public Result delete(String ids) throws MyException {
        if (!CheckUtils.isNull(ids)) {
            if (apiListDao.delete(Arrays.asList(ids.split(","))) > 0) {
                return  ResultUtils.success();
            }
        }

        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer classifyId, Integer pageNum, Integer pageSize) throws MyException {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ApiList> pageInfo = new PageInfo<ApiList>(apiListDao.findList(classifyId));
        return ResultUtils.success(pageInfo);
    }

    @Override
    public Result isEnable(Integer id, Integer status) throws MyException {
        if (apiListDao.isEnable(id,status) > 0) {
            return  ResultUtils.success();
        }
        return ResultUtils.failure();
    }


}