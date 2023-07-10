package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.po.Role;
import com.cbrc.dashboard.service.RoleService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.qdbank.dm.controller
 * @author: Herry
 * @Date: 2018/9/25 21:15
 * @Description: TODO
 */

@RestController
@RequestMapping("/dashboard/system/role-manager")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取角色信息列表", notes = "获取角色信息列表")
    @RequiresPermissions("system:role_manage:list")
    @GetMapping("role-list")
    public JSONObject getRoleList() {
        return WebUtil.result(roleService.getRoleList());
    }

    @ApiOperation(value = "分页获取角色信息列表", notes = "分页获取角色信息列表")
    @RequiresPermissions("system:role_manage:list")
    @PostMapping("role-list-page")
    public JSONObject getRoleList(@RequestBody JSONObject params) {
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        JSONObject query = params.getJSONObject("query");

        return WebUtil.result(roleService.getRoleListWithPage(startPage, pageSize, query));
    }

    @ApiOperation(value = "增加角色信息", notes = "增加角色信息")
    @RequiresPermissions("system:role_manage:add")
    @PostMapping("role-add")
    public JSONObject addRole(@RequestBody JSONObject info) {
        CommonUtil.hasAllRequired(info, "roleName,roleDesc,status,permissions");
        Role r = info.toJavaObject(Role.class);
        List<Integer> permissionIds = (List<Integer>) info.get("permissions");
        return WebUtil.netStatus2Json(roleService.addRoleWithPermissionIds(r, permissionIds));
    }

    @ApiOperation(value = "更新角色信息", notes = "更新角色信息")
    @RequiresPermissions("system:role_manage:update")
    @PostMapping("role-update")
    public JSONObject updateRole(@RequestBody JSONObject info) {
        CommonUtil.hasAllRequired(info, "roleId,roleName,roleDesc,status,permissions");
        Role r = info.toJavaObject(Role.class);
        List<Integer> permissionIds = (List<Integer>) info.get("permissions");
        return WebUtil.netStatus2Json(roleService.updateRoleWithPermissionIds(r, permissionIds));
    }

    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @RequiresPermissions("system:role_manage:delete")
    @PostMapping("role-del")
    public JSONObject delRole(@RequestBody JSONObject roleIds) {
        CommonUtil.hasAllRequired(roleIds, "roleIds");
        List<Integer> ids = (List<Integer>) roleIds.get("roleIds");
        return WebUtil.netStatus2Json(roleService.delRoleWithRoleIds(ids));
    }
}
