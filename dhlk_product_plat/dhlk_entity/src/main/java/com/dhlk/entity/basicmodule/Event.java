package com.dhlk.entity.basicmodule;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 事件对象 dhlk_basic_event
 * 
 * @author jlv
 * @date 2020-04-26
 */
@Data
public class Event implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** null */
    private Long id;

    /** 警告Id */
    private String  alarmId;

    /** 租户id */
    private String tenantid;

    /** 类型 */
    private String type;

    /** 来源 */
    private String originator;

    /** 安全级别 */
    private String severity;

    /** 状态 */
    private String status;

    /** 开始时间 */
    private Long startts;

    /** 结束时间 */
    private Long endts;

    /** 确认时间 */
    private Long ackts;

    /** 清除时间 */
    private Long clearts;

    /** 详情 */
    private String details;

    /** 传播 */
    private Integer propagate;

    /** tbId */
    private String tbid;

    /** 创建时间 */
    private Long createdtime;

    /** 名称 */
    private String name;

}
