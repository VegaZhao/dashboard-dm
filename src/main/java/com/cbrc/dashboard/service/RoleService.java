package com.cbrc.dashboard.service;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.dto.RoleDto;
import com.cbrc.dashboard.dao.po.Role;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.enums.NetStatusEnum;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.service
 * @author: herry
 * @date: 2018-05-16  下午2:09
 * @Description: TODO
 */

public interface RoleService {

    List<RoleDto> getRoleByUserId(Integer userId);

    List<RoleDto> getRoleList();

    CustomPage<RoleDto> getRoleListWithPage(int startPage, int pageSize, JSONObject query);

    NetStatusEnum addRoleWithPermissionIds(Role role, List<Integer> permissionIds);

    NetStatusEnum updateRoleWithPermissionIds(Role role, List<Integer> permissionIds);

    NetStatusEnum delRoleWithRoleIds(List<Integer> roleIds);
}
