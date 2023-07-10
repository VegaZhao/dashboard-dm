package com.cbrc.dashboard.bo;

import lombok.Data;

import java.util.Date;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.bo
 * @author: herry
 * @date: 2018-05-18  下午2:44
 * @Description: 用户登录信息
 */
@Data
public class UserOnlineBo {
    private Integer userId;

    private String userName;

    private String password;

    private String salt;// 加密盐

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

    private Date regTime; //注册时间

    private String regIp; //注册IP

    private Date updateTime; //更新时间

    private Integer status; //账号状态

    private Date lastAccess; //最后一次和系统交互的时间

    private String host; //主机的IP地址

    private String sessionId; //session ID

    private Long timeout;//回话到期 ttl(ms)

    private Date startTime; //session创建时间

    private Boolean sessionStatus; //sessionStatus
}
