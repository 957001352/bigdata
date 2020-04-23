package com.dhlk.basicmodule.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhlk.basicmodule.service.service.AttachmentService;
import domain.BaseFile;
import domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.*;

import java.util.List;

/**
 * @Description
 * @Author lpsong
 * @Date 2020/3/31
 */
@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

    @Value("${attachment.path}")
    private String attachmentPath;

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 附件上传
     * @param file
     * @param isAdd
     * @param dataId
     * @return
     */
    @PostMapping(value = "/upload",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result upload(@RequestPart(value = "file") MultipartFile file,
                         @RequestParam(value = "isAdd") boolean isAdd,
                         @RequestParam(value = "dataId",required = false) String dataId) throws Exception {
        // 上传附件
        FileUpDownUtils fileUpload=new FileUpDownUtils(attachmentPath+DateUtils.getToday(),file);
        Result result= fileUpload.execUploadFile(file,dataId);
        // 上传成功，保存附件记录
        if (0 == result.getCode()) {
            BaseFile baseFile = (BaseFile)result.getData();
            //删除之前上传的附件记录和文件，只留最新一条
            if(!isAdd&& !CheckUtils.isNull(dataId)){
                //删除文件
                List<BaseFile> fileList=attachmentService.findByDataId(dataId);
                for(BaseFile f:fileList){
                    FileUtils.getInstance().deleteFile(f.getPath());
                }
                //删除上传记录
                attachmentService.deleteByDataId(dataId);
            }
            // 保存
            Result recordResult = attachmentService.saveRecord(baseFile);
            if(0 == recordResult.getCode()){
                baseFile.setWebPath(baseFile.getPath().replace(attachmentPath,"\\attach\\"));
                return ResultUtils.success(result.getData());
            }
        }
        return ResultUtils.failure();
    }

    /**
     * 保存附件记录
     * @param baseFile
     * @return
     */
    @PostMapping(value = "/saveRecord")
    public Result saveRecord(@RequestBody BaseFile baseFile){
        return attachmentService.saveRecord(baseFile);
    }

    /**
     * 根据关联数据查询附件
     * @param dataId
     * @return
     */
    @GetMapping(value = "/findByDataId")
    public List<BaseFile> findByDataId(@RequestParam(value = "dataId") String dataId){
        return attachmentService.findByDataId(dataId);
    }

    /**
     * 根据附件ID查询附件
     * @param id
     * @return
     */
    @GetMapping(value = "/findById")
    public BaseFile findById(@RequestParam(value = "id") Integer id){
        return attachmentService.findById(id);
    }

    /**
     * 根据附件ID删除附件
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteById")
    public Result deleteById(@RequestParam(value = "id") Integer id){
        return attachmentService.deleteById(id);
    }

    /**
     * 根据关联数据删除附件
     * @param dataId
     * @return
     */
    @DeleteMapping(value = "/deleteByDataId")
    public Result  deleteByDataId(@RequestParam(value = "dataId") String dataId){
        return attachmentService.deleteByDataId(dataId);
    }

    @PostMapping(value = "/reviceMess")
    public void reviceMess(@RequestBody JSONObject mess){

        System.out.println(mess);
    }
}
