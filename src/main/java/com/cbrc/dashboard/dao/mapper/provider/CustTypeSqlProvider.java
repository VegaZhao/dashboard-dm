package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class CustTypeSqlProvider {


    public String getGroupList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd) {
        return new SQL() {
            {
                SELECT_DISTINCT("IFNULL(g.GROUPID, ccl.SOCIALCREDITCODE) as GROUPID, IFNULL(g.CUSTOMERNAME, ccl.CUSTOMERNAME) as GROUPNAME");
                FROM("c_credit_corporation_loan ccl");
                LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9)=m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m') ");
                LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
                WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
            }
        }.toString();
    }

    public String getCustList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName, @Param("num") Integer num) {
        SQL sql = new SQL();
        sql.SELECT("ci.SOCIAL_CREDIT_CODE as CUSTCODE, ci.NAME as CUSTNAME");
        sql.FROM("CUSTOMER_INFO ci");
        if (custName != null && !custName.equals("")) {
            sql.WHERE("ci.NAME like concat(concat('%','" + custName + "'),'%')");
        }
        sql.ORDER_BY("ci.NAME");
        String querySQL = sql.toString()+" limit #{num}";
        return querySQL;
    }

    public String getGroupCustList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("groupName") String groupName, @Param("num") Integer num) {
        SQL sql = new SQL();
        sql.SELECT("cg.*");
        sql.FROM("c_group cg");
        if (groupName != null && !groupName.equals("")) {
            sql.WHERE("cg.CUSTOMERNAME like concat(concat('%','" + groupName + "'),'%')");
        }
        sql.ORDER_BY("cg.CUSTOMERNAME");
        String querySQL = sql.toString()+" limit #{num}";
        return querySQL;
    }
//    public String getCustList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("groupName") String groupName) {
//        SQL sql = new SQL();
//        sql.SELECT_DISTINCT("IFNULL(m.ORGANIZATIONCODE, ccl.SOCIALCREDITCODE) as CUSTCODE, IFNULL(m.MEMBERNAME, ccl.CUSTOMERNAME) as CUSTNAME");
//        sql.FROM("c_credit_corporation_loan ccl");
//        sql.LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9)=m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//        sql.LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
//        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
//        if (groupName != null && !groupName.equals("")) {
//            groupName = groupName.replace("[", "");
//            groupName = groupName.replace("]", "");
//            if (groupName != null && !groupName.equals(""))
//                whereCondition += " and (g.CUSTOMERNAME in (" + groupName + ") or ccl.CUSTOMERNAME in (" + groupName + "))";
//        }
//        sql.WHERE(whereCondition);
//        return sql.toString();
//    }
}
