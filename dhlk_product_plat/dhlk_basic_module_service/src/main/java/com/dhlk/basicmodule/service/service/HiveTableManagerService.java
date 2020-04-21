package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.hive.MetaTable;
import domain.Result;

/**
 * hive数据表管理
 */
public interface HiveTableManagerService {
        /**
         * 新增表
         */
        Result createTable(Boolean conver);

        /**
         * 删除表
         */
        Result dropTable();

        Result findTableList();

}