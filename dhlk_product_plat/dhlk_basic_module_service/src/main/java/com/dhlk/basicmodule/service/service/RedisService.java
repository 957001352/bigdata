package com.dhlk.basicmodule.service.service;

import com.dhlk.entity.basicmodule.User;
import service.RedisBasicService;

public interface RedisService extends RedisBasicService {
    /*
     * 获取当前用户
     * @param
     * @return
     */
    User currentUser();
    /*
     * 获取当前用户所在集团ID
     * @param
     * @return
     */
    Integer findFactoryId();
}
