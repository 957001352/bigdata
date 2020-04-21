package com.dhlk.web.basicmodule.controller;

import com.dhlk.entity.basicmodule.User;
import com.dhlk.web.basicmodule.service.UserService;
import domain.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import utils.ResultUtils;

import javax.validation.Valid;


/**
* 用户管理
*/
@RestController
@RequestMapping(value = "/user")
@Api(description = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 保存
     * @param
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody User user,BindingResult bindingResult) {
        Result result = ResultUtils.loadResult(bindingResult);
        if (result==null) {
            return userService.save(user);
        }
        return result;
    }
    /**
     * 删除
     * @param ids
     * @return result
     */
    @GetMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") String ids) {
        return userService.delete(ids);
    }

    /**
    * 列表查询
     * @param name name为模糊查询，name如果没有或者为空就是全表扫描
    * @return result
    */
    @GetMapping("/findList")
    public Result findList(@RequestParam(value = "name", required = false) String name) {
        return  userService.findList(name);
    }
    /**
     * 修改密码
     * @param id
     * @param password
     * @return result
     */
    @GetMapping("/updatePassword")
    public Result updatePassword(@RequestParam(value = "id") Integer id,
                            @RequestParam(value = "password") String password) {
        return  userService.updatePassword(id,password);
    }

    /**
     * 查询用户所属的机构
     * @param id
     * @return result
     */
    @GetMapping("/findOrg")
    public Result updatePassword(@RequestParam(value = "id") Integer id) {
        return  userService.findOrg(id);
    }

    /**
     * 改变用户状态
     * @param id
     * @return result
     */
    @GetMapping("/isEnable")
    public Result isEnable(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "status") Integer status) {
        return  userService.isEnable(id,status);
    }
}
