package com.dhlk.entity.basicmodule;

import annotation.AdminIDCheck;
import annotation.NameCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 机构管理
 */
@Data
public class Org implements Serializable {

    /** $column.columnComment */
    private Integer id;

    /** 机构编码 */
    private String code;

    /** 机构名称 */
    @NameCheck(message = "请填写正确的机构名称，2-50位的汉字、数字和字母",maxLength = 50)
    private String name;

    /** 父机构 */
    private Integer parentId;

    /** 状态 0正常 2 删除 */
    private Integer status;

    /** 机构员工数量 */
    private Integer staffNum;
}
