package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.service.PermissionService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import com.cbrc.dashboard.dao.dto.PermissionDto;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.qdbank.dm.controller
 * @author: Herry
 * @Date: 2018/10/8 16:25
 * @Description: TODO
 */
@RestController
@RequestMapping("/dashboard/system/permission-manager")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取权限信息列表", notes = "获取权限信息列表")
    @RequiresPermissions("system:permission_manage:list")
    @GetMapping("permission-list")
    public JSONObject getPermissionList() {
        return WebUtil.result(permissionService.getPermissionList());
    }


    @ApiOperation(value = "分页获取权限信息列表", notes = "分页获取权限信息列表")
    @RequiresPermissions("system:permission_manage:list")
    @PostMapping("permission-list-page")
    public JSONObject getPermissionListWithPage(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params, "startPage,pageSize");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        JSONObject query = params.getJSONObject("query");
        CustomPage<PermissionDto> data = permissionService.getPermissionListWithPage(startPage, pageSize, query);
        return WebUtil.result(data);
    }

    @ApiOperation(value = "新增权限信息", notes = "新增权限信息")
    @RequiresPermissions("system:permission_manage:add")
    @PostMapping("permission-add")
    public JSONObject addPermission(@RequestBody JSONObject info) {
        CommonUtil.hasAllRequired(info, "permissionName,permissionCode,actionName,actionCode");
        Permission p = info.toJavaObject(Permission.class);
        return WebUtil.netStatus2Json(permissionService.addPermission(p));
    }

    @ApiOperation(value = "更新权限信息", notes = "更新权限信息")
    @RequiresPermissions("system:permission_manage:update")
    @PostMapping("permission-update")
    public JSONObject updatePermission(@RequestBody JSONObject info) {
        CommonUtil.hasAllRequired(info, "permissionId,permissionName,permissionCode,actionName,actionCode");
        Permission p = info.toJavaObject(Permission.class);
        return WebUtil.netStatus2Json(permissionService.updatePermission(p));
    }

    @ApiOperation(value = "删除权限信息", notes = "删除权限信息")
    @RequiresPermissions("system:permission_manage:delete")
    @PostMapping("permission-del")
    public JSONObject delPermission(@RequestBody JSONObject info) {
        CommonUtil.hasAllRequired(info, "permissionIds");
        List<Integer> permissionIds = (List<Integer>) info.get("permissionIds");
        Set<Integer> ids = new HashSet<>(permissionIds);
        return WebUtil.netStatus2Json(permissionService.delPermission(ids));
    }
}
