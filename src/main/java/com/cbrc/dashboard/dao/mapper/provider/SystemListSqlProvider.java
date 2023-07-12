package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class SystemListSqlProvider {
    public String getSystemListPro() {
        return new SQL() {
            {
                SELECT("u.*");
                FROM("srp_dict_system_list u");
            }
        }.toString();
    }
}
