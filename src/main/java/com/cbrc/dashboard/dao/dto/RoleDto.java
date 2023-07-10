package com.cbrc.dashboard.dao.dto;

import com.cbrc.dashboard.dao.Entity;
import com.cbrc.dashboard.dao.po.Permission;
import lombok.Data;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.dto
 * @author: herry
 * @date: 2018-05-16  下午4:42
 * @Description: TODO
 */

@Data
public class RoleDto extends Entity {

    private Integer roleId; //角色ID

    private String roleName; //角色名称

    private String roleDesc; //角色描述

    private Integer status; //角色状态

    private String statusDesc; //账户状态描述

    private List<Permission> permissions;// 角色对应的权限信息
}
