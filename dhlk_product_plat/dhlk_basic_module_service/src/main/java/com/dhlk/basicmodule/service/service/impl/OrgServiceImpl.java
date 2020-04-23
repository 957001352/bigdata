package com.dhlk.basicmodule.service.service.impl;

import com.dhlk.basicmodule.service.dao.OrgDao;
import com.dhlk.basicmodule.service.dao.UserDao;
import com.dhlk.basicmodule.service.service.LoginService;
import com.dhlk.basicmodule.service.service.OrgService;
import domain.Tree;
import utils.TreeUtil;
import com.dhlk.entity.basicmodule.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import domain.Result;
import com.dhlk.entity.basicmodule.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.CheckUtils;
import utils.ResultUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrgServiceImpl implements OrgService {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserDao userDao;
    @Override
    public Result save(Org org) {
        Integer flag = -1;
        if(orgDao.isRepeatName(org) > 0){
            return ResultUtils.error("存在同级相同名称部门");
        }
        if(CheckUtils.isNull(org.getId())){
            flag = orgDao.insert(org);
        }else{
            flag = orgDao.update(org);
        }
        return flag>0? ResultUtils.success():ResultUtils.failure();
    }

    @Override
    public Result delete(Integer id) {
        if(!CheckUtils.checkId(id)){
            return ResultUtils.error("id输入错误");
        }
        List<Org> underOrgs = orgDao.findUnderOrgById(id);
        for (Org or:underOrgs) {
            List<User> userByOrgId = userDao.findUserByOrgId(or.getId());
            if(userByOrgId.size() > 0){
                return ResultUtils.error("请先删除机构下的所有用户");
            }
        }

        if(orgDao.delete(underOrgs) > 0){
            return ResultUtils.success();
        }
        return ResultUtils.failure();
    }

    @Override
    public Result findPageList(Integer parentId, Integer pageNum, Integer pageSize) {
        if(!CheckUtils.checkId(parentId)){
            return ResultUtils.error("parentId输入错误");
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Org> pageInfo = new PageInfo<>(orgDao.findList(parentId));
        return ResultUtils.success(pageInfo);
    }

    @Override
    public Result findTreeList() {
        List<Org> orgs = orgDao.treeList(0);
        for (Org org:orgs) {
            List<User> userByOrgIds = userDao.findUserByOrgId(org.getId());
            org.setStaffNum(userByOrgIds.size());
        }
        List<Tree<Org>> trees = new ArrayList<>();
        buildTrees(trees, orgs);
        Tree<Org> orgTree = TreeUtil.build(trees);
        return ResultUtils.success(orgTree);
    }

    @Override
    public Result findPageUserByOrgId(Integer orgId,Integer pageNum,Integer pageSize) {
        if(!CheckUtils.checkId(orgId)){
            return ResultUtils.error("orgId输入错误");
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<User> pageInfo = new PageInfo<>(userDao.findUserByOrgId(orgId));
        return ResultUtils.success(pageInfo);
    }


    private void buildTrees(List<Tree<Org>> trees, List<Org> orgs) {
        orgs.forEach(org -> {
            Tree<Org> tree = new Tree<>();
            tree.setId(org.getId().toString());
            tree.setParentId(org.getParentId().toString());
            tree.setTitle(org.getName());
            tree.setStaffNum(org.getStaffNum());
            trees.add(tree);
        });
    }
}
