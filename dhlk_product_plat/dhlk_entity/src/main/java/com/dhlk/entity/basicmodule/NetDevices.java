package com.dhlk.entity.basicmodule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 网络设备管理
 */
@Data
public class NetDevices implements Serializable {
    private Integer id;
    private String name;//设备名称
    private String ip;//设备ip
    private String gateway;//默认网关
    private String mask;//子网掩码
    private String license;//SN码
    private Integer typeId;//设备类型  1大数据一体机 2 BI控制器
    @ApiModelProperty(hidden = true)
    private String typeName;
    @ApiModelProperty(hidden = true)
    private Integer factoryId;
    @ApiModelProperty(hidden = true)
    private Integer status;// 状态  0正常 1禁用
    private String note;//设备描述
    @ApiModelProperty(hidden = true)
    private String createTime;//创建时间
    private String tbId;//tb设备表id
    private String credentials;//tb设备凭证
    private List<NetDevicesSoft> softList;//软件列表


    @ApiModelProperty(hidden = true)
    private List<ProductDevices> productDevicesList;//生产设备列表

    @ApiModelProperty(hidden = true)
    private List<NetDevices> netDevicesList;//网络设备列表
}
