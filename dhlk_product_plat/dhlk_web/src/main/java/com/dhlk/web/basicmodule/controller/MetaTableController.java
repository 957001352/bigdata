package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.hive.MetaTable;
import com.dhlk.web.basicmodule.service.MetaTableService;
import com.dhlk.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* hive元数据表管理
*/
@RestController
@RequestMapping(value = "/metaTable")
public class MetaTableController {
    @Autowired
    private MetaTableService metaTableService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@RequestBody MetaTable metaTable) {
        return metaTableService.save(metaTable);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return metaTableService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  metaTableService.findPageList(pageNum, pageSize);
    }
}
