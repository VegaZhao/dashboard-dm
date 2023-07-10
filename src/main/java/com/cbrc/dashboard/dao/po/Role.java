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
 * @date: 2018-05-16  下午1:52
 * @Description: TODO
 */

@Data
@TableName("role")
public class Role extends Entity {

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId; //角色ID

    @TableField(value = "role_name")
    private String roleName; //角色名称

    @TableField(value = "role_desc")
    private String roleDesc; //角色描述

    @TableField(value = "status")
    private Integer status; //角色状态 1有效 2无效
}
