package com.dhlk.basicmodule.service.controller;

import com.dhlk.basicmodule.service.service.ApiClassifyService;
import com.dhlk.entity.api.ApiClassify;
import domain.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.FileUpDownUtils;
import utils.ResultUtils;


/**
* 接口分类管理
*/
@RestController
@RequestMapping(value = "/apiClassify")
public class ApiClassifyController {
    @Autowired
    private ApiClassifyService apiClassifyService;

    @Value("${attachment.path}")
    private String attachmentPath;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    @RequiresPermissions("apiClassify:save")
    public Result save(@RequestBody ApiClassify apiClassify) {
        return apiClassifyService.save(apiClassify);
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    @RequiresPermissions("apiClassify:delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return apiClassifyService.delete(ids);
    }

    /**
    * 列表查询
     * @param pageNum
     * @param pageSize
    * @return
    */
    @GetMapping("/findPageList")
    @RequiresPermissions("apiClassify:findPageList")
    public Result findPageList(@RequestParam(value = "parentId", required = false) Integer parentId,
                               @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return  apiClassifyService.findPageList(parentId,pageNum, pageSize);
    }
    /**
     * 分类树列表查询
     * @return
     */
    @GetMapping("/findTreeList")
    @RequiresPermissions("apiClassify:findTreeList")
    public Result findTreeList() {
        return  apiClassifyService.findTreeList();
    }



    @PostMapping(value = "/importExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result importExcel(@RequestParam(value = "file") MultipartFile file) {
        FileUpDownUtils fileUpload=new FileUpDownUtils(attachmentPath,file);
        fileUpload.execUploadFile(file,null);
        System.out.println("===============>"+file.getOriginalFilename());
        return ResultUtils.success();
    }
}
