package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProfileSqlProvider {

    // 获取系统列表
    public String getSystemListPro() {
        return new SQL() {
            {
                SELECT("u.*");
                FROM("srp_dict_system_list u");
            }
        }.toString();
    }

    // 获取系统漏洞清单
    public String getVulnerabilityTableBySystemPro(@Param("systemName") String systemName, @Param("searchKey") String searchKey,
                                                @Param("searchValue") String searchValue) {
        SQL sql = new SQL();
        sql.SELECT("(@row_number:=@row_number + 1) AS rn, id, systemname, title, description, severity, discresource, category, funcscene, discdate, repairestatus, repaireddate, discoverer, remarks");
        sql.FROM("srp_vulnerabilities_list s, (select @row_number:=0) t");
        String whereCondition ="";
        whereCondition += "s.systemname = #{systemName}";
        searchValue = searchValue.replaceAll(" ", "");
        if(searchKey.equals("category") && searchValue != null &&!searchValue.equals("")) {
            whereCondition += "and s.category like concat(concat('%','" + searchValue + "'),'%')";
        };
        if(searchKey.equals("title") && searchValue != null &&!searchValue.equals("")) {
            whereCondition += "and s.title like concat(concat('%','" + searchValue + "'),'%')";
        };
        sql.WHERE(whereCondition);
        sql.ORDER_BY("s.discdate desc");
        return sql.toString();
    }
}
