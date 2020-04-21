package com.dhlk.entity.basicmodule;

import lombok.Data;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/26
 */
@Data
public class DevicesClassifyDetail {
    private Integer id;
    private Integer attrSetId;//属性集合
    private String attrSubName;//属性简称
    private Integer attrDetailId;//属性明细
    private String devicesClassifyId;//设备分类
}