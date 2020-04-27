package com.dhlk.hadoop.controller;

import com.dhlk.hadoop.utils.HadoopUtils;
import com.dhlk.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description hadoop文件管理
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
     *
     * @return
     */
    @GetMapping("/listFile")
    public Result listFile(@RequestParam(value = "filePath", required = false) String filePath,
                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return hadoopUtil.fileList(filePath, pageNum, pageSize);
    }

    /**
     * @param response
     * @return domain.Result
     * @date 2020/4/17 15:28
     * @author jzhao
     * @description 下载文件
     */
    @GetMapping("/downFile")
    public Result downFile(@RequestParam(value = "filePath", required = true) String filePath, HttpServletResponse response) {
        return hadoopUtil.downHdfsFile(filePath, response);
    }
}