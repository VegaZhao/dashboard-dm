package com.cbrc.dashboard.service;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.dto.PermissionDto;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.enums.NetStatusEnum;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    List<Permission> getPermissionList();

    CustomPage<PermissionDto> getPermissionListWithPage(int startPage, int pageSize, JSONObject query);

    NetStatusEnum addPermission(Permission permission);

    NetStatusEnum delPermission(Set<Integer> permissionIds);

    NetStatusEnum updatePermission(Permission permission);

    List<PermissionDto> getPermissionByRoleIds(Set<Integer> roleIds);
}
