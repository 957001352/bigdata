package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.EmailCheck;
import com.dhlk.annotation.LoginNameCheck;
import com.dhlk.annotation.MobizeCheck;
import com.dhlk.annotation.NameCheck;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户管理
 */
@Data
public class User implements Serializable {

    private String orgId;//机构id
    private String roleIds;//角色id集合，逗号隔开
    private String group;//用户分组条件

    /** $column.columnComment */
    private Integer id;

    /** 姓名 */
    @NameCheck
    private String name;

    /** 用户名 默认长度4~20 */
    @LoginNameCheck(maxLength = 20,minLength = 3)
    private String loginName;

    private String password;

    /** 状态  0正常 1禁用 */
    private Integer status;

    /** 是否超级用户 */
    private Integer isAdmin;

    /** 创建时间 */
    private Date createTime;

    /** 电话 */
    @MobizeCheck
    private String phone;

    /** 邮箱 */
    @EmailCheck
    private String email;

    /** 用户关联机构最顶级 */
    private Org factory;

}


