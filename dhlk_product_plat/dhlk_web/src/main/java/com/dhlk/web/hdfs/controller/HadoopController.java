package com.dhlk.web.hdfs.controller;

import com.dhlk.web.hdfs.service.HadoopService;
import domain.Result;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.FileUtils;
import utils.ResultUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: jzhao
 * @Date: 2020/4/21 09:36
 * @Description:
 */
@RestController
@RequestMapping(value = "/hadoop")
@Api(description = "hadoop文件管理")
public class HadoopController {
    @Autowired
    private HadoopService hadoopService;

    /**
     * @param filePath
     * @return domain.Result
     * @date 2020/4/21 10:01
     * @author jzhao
     * @description 读取文件列表
     */
    @GetMapping("/listFile")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "filePath", value = "文件目录", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页显示数量", required = false, dataType = "Integer")
    })
    public Result listFile(@RequestParam(value = "filePath", required = false) String filePath,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return hadoopService.listFile(filePath, pageNum, pageSize);
    }

}
