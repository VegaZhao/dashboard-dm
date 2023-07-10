package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cbrc.dashboard.dao.Entity;
import lombok.Data;


/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.po
 * @author: herry
 * @date: 2018-05-16  下午1:40
 * @Description: TODO
 */
@Data
@TableName("permission")
public class Permission extends Entity {

    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId; //权限ID

    @TableField(value = "permission_code")
    private String permissionCode; //权限菜单

    @TableField(value = "permission_name")
    private String permissionName; //菜单释义

    @TableField(value = "action_code")
    private String actionCode; //支持的操作，如 新增、删除、更新等，支持通配符

    @TableField(value = "action_name")
    private String actionName; //操作释义

    @TableField(value = "required_permission")
    private Integer requiredPermission; //是否为必须权限，1为是，2为否
}
