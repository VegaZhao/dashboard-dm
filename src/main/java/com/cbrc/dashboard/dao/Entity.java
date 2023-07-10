/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao
 * @author: Herry
 * @date: 2018年5月7日 下午3:19:34
 * @Description:
 */
package com.cbrc.dashboard.dao;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author Herry
 *
 */
public class Entity implements Serializable {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
