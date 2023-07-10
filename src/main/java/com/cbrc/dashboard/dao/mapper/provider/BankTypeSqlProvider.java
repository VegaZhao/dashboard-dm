package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class BankTypeSqlProvider {
    public String getBankTypeList() {
        return new SQL() {
            {
                SELECT("u.*");
                FROM("dict_bank_type u");
            }
        }.toString();
    }

    public String getBankList(@Param("bankType") String bankType) {
        SQL sql = new SQL();
        sql.SELECT("u.*");
        sql.FROM("dict_bank u");
        if (bankType != null && !bankType.equals("")) {
            bankType = bankType.replace("[","");
            bankType = bankType.replace("]","");
            if (bankType != null && !bankType.equals(""))
                sql.WHERE("type_id in (" + bankType + ")");
        }
        return sql.toString();
    }
}
