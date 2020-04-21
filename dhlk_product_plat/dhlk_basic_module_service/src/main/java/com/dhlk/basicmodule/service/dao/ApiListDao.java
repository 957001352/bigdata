package com.dhlk.basicmodule.service.dao;

import com.dhlk.entity.api.ApiList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Description 接口管理
 * @Author lpsong
 * @Date 2020/3/11
 */
@Repository
public interface ApiListDao {

    Integer insert(ApiList apiList);

    Integer update(ApiList apiList);

    Integer delete(List<String> ids);

    List<ApiList> findList(Integer classifyId);

    Integer isEnable(@Param("id") Integer id, @Param("status") Integer status);
    /*
     * 判断接口名称是否重复
     * @return
     */
    Integer isRepeatTitle(ApiList apiList);
}