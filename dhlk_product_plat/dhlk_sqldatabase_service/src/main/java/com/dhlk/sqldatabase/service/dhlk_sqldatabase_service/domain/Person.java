package com.dhlk.sqldatabase.service.dhlk_sqldatabase_service.domain;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * Content:
 * Author:jpdong
 * Date:2020/3/4
 */
@Data
public class Person {
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
