package com.dhlk.basicmodule.service.service;

import domain.Result;
import com.dhlk.entity.api.ApiClassify;
import com.dhlk.entity.api.ApiList;

/**
 * API接口管理
 */
public interface ApiListService {
    /**
     * 新增/修改
     * 判断接口名称是否重复
     */
    Result save(ApiList apiList);
    /**
     * 物理删除
     * @param ids
     */
    Result delete(String ids);
    /**
     * 分页查询
     * @param classifyId 接口分类
     * @param pageNum
     * @param pageSize
     */
    Result findPageList(Integer classifyId, Integer pageNum, Integer pageSize);

    /**
     * 接口启用禁用
     * @param id 主键
     * @param status 0启用 1禁用
     */
    Result isEnable(Integer id,Integer status);
}
