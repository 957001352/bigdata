package com.dhlk.hadoop.controller;

import com.dhlk.hadoop.utils.HadoopUtils;
import domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultUtils;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/4/15
 */
@RestController
@RequestMapping(value = "/hadoop")
public class HadoopController {
    @Autowired
    private HadoopUtils hadoopUtil;
    /**
     * 读取文件列表
     * @return
     * @throws Exception
     */
    @GetMapping("/listFile")
    public Result listFile() throws Exception {
        System.out.println("---------------------");
        List<Map<String, String>> returnList = hadoopUtil.listFile("/data/2020-04-15");
        return ResultUtils.success(returnList);
    }

}