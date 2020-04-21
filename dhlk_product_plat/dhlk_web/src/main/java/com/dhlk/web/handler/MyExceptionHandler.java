package com.dhlk.web.handler;

import domain.Result;
import enums.ResultEnum;
import exceptions.MyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import utils.Convert;
import utils.ResultUtils;

import java.util.Map;


/**
 * @Description 统一异常处理
 * @Author lpsong
 * @Date 2020/3/31
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public Result myException(MyException e) {
        e.printStackTrace();
        return ResultUtils.error(Convert.stringToInteger(e.getCode()), e.getMessage());
    }
    @ExceptionHandler(value =MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return ResultUtils.error("文件大小超出10MB限制");
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result Exception(Exception e) {
       e.printStackTrace();
        return ResultUtils.error(ResultEnum.UNKNOWN_ERR.getState(), ResultEnum.UNKNOWN_ERR.getStateInfo());
    }

}
