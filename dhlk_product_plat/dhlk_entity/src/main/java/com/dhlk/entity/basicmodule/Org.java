package com.dhlk.entity.basicmodule;

import com.dhlk.annotation.NameCheck;
import com.dhlk.annotation.SetNameCheck;
import lombok.Data;

import javax.validation.constraints.Size;
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
    @SetNameCheck(message = "请填写正确的机构名称，2-20位的汉字、数字和字母", maxLength = 20)
    private String name;

    /** 父机构 */
    private Integer parentId;

    /** 状态 0正常 2 删除 */
    private Integer status;

    /** 机构员工数量 */
    private Integer staffNum;
}
