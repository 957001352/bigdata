package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * 软件网络设备关系管理
 */
@Data
public class NetDevicesSoft implements Serializable {
    private Integer id;
    private String name;//软件名称
    private String version;//版本
    private String url;//云端地址
    private String port;//端口
    private Integer netDeviceId;//网络设备

}
