package com.dhlk.web.hdfs.service;

import com.dhlk.web.basicmodule.service.fbk.SysLogServiceFbk;
import com.dhlk.web.hdfs.service.fbk.HadoopServiceFbk;
import domain.Result;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: jzhao
 * @Date: 2020/4/21 09:37
 * @Description:
 */
@FeignClient(value = "dhlk-hadoop/hadoop")
public interface HadoopService {
    @GetMapping(value = "/listFile")
    Result listFile(@RequestParam(value = "filePath", required = false) String filePath,
                    @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize);

}
