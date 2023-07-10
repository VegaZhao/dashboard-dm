package com.cbrc.dashboard.dao.dto;

import lombok.Data;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.dto
 * @author: herry
 * @date: 2018-05-16  下午4:43
 * @Description: TODO
 */
@Data
public class PermissionDto {

    private Integer permissionId; //权限ID

    private String permissionName; //权限菜单

    private String permissionCode; //菜单释义

    private String actionCode; //支持的操作，如 新增、删除、更新等，支持通配符

    private String actionName; //操作释义

    private Integer requiredPermission; //是否为必须权限，1为是，2为否
}
