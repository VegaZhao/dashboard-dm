package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cbrc.dashboard.dao.Entity;
import lombok.Data;

import java.util.Date;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.po
 * @author: herry
 * @date: 2018-05-15  上午11:18
 * @Description: TODO
 */
@Data
@TableName("user")
public class User extends Entity {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "salt")
    private String salt;// 加密盐

    @TableField(value = "nick_name") //昵称
    private String nickName;

    @TableField(value = "sex")
    private Integer sex; //性别

    @TableField(value = "birthday")
    private Date birthday;

    @TableField(value = "avatar")
    private String avatar; //头像链接

    @TableField(value = "tel")
    private String tel; //手机号

    @TableField(value = "mobile")
    private String mobile; //电话号码

    @TableField(value = "email")
    private String email; //邮箱

    @TableField(value = "wx")
    private String wx; //微信账号

    @TableField(value = "qq")
    private String qq; //qq账号

    @TableField(value = "area")
    private String area; //所属区域，逗号分割

    @TableField(value = "address")
    private String address; //详细地址

    @TableField(value = "reg_time")
    private Date regTime; //注册时间

    @TableField(value = "reg_ip")
    private String regIp; //注册IP

    @TableField(value = "update_time")
    private Date updateTime; //更新时间

    @TableField(value = "status")
    private Integer status; //账号状态 1有效，2无效

}
