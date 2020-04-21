package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.DataBroker;
import com.dhlk.entity.hive.MetaTable;
import domain.Result;

/**
 * hive元数据管理
 */
public interface MetaTableService {
        /**
         * 新增/修改
         */
        Result save(MetaTable metaTable);

        /**
         * 逻辑删除
         *
         * @param ids
         */
        Result delete(String ids);

        /**
         * 分页查询
         *
         * @param pageNum
         * @param pageSize
         * @return
         */
        Result findPageList(Integer pageNum, Integer pageSize);
}