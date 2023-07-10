package com.cbrc.dashboard.enums;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @author: Herry
 * @Date: 2018/12/2314:52
 * @Description: TODO
 */
public enum RedisKeyEnum {

    REDIS_COL_INFO_KEY("redis_col_info"),
    REDIS_COL_UPDATE_TIME("redis_col_update_time"),
    REDIS_TAB_INFO_KEY("redis_tab_info"),
    REDIS_TAB_UPDATE_TIME("redis_tab_update_time"),
    REDIS_DATA_REPORT_TEMPLATE_INFO_KEY("redis_data_report_template_info");


    private String value;

    RedisKeyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
