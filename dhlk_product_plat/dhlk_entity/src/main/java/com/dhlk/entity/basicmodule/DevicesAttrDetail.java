package com.dhlk.entity.basicmodule;

import lombok.Data;

/**
 * @Description
 * @Author gchen
 * @Date 2020/3/26
 */
@Data
public class DevicesAttrDetail {
    private Integer id;
    private String attr;//属性名称
    private String dataType;//数据类型
    private Integer dataLength;//数据长度
    private String unit;//单位
    private Integer attrSetId;//属性集合
}