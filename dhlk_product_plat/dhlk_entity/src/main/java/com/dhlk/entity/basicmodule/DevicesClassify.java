package com.dhlk.entity.basicmodule;

import com.dhlk.entity.dm.DmClassifyType;
import domain.BaseFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description
 * @Author gchen
 * @Date 2020/3/26
 */
@Data
public class DevicesClassify {
    private String id;
    private String classifyName;//分类名称
    private String describe;//描述
    private String classifyId;//类型
    private Integer factoryId;//工厂
    private List<DevicesClassifyDetail> classifyDetails;//设备类型属性明细
    @ApiModelProperty(hidden = true)
    private List<LinkedHashMap<String,String>> attrSet;
    private Integer nameCount;//参数数量
    private DmClassifyType dmClassifyType;//基础类型type

    private String imagePath;//关联文件
}