package com.dhlk.basicmodule.service.service;

import domain.Result;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/26
 */
public interface HiveMetaService {

    Result findPageList(String table, Integer pageNum, Integer pageSize);

    Result findColumnList(String table);
}
