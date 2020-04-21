package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 生产设备管理
 */
@Data
public class ProductDevices implements Serializable {
    private Integer id;
    private String name;//设备名称
    private Integer orgId;//车间id
    private Integer factoryId;//集团id
    private String classifyId;//类型管理
    @ApiModelProperty(hidden = true)
    private String classifyName;//类型管理
    @ApiModelProperty(hidden = true)
    private Integer status;// 状态  0正常 1禁用 2 删除
    @ApiModelProperty(hidden = true)
    private String createTime;//创建时间
    private String note;//设备描述
    private String tbId;//tb设备表id
    private String credentials;//tb设备凭证
    @ApiModelProperty(hidden = true)
    private List<LinkedHashMap<String,String>> attrSet;
    @ApiModelProperty(hidden = true)
    private LinkedHashMap<String,String> classifySet;
}
