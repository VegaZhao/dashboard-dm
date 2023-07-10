package com.cbrc.dashboard.dao.dto;

import com.cbrc.dashboard.dao.Entity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.dto
 * @author: herry
 * @date: 2018-05-16  下午3:28
 * @Description: TODO
 */

@Data
public class UserDto extends Entity {

    private Integer userId;

    private String userName;

    private String nickName;

    private Integer sex; //性别

    private Date birthday;

    private String avatar; //头像链接

    private String tel; //手机号

    private String mobile; //电话号码

    private String email; //邮箱

    private String wx; //微信账号

    private String qq; //qq账号

    private String area; //所属区域，逗号分割

    private String address; //详细地址

    private Integer status; //账号状态

    private Boolean sessionStatus; // 用户是否在线

    private String statusDesc; //账户状态描述

    private Date regTime; //注册时间

    private Date updateTime; //更新时间

    private List<RoleDto> roles; //用户角色信息
}
