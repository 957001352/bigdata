package com.dhlk.entity.basicmodule;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色管理
 */
@Data
public class Role implements Serializable {

    private String menuIds;//菜单id集合，逗号隔开

    /** null */
    private Integer id;

    /** 名称 */
    private String name;

    /** 备注 */
    private String note;
}
