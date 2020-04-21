package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.MenuDao;
import com.dhlk.basicmodule.service.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import domain.Result;
import com.dhlk.entity.basicmodule.Menu;
import domain.Tree;
import enums.ResultEnum;
import enums.SystemEnums;
import exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.CheckUtils;
import utils.ResultUtils;
import utils.TreeUtil;

import java.util.*;
import java.util.stream.Collectors;
/*
 * Content: 菜单管理
 * Author:jlv
 * Date:2020/3/26
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public Result save(Menu menu) {
        if(menu==null){
            return ResultUtils.error("菜单不能为空");
        }
        Integer res = null;
        Menu tempMenu = menuDao.selectMenuByCode(menu.getCode());
        if (!CheckUtils.isNull(menu.getId())) {
            //菜单id存在,则更新菜单
            if (tempMenu!=null && tempMenu.getId()==menu.getId()) {
                res = menuDao.update(menu);
                return res > 0 ? ResultUtils.success() : ResultUtils.failure();
            } else  return ResultUtils.error("菜单已存在");
        } else {
            if (tempMenu==null) {

                //菜单id不存在,则保存菜单
                res = menuDao.insert(menu);
                return res > 0 ? ResultUtils.success() : ResultUtils.failure();
            } else return ResultUtils.error("菜单已存在");
        }
    }
    //condition 参数就是执行Cacheable的条件
    //@Caching(evict={@CacheEvict(value = "common", key="'MenuDelete_'+#ids.split(',')",condition="#blog!=null")})
    @Override
    public Result delete(String ids) {
        if(CheckUtils.isNull(ids)){
            return ResultUtils.error("参数不能为空");
        }
        List<String> list=Arrays.asList(ids.split(","));
        Integer res = menuDao.deleteMenuByIds(list);
        return res > 0 ? ResultUtils.success() : ResultUtils.failure();
    }
    //unless 参数就是不执行Cacheable的条件
    //@Cacheable(value = "common", key = "'MenuId_'+#id", unless = "#id == null")
    @Override
    public Menu selectMenuById(Integer id) {
        if(!CheckUtils.checkId(id) || id<1){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }


        return menuDao.selectMenuById(id);
    }

    @Override
    public Menu selectMenuByCode(String code) {
        if(!CheckUtils.isNull(code)){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return menuDao.selectMenuByCode(code);
    }
    //condition 参数就是执行Cacheable的条件
  /*  @Caching(evict={@CacheEvict(value = "common", key="'MenuParentId_'+#id",condition="#id!=null")
            , @CacheEvict(value = "common", key="'MenuPageNum_'+#pageNum",condition="#pageNum!=null")
            , @CacheEvict(value = "common", key="'MenuPageSize_'+#pageSize",condition="#pageSize!=null")})*/
    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        if( !CheckUtils.checkId(pageNum) || !CheckUtils.checkId(pageSize)){
            return ResultUtils.error("参数错误");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Menu> menus = menuDao.findList(parentId,null,null,null,0);
        PageInfo<Menu> menusInfo = new PageInfo<>(menus);
        return new Result(SystemEnums.SUCESS, menusInfo);
    }

    @Override
    public Result isEnable(Integer id, Integer status) {
        if(!CheckUtils.checkId(id) || id<1 || !CheckUtils.checkStatus(status)){
            return ResultUtils.error("参数错误");
        }
        Menu menu=new Menu();
        menu.setId(id);
        menu.setStatus(status);
        Integer res=menuDao.update(menu);
        return new Result(SystemEnums.SUCESS, res);
    }

    @Override
    public boolean isRepeatCode(Menu menu)  {
        if(!CheckUtils.isNull(menu)){
            new MyException(SystemEnums.PARMS_ILLEGAL);
        }
        return CheckUtils.isNull(menuDao.selectMenuByCode(menu.getCode()));
    }
    //@Cacheable(value = "common", key = "MenuFindTreeList")
    @Override
    public Result findTreeList() {
        List<Menu> list = menuDao.findList(null,null,null,null,0);
        if(list.isEmpty()){
            return ResultUtils.error("没有查询到菜单树");
        }
        List<Menu> treeLi =treeBuilder(list);
        return ResultUtils.success(treeLi);
    }

    @Override
    public Result findTreeByUserId(Integer userId) {
        if(!CheckUtils.checkId(userId) || userId<1){
            return ResultUtils.error("id参数错误");
        }
        List<Menu> list = menuDao.findTreeByUserId(userId);
        if(list.isEmpty()){
            return ResultUtils.error("没有查询到菜单树");
        }
        List<Menu> treeLi=treeBuilder(list);
        return ResultUtils.success(treeLi);
    }

    @Override
    public Result getMenuCheckedListByRoleId(Integer roleId) {
        if(!CheckUtils.checkId(roleId) || roleId<1){
            return ResultUtils.error(ResultEnum.PARAM_ERR);
        }
        // 根据角色roleId过滤 获取菜单列表
        List<Menu> list= menuDao.getMenuCheckedList(roleId);
        //过滤选中的菜单id
        List<String> ids = list.stream().filter(menu -> menu.getChecked()).map(menu -> menu.getId().toString()).collect(Collectors.toList());
        //构建菜单树
        List<Tree<Menu>> trees = new ArrayList<>();
        buildTrees(trees, list);
        Tree<Menu> menuTree = TreeUtil.build(trees);
        //封装结果集并返回前端
        Map<String,Object> result=new HashMap<String,Object>();
        result.put("total",list.size());
        result.put("ids",ids);
        result.put("rows",menuTree);
        return ResultUtils.success(result);
    }

    /**
     *适配转换1
     * @param list
     */
    private List<Menu> treeBuilder(List<Menu> list) {
        List<Menu> treeLi = new ArrayList<Menu>();
        for (Menu pojo : list) {
            if (pojo.getParentId().intValue() == 0) {
                treeBuilder(pojo, list);
                treeLi.add(pojo);
            }
        }
        return treeLi;
    }
    /**
     *  适配转换2
     * @param menu
     * @param list
     */
    private void treeBuilder(Menu menu, List<Menu> list) {
        for (Menu child : list) {
            if (menu.getId().intValue() == child.getParentId()) {
                treeBuilder(child, list);
                menu.getChildList().add(child);
            }
        }
    }
   private void buildTrees(List<Tree<Menu>> trees, List<Menu> menus) {
       menus.forEach(menu->{
           Tree<Menu> tree = new Tree<>();
           tree.setId(menu.getId().toString());
           tree.setParentId(menu.getParentId().toString());
           tree.setTitle(menu.getName());
           tree.setChecked(menu.getChecked());
           trees.add(tree);
       });
   }
}
