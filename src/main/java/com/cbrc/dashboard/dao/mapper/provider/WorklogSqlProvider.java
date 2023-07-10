package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class WorklogSqlProvider {
    public String getWorklogList() {
        return new SQL() {
            {
                SELECT("date_format(wl.logdate,'%Y') as year, date_format(wl.logdate,'%Y-%m') as month, ct.title, ct.contents, ct.sortid, wl.period");
                FROM("wl_worklog wl");
                INNER_JOIN("wl_contents ct on ct.log_id = wl.id");
                ORDER_BY("wl.logdate desc, ct.sortid");
            }
        }.toString();
    }
}
