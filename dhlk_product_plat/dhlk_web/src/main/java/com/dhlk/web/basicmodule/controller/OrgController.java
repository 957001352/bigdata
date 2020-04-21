package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.Org;
import com.dhlk.web.basicmodule.service.OrgService;
import domain.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.ResultUtils;

import javax.validation.Valid;


/**
* 厂区管理
*/
@RestController
@RequestMapping(value = "/org")
@Api(description = "厂区管理")
public class OrgController {
    @Autowired
    private OrgService orgService;

    /**
     * 添加、修改厂区
     * @param org
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody Org org, BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if(result == null){
            return orgService.save(org);
        }
        return result;
    }
    /**
     * 删除厂区
     * @param id
     * @return result
     */
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "id") Integer id) {
        return orgService.delete(id);
    }

    /**
     * 查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/findPageList")
    public Result findPageList(@RequestParam(value = "parentId",required = false,defaultValue = "0") Integer parentId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgService.findPageList(parentId,pageNum, pageSize);
    }

    /**
     * 机构树查询
     * @param
     * @return
     */
    @GetMapping("/findTreeList")
    public Result findTreeList() {
        return  orgService.findTreeList();
    }

    /**
     * 查询机构下所有的用户
     * @param
     * @return
     */
    @GetMapping("/findPageUserByOrg")
    public Result findPageUserByOrg(@RequestParam(value = "orgId") Integer orgId,
                                @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  orgService.findPageUserByOrgId(orgId,pageNum,pageSize);
    }

}
