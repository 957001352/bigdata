package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.SetNameCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/26
 */
@Data
public class DevicesAttrSet {
    private  Integer id;
    @SetNameCheck(message = "请填写正确的参数名称")
    private  String name;//名称
    private  String describe;//描述
    @ApiModelProperty(hidden = true)
    private  Integer factoryId;//工程id
    private  String attrSetId;//别名
    private  List<DevicesAttrDetail> attrDetails;//属性明细
}