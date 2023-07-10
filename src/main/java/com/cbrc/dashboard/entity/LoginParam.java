package com.cbrc.dashboard.entity;

import lombok.Data;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.entity
 * @author: Herry
 * @Date: 2018/8/9 21:06
 * @Description: TODO
 */
@Data
public class LoginParam {

    private String userName;

    private String password;

    private boolean isRememberMe;
}
