package com.dhlk.web.hdfs.service.fbk;

import com.dhlk.web.hdfs.service.HadoopService;
import domain.Result;
import enums.ResultEnum;
import feign.Response;
import org.springframework.stereotype.Service;
import utils.ResultUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: jzhao
 * @Date: 2020/4/21 09:37
 * @Description:
 */
@Service
public class HadoopServiceFbk implements HadoopService {

    @Override
    public Result listFile(String filePath, Integer pageNum, Integer pageSize) {
        return ResultUtils.error(ResultEnum.NETWORK_ERR);
    }
}
