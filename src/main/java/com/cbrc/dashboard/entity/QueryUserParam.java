package com.cbrc.dashboard.entity;

import lombok.Data;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.entity
 * @author: Herry
 * @Date: 2018/8/20 21:52
 * @Description: TODO
 */
@Data
public class QueryUserParam {

    private int startPage;

    private int pageSize;

    private String userName;
}
