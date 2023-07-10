package com.cbrc.dashboard.dao.mapper.provider;

import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import static com.alibaba.druid.sql.ast.SQLOrderingSpecification.DESC;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.dao.mapper.provider
 * @author: Herry
 * @Date: 2020/9/16 16:18
 * @Description: TODO
 */
public class CreditCoporationLoanProvider {

    //担保方式等四页面的饼图、条形图
    public String getCreditCorporationLoanByDateRange(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd) {
        return new SQL() {
            {
                SELECT("ccl.*,at.name as guarantee_type_desc,bt.name as loan_type_desc,it.name as vocation_code_desc,lt.name as loan_way_desc");
                FROM("c_credit_corporation_loan ccl");
                LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
                LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
                LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
                LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
                WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
            }
        }.toString();
    }

    public String getBankStateData(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("bankType") String bankType, @Param("bankName") String bankName) {
        SQL sql = new SQL();

        sql.SELECT("ccl.*,at.name as guarantee_type_desc,bt.name as loan_type_desc,it.name as vocation_code_desc,lt.name as loan_way_desc");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
        sql.LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
        sql.LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
        sql.LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
        sql.LEFT_OUTER_JOIN("dict_bank db on ccl.BANKNAME = db.bank_name");
//        sql.LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//        sql.LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (bankName != null && !bankName.equals("")) {
            bankName = bankName.replace("[", "");
            bankName = bankName.replace("]", "");
            bankName = bankName.replaceAll(",", "','");
            if (bankName != null && !bankName.equals(""))
                whereCondition += " and db.bank_id in ('" + bankName +"')";
        }
        if (bankType != null && !bankType.equals("")) {
            bankType = bankType.replace("[", "");
            bankType = bankType.replace("]", "");
            bankType = bankType.replaceAll(",", "','");
            if (bankType != null && !bankType.equals(""))
                whereCondition += " and db.type_id in ('" + bankType +"')";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //担保方式等四页面tab页条形图（包括银行和客户可以选择不同担保方式返回前25条数据）
    public String getCreditCorporationLoanByAssureType
            (@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
             @Param("assureType") String assureType, @Param("loanWay") String loanWay,
             @Param("industryType") String industryType, @Param("loanType") String loanType
            ) {
        return new SQL() {
            {
                SELECT("ccl.* ");
                FROM("c_credit_corporation_loan ccl");
//                LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
//                LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");//业务品种
//                LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
//                LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");//贷款发放方式
//                LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//                LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
                WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
                if (!assureType.equals("0")) {
                    AND();
                    WHERE("ccl.GUARANTEETYPE = #{assureType}");
                }
                ;
                if (!loanWay.equals("0")) {
                    AND();
                    WHERE("ccl.LOANWAY = #{loanWay}");
                }                ;
                if (!industryType.equals("0")) {
                    AND();
                    WHERE("ccl.VOCATIONCODE = #{industryType}");
                }
                ;
                if (!loanType.equals("0")) {
                    AND();
                    WHERE("ccl.LOANTYPE = #{loanType}");
                }
            }
        }.toString();
    }

    //单一法人公司客户排名
    public String getCreditCorporationLoanOnCustomer
    (@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
     @Param("assureType") String assureType, @Param("loanWay") String loanWay,
     @Param("industryType") String industryType, @Param("loanType") String loanType,
     @Param("bankType") String bankType, @Param("bankName") String bankName,
     @Param("searchKey") String searchKey, @Param("searchValue") String searchValue
    ) {
        SQL sql = new SQL();
        sql.SELECT("row_number() OVER(ORDER BY amt DESC) as rank,ccl.CUSTOMERNAME, sum(ccl.ISSUEMONEY) amt ,count(ccl.ISSUEMONEY) count");
        sql.FROM("c_credit_corporation_loan ccl");
//        sql.LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
//        sql.LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
//        sql.LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
//        sql.LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
        sql.LEFT_OUTER_JOIN("dict_bank b on ccl.BANKNAME = b.bank_name");
        sql.LEFT_OUTER_JOIN("dict_bank_type t on b.type_id = t.type_id");
//        sql.LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//        sql.LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        sql.GROUP_BY("ccl.CUSTOMERNAME");
        sql.ORDER_BY("amt desc");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (!assureType.equals("0")) {
            whereCondition += "and ccl.GUARANTEETYPE = #{assureType}";
        }
        if (!loanWay.equals("0")) {
            whereCondition += "and ccl.LOANWAY = #{loanWay}";
        }                ;
        if (!industryType.equals("0")) {
            whereCondition += "and ccl.VOCATIONCODE = #{industryType}";
        }
        if (!loanType.equals("0")) {
            whereCondition += "and ccl.LOANTYPE = #{loanType}";
        };
        if (bankName != null && !bankName.equals("")) {
            bankName = bankName.replace("[", "");
            bankName = bankName.replace("[", "");
            bankName = bankName.replace("]", "");
            bankName = bankName.replaceAll(",", "','");
            if (bankName != null && !bankName.equals(""))
                whereCondition += " and b.bank_id in ('" + bankName +"')";
        }
        if (bankType != null && !bankType.equals("")) {
            bankType = bankType.replace("[", "");
            bankType = bankType.replace("]", "");
            bankType = bankType.replaceAll(",", "','");
            if (bankType != null && !bankType.equals(""))
                whereCondition += " and t.type_id in ('" + bankType +"')";
        }
        if (searchKey.equals("name") && !searchValue.equals("")) {
            searchValue = searchValue.replaceAll(" ", "");
            if(searchKey.equals("name") && searchValue != null &&!searchValue.equals("")) {
                whereCondition += " and ccl.CUSTOMERNAME like concat(concat('%','" + searchValue + "'),'%')";
            }
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //银行全排名表格，可快捷搜索
    public String getCreditCorporationLoanOnBank
            (@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
             @Param("assureType") String assureType, @Param("loanWay") String loanWay,
             @Param("industryType") String industryType, @Param("loanType") String loanType,
             @Param("searchKey") String searchKey, @Param("searchValue") String searchValue
            ) {
                SQL sql = new SQL();
                sql.SELECT("row_number() OVER(ORDER BY amt DESC) as rank, ccl.BANKNAME,sum(ccl.ISSUEMONEY) amt ,count(ccl.ISSUEMONEY) count");
                sql.FROM("c_credit_corporation_loan ccl");
//                sql.LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
//                sql.LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
//                sql.LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
//                sql.LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
//                sql.LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//                sql.LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
                sql.GROUP_BY("ccl.BANKNAME");
                sql.ORDER_BY("amt desc");
                String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
                if (!assureType.equals("0")) {
                    whereCondition += "and ccl.GUARANTEETYPE = #{assureType}";
                }
                ;
                if (!loanWay.equals("0")) {
                    whereCondition += "and ccl.LOANWAY = #{loanWay}";
                }                ;
                if (!industryType.equals("0")) {
                    whereCondition += "and ccl.VOCATIONCODE = #{industryType}";
                }
                ;
                if (!loanType.equals("0")) {
                    whereCondition += "and ccl.LOANTYPE = #{loanType}";
                };
                if(searchKey.equals("name") && searchValue != null &&!searchValue.equals("")){
                    searchValue = searchValue.replaceAll(" ", "");
                    if(searchKey.equals("name") && searchValue != null &&!searchValue.equals("")){
                    whereCondition += "and ccl.BANKNAME like concat(concat('%','" + searchValue + "'),'%')";
                    }
                }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    public String getBankStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("bankType") String bankType, @Param("bankName") String bankName) {
        SQL sql = new SQL();
        sql.SELECT("c.BANKNAME, sum(c.ISSUEMONEY) amt ,t.type_id,t.type_name,b.bank_id");
        sql.FROM("c_credit_corporation_loan c");
        sql.LEFT_OUTER_JOIN("dict_bank b on c.BANKNAME=b.bank_name");
        sql.LEFT_OUTER_JOIN("dict_bank_type t on b.type_id=t.type_id");
        sql.GROUP_BY("c.BANKNAME,t.type_id,t.type_name,b.bank_id");
        String whereCondition = "c.REPORTDATE >= #{monthBegin} and c.REPORTDATE <= #{monthEnd}";
        if (bankName != null && !bankName.equals("")) {
            bankName = bankName.replace("[", "");
            bankName = bankName.replace("]", "");
            bankName = bankName.replaceAll(",", "','");
            if (bankName != null && !bankName.equals(""))
                whereCondition += " and b.bank_id in ('" + bankName +"')";
        }
        if (bankType != null && !bankType.equals("")) {
            bankType = bankType.replace("[", "");
            bankType = bankType.replace("]", "");
            bankType = bankType.replaceAll(",", "','");
            if (bankType != null && !bankType.equals(""))
                whereCondition += " and t.type_id in ('" + bankType +"')";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //CustomerExpire折线图
    public String getCreditCorporationLoanByData(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd) {
        return new SQL() {
            {
                SELECT("ccl.*");
                FROM("c_credit_corporation_loan ccl");
                WHERE("ccl.DUEDATE >= #{dateBegin} and ccl.DUEDATE <= #{dateEnd}");
            }
        }.toString();
    }

    //CustomerExpire表格贷款明细
    public String getCreditCorporationTableByDay(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd,
                                                 @Param("dueDate") String dueDate, @Param("searchKey") String searchKey,
                                                 @Param("searchValue") String searchValue) {
        SQL sql = new SQL();
                sql.SELECT("ccl.*");
                sql.FROM("c_credit_corporation_loan ccl");
                String whereCondition ="";
                if (dueDate != null && !dueDate.equals("")) {
                    whereCondition += "ccl.DUEDATE = #{dueDate}";
                }else{
                    whereCondition += "ccl.DUEDATE >= #{dateBegin} and ccl.DUEDATE <= #{dateEnd}";
                };
                if(searchKey.equals("compname") && searchValue != null &&!searchValue.equals("")) {
                    searchValue = searchValue.replaceAll(" ", "");
                    if(searchKey.equals("compname") && searchValue != null &&!searchValue.equals("")){
                        whereCondition += "and ccl.CUSTOMERNAME like concat(concat('%','" + searchValue + "'),'%')";
                    }
                };
        if(searchKey.equals("loandate") && searchValue != null &&!searchValue.equals("")) {
            searchValue = searchValue.replaceAll(" ", "");
            if(searchKey.equals("loandate") && searchValue != null &&!searchValue.equals("")){
                whereCondition += "and ccl.STARTDATE like concat(concat('%','" + searchValue + "'),'%')";
            }
        };
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    ////CustomerExpire表格贷款金额汇总
    public String getCreditCorporationLoanByDay(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd,
                                                @Param("dueDate") String dueDate,@Param("searchKey") String searchKey,
                                                @Param("searchValue") String searchValue) {
        SQL sql = new SQL();
        sql.SELECT("sum(ccl.ISSUEMONEY) as amt");
        sql.FROM("c_credit_corporation_loan ccl");
        String whereCondition ="";
        if (dueDate != null && !dueDate.equals("")) {
            whereCondition += "ccl.DUEDATE = #{dueDate}";
        }else{
            whereCondition += "ccl.DUEDATE >= #{dateBegin} and ccl.DUEDATE <= #{dateEnd}";
        };
        if(searchKey.equals("compname") && searchValue != null &&!searchValue.equals("")) {
            searchValue = searchValue.replaceAll(" ", "");
            if(searchKey.equals("compname") && searchValue != null &&!searchValue.equals("")){
                whereCondition += "and ccl.CUSTOMERNAME like concat(concat('%','" + searchValue + "'),'%')";
            }
        };
        if(searchKey.equals("loandate") && searchValue != null &&!searchValue.equals("")) {
            searchValue = searchValue.replaceAll(" ", "");
            if(searchKey.equals("loandate") && searchValue != null &&!searchValue.equals("")){
                whereCondition += "and ccl.STARTDATE like concat(concat('%','" + searchValue + "'),'%')";
            }
        };
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    public String getCustStateData(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();

        sql.SELECT("ccl.*,at.name as guarantee_type_desc,bt.name as loan_type_desc,it.name as vocation_code_desc,lt.name as loan_way_desc");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
        sql.LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
        sql.LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
        sql.LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
        sql.LEFT_OUTER_JOIN("dict_bank db on ccl.BANKNAME = db.bank_name");
//        sql.LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//        sql.LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
//        if (groupName != null && !groupName.equals("")) {
//            groupName = groupName.replace("[","");
//            groupName = groupName.replace("]","");
//            if (groupName != null && !groupName.equals(""))
//                whereCondition += " and (g.CUSTOMERNAME in (" + groupName + ") or ccl.CUSTOMERNAME in (" + groupName + "))";
//        }
        if (custName != null && !custName.equals("")) {
            custName = custName.replace("[","");
            custName = custName.replace("]","");
            if (custName != null && !custName.equals(""))
                whereCondition += " and (ccl.CUSTOMERNAME in (" + custName + "))";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //集团四统计图
    public String getGroupStateData(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();

        sql.SELECT("ccl.*,at.name as guarantee_type_desc,bt.name as loan_type_desc,it.name as vocation_code_desc,lt.name as loan_way_desc");
        sql.FROM("c_group g");
        sql.LEFT_OUTER_JOIN("c_group_members m on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        sql.LEFT_OUTER_JOIN("c_credit_corporation_loan ccl on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
        sql.LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
        sql.LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
        sql.LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
        sql.LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
        sql.LEFT_OUTER_JOIN("dict_bank db on ccl.BANKNAME = db.bank_name");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (custName != null && !custName.equals("")) {
            custName = custName.replace("[","");
            custName = custName.replace("]","");
            if (custName != null && !custName.equals(""))
                whereCondition += " and (g.CUSTOMERNAME in (" + custName + "))";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    public String getCustStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();
        sql.SELECT("ccl.CUSTOMERNAME, sum(ccl.ISSUEMONEY) amt");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.GROUP_BY("ccl.CUSTOMERNAME");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (custName != null && !custName.equals("")) {
            custName = custName.replace("[","");
            custName = custName.replace("]","");
            if (custName != null && !custName.equals(""))
                whereCondition += " and (ccl.CUSTOMERNAME in (" + custName + "))";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //集团树状图
    public String getGroupStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();
        sql.SELECT("g.CUSTOMERNAME, sum(ccl.ISSUEMONEY) amt");
        sql.FROM("c_group g");
        sql.LEFT_OUTER_JOIN("c_group_members m on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        sql.LEFT_OUTER_JOIN("c_credit_corporation_loan ccl on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
        sql.GROUP_BY("g.CUSTOMERNAME");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (custName != null && !custName.equals("")) {
            custName = custName.replace("[","");
            custName = custName.replace("]","");
            if (custName != null && !custName.equals(""))
                whereCondition += " and (g.CUSTOMERNAME in (" + custName + "))";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //集团树状图-2
    public String getGroupCustStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();
        sql.SELECT("g.CUSTOMERNAME, sum(ccl.ISSUEMONEY) amt, g.GROUPID, m.MEMBERNAME");
        sql.FROM("c_group g");
        sql.LEFT_OUTER_JOIN("c_group_members m on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        sql.LEFT_OUTER_JOIN("c_credit_corporation_loan ccl on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
        sql.GROUP_BY("g.CUSTOMERNAME, g.GROUPID, m.MEMBERNAME");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (custName != null && !custName.equals("")) {
            custName = custName.replace("[","");
            custName = custName.replace("]","");
            if (custName != null && !custName.equals(""))
                whereCondition += " and (g.CUSTOMERNAME in (" + custName + "))";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //集团-客户表
    public String getCreditCorporationLoanOnGroup
            (@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
             @Param("groupName") String groupName,
             @Param("searchKey") String searchKey, @Param("searchValue") String searchValue
            ) {
        SQL sql = new SQL();
        sql.SELECT("row_number() OVER(ORDER BY amt DESC) as rank, g.CUSTOMERNAME as groupName, ccl.CUSTOMERNAME as customerName,sum(ccl.ISSUEMONEY) amt ,count(ccl.ISSUEMONEY) count");
        sql.FROM("c_group g");
        sql.LEFT_OUTER_JOIN("c_group_members m on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        sql.LEFT_OUTER_JOIN("c_credit_corporation_loan ccl on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
        sql.GROUP_BY("ccl.CUSTOMERNAME, g.CUSTOMERNAME");
        sql.ORDER_BY("g.CUSTOMERNAME desc");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (groupName != null && !groupName.equals("")) {
            groupName = groupName.replace("[","");
            groupName = groupName.replace("]","");
            if (groupName != null && !groupName.equals(""))
                whereCondition += " and (g.CUSTOMERNAME in (" + groupName + "))";
        };
        if(searchKey.equals("groupName") && searchValue != null &&!searchValue.equals("")){
            searchValue = searchValue.trim();
            if(searchKey.equals("groupName") && searchValue != null &&!searchValue.equals("")){
                whereCondition += " and g.CUSTOMERNAME like concat(concat('%','" + searchValue + "'),'%')";
            }
        };
        if(searchKey.equals("customerName") && searchValue != null &&!searchValue.equals("")){
            searchValue = searchValue.trim();
            if(searchKey.equals("customerName") && searchValue != null &&!searchValue.equals("")){
                whereCondition += " and ccl.CUSTOMERNAME like concat(concat('%','" + searchValue + "'),'%')";
            }
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    public String getCustRelat(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {

        String querySql = "SELECT ccl.* FROM c_credit_corporation_loan ccl";
        querySql += " WHERE ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        querySql += " AND exists (select 1 from c_corporation cor";
        querySql += " LEFT OUTER JOIN c_corporation_affiliatedenterprises cca on cor.CORPORATIONID = cca.CORPORATIONID and cor.REPORTDATE = cca.REPORTDATE";
        querySql += " WHERE cca.PAPERTYPE <> '2' AND cca.ENTERPRISENAME = #{custName}";
        querySql += " AND date_format(date_add(date_format(concat(cca.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m') = ccl.REPORTDATE";
        querySql += " AND substr(ccl.SOCIALCREDITCODE, 9, 9) = cor.ORGANIZATIONCODE)";
        querySql += " ORDER BY ccl.CUSTOMERNAME";

        return querySql;

    }

    public String getRelatIssueMoneySum(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {

        String querySql = "SELECT SUM(ccl.ISSUEMONEY) as AMT FROM c_credit_corporation_loan ccl";
        querySql += " WHERE ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        querySql += " AND exists (select 1 from c_corporation cor";
        querySql += " LEFT OUTER JOIN c_corporation_affiliatedenterprises cca on cor.CORPORATIONID = cca.CORPORATIONID and cor.REPORTDATE = cca.REPORTDATE";
        querySql += " WHERE cca.PAPERTYPE <> '2' AND cca.ENTERPRISENAME = #{custName}";
        querySql += " AND date_format(date_add(date_format(concat(cca.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m') = ccl.REPORTDATE";
        querySql += " AND substr(ccl.SOCIALCREDITCODE, 9, 9) = cor.ORGANIZATIONCODE)";
        querySql += " ORDER BY ccl.CUSTOMERNAME";

        return querySql;
    }

    public String getCustLoanBnak(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();
        sql.SELECT("ccl.*");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
        sql.AND();
        sql.WHERE("ccl.CUSTOMERNAME = #{custName}");
        return sql.toString();
    }

    public String getCustIssueMoneySum(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
        SQL sql = new SQL();
        sql.SELECT("ccl.CUSTOMERNAME, sum(ccl.ISSUEMONEY) as AMT");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
        sql.AND();
        sql.WHERE("ccl.CUSTOMERNAME = #{custName}");
        sql.GROUP_BY("ccl.CUSTOMERNAME");
        return sql.toString();
    }

    //集团排名
    public String getGroupRank
    (@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
     @Param("assureType") String assureType, @Param("loanWay") String loanWay,
     @Param("industryType") String industryType, @Param("loanType") String loanType,
     @Param("bankType") String bankType, @Param("bankID") String bankID,
     @Param("searchKey") String searchKey, @Param("searchValue") String searchValue
     ) {
        SQL sql = new SQL();
        sql.SELECT("row_number() over(order by AMT DESC) as RANK ,IFNULL(g.CUSTOMERNAME, ccl.CUSTOMERNAME) as groupName,sum(ccl.ISSUEMONEY) AMT ,count(ccl.ISSUEMONEY) as count");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.LEFT_OUTER_JOIN("dict_assure_type at on ccl.GUARANTEETYPE = at.id");
        sql.LEFT_OUTER_JOIN("dict_business_type bt on ccl.LOANTYPE = bt.id");
        sql.LEFT_OUTER_JOIN("dict_industry_type it on ccl.VOCATIONCODE = it.id");
        sql.LEFT_OUTER_JOIN("dict_loan_type lt on ccl.LOANWAY = lt.id");
        sql.LEFT_OUTER_JOIN("dict_bank db on ccl.BANKNAME = db.bank_name");
        sql.LEFT_OUTER_JOIN("c_group_members m on substr(ccl.SOCIALCREDITCODE, 9, 9) = m.ORGANIZATIONCODE and ccl.REPORTDATE=date_format(date_add(date_format(concat(m.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
        sql.LEFT_OUTER_JOIN("c_group g on m.GROUPID=g.GROUPID and m.REPORTDATE=g.REPORTDATE");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}";
        if (!assureType.equals("0")) {
            whereCondition += "and ccl.GUARANTEETYPE = #{assureType}";
        }
        if (!loanWay.equals("0")) {
            whereCondition += "and ccl.LOANWAY = #{loanWay}";
        }                ;
        if (!industryType.equals("0")) {
            whereCondition += "and ccl.VOCATIONCODE = #{industryType}";
        }
        if (!loanType.equals("0")) {
            whereCondition += "and ccl.LOANTYPE = #{loanType}";
        }
        if (!loanType.equals("0")) {
            whereCondition += "and ccl.LOANTYPE = #{loanType}";
        }
        if (bankID != null && !bankID.equals("")) {
            bankID = bankID.replace("[", "");
            bankID = bankID.replace("]", "");
            bankID = bankID.replaceAll(",", "','");
            if (bankID != null && !bankID.equals(""))
                whereCondition += " and db.bank_id in ('" + bankID +"')";
        }
        if (bankType != null && !bankType.equals("")) {
            bankType = bankType.replace("[", "");
            bankType = bankType.replace("]", "");
            bankType = bankType.replaceAll(",", "','");
            if (bankType != null && !bankType.equals(""))
                whereCondition += " and db.type_id in ('" + bankType +"')";
        }
        if (searchKey.equals("name") && !searchValue.equals("")) {
            searchValue = searchValue.replaceAll(" ", "");
            if(searchKey.equals("name") && !searchValue.equals("")) {
                whereCondition += " and IFNULL(g.CUSTOMERNAME, ccl.CUSTOMERNAME) like concat(concat('%','" + searchValue + "'),'%')";
            }
        }
        sql.WHERE(whereCondition);
        sql.GROUP_BY("IFNULL(g.CUSTOMERNAME, ccl.CUSTOMERNAME)");
        sql.ORDER_BY("AMT DESC");
        return sql.toString();
    }

    public String getHomeTreeIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("bankType") String bankType, @Param("bankName") String bankName) {
        SQL sql = new SQL();
        sql.SELECT("c.SOCIALCREDITCODE as social_credit_code,c.CUSTOMERNAME as customer_name,c.BANKNAME as bank_name,c.ISSUEMONEY as issue_money,t.type_id as type_id,t.type_name as type_name,b.bank_id as bank_id");
        sql.FROM("c_credit_corporation_loan c");
        sql.LEFT_OUTER_JOIN("dict_bank b on c.BANKNAME=b.bank_name");
        sql.LEFT_OUTER_JOIN("dict_bank_type t on b.type_id=t.type_id");
        String whereCondition = "c.REPORTDATE >= #{monthBegin} and c.REPORTDATE <= #{monthEnd}";
        if (bankName != null && !bankName.equals("")) {
            bankName = bankName.replace("[", "");
            bankName = bankName.replace("]", "");
            bankName = bankName.replaceAll(",", "','");
            if (bankName != null && !bankName.equals(""))
                whereCondition += " and b.bank_id in ('" + bankName +"')";
        }
        if (bankType != null && !bankType.equals("")) {
            bankType = bankType.replace("[", "");
            bankType = bankType.replace("]", "");
            bankType = bankType.replaceAll(",", "','");
            if (bankType != null && !bankType.equals(""))
                whereCondition += " and t.type_id in ('" + bankType +"')";
        }
        sql.WHERE(whereCondition);
        return sql.toString();
    }

    //单一法人公司客户新发放贷款明细
    public String getCreditCorporationLoanOnCustDetail
    (@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
     @Param("custName") String custName, @Param("bankName") String bankName,
     @Param("assureID") String assureID, @Param("businessID") String businessID,
     @Param("industryID") String industryID, @Param("loanID") String loanID
    ) {
        SQL sql = new SQL();
        sql.SELECT("row_number() over(order by ccl.BANKNAME) as RANK, ccl.SOCIALCREDITCODE, ccl.CUSTOMERNAME, ccl.BANKNAME, ccl.ISSUEMONEY, ccl.STARTDATE, ccl.DUEDATE, dat.name as guaranteeTypeDesc, dit.name as vocationCodeDesc, dbt.name as loanTypeDesc, dlt.name as loanWayDesc");
        sql.FROM("c_credit_corporation_loan ccl");
        sql.LEFT_OUTER_JOIN("dict_assure_type dat on dat.id = ccl.GUARANTEETYPE");
        sql.LEFT_OUTER_JOIN("dict_business_type dbt on dbt.id = ccl.LOANTYPE");
        sql.LEFT_OUTER_JOIN("dict_industry_type dit on dit.id = ccl.VOCATIONCODE");
        sql.LEFT_OUTER_JOIN("dict_loan_type dlt on dlt.id = ccl.LOANWAY");
        String whereCondition = "ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd} ";
        if (!custName.equals("0")) {
            whereCondition += " and ccl.CUSTOMERNAME = #{custName}";
        }
        if (!bankName.equals("0")) {
            whereCondition += " and ccl.BANKNAME = #{bankName}";
        }
        if (!assureID.equals("0")) {
            whereCondition += " and ccl.GUARANTEETYPE = #{assureID}";
        }
        if (!businessID.equals("0")) {
            whereCondition += " and ccl.LOANTYPE = #{businessID}";
        }
        if (!industryID.equals("0")) {
            whereCondition += " and ccl.VOCATIONCODE = #{industryID}";
        }
        if (!loanID.equals("0")) {
            whereCondition += " and ccl.LOANWAY = #{loanID}";
        }
        sql.WHERE(whereCondition);
        if (!custName.equals("0")) {
            sql.ORDER_BY("ccl.BANKNAME");
        }
        if (!bankName.equals("0")) {
            sql.ORDER_BY("ccl.CUSTOMERNAME");
        }
        return sql.toString();
    }
}

//getCustRelat 备份
//    public String getCustRelat(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
//        SQL sql = new SQL();
//        sql.SELECT("ccl.*");
//        sql.FROM("c_corporation cor");
//        sql.LEFT_OUTER_JOIN("c_corporation_affiliatedenterprises cca on cor.CORPORATIONID = cca.CORPORATIONID and cor.REPORTDATE = cca.REPORTDATE");
//        sql.LEFT_OUTER_JOIN("c_credit_corporation_loan ccl on cor.ORGANIZATIONCODE = substr(ccl.SOCIALCREDITCODE, 9, 9) and ccl.REPORTDATE=date_format(date_add(date_format(concat(cca.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//        sql.WHERE("cca.PAPERTYPE <> '2'");
//        sql.AND();
//        sql.WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
//        sql.AND();
//        sql.WHERE("cca.ENTERPRISENAME = #{custName}");
//        sql.ORDER_BY("ccl.CUSTOMERNAME");
//        return sql.toString();
//    }
//getRelatIssueMoneySum备份
//public String getRelatIssueMoneySum(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName) {
//    SQL sql = new SQL();
//    sql.SELECT("cca.ENTERPRISENAME, SUM(ccl.ISSUEMONEY) as AMT");
//    sql.FROM("c_corporation cor");
//    sql.LEFT_OUTER_JOIN("c_corporation_affiliatedenterprises cca on cor.CORPORATIONID = cca.CORPORATIONID and cor.REPORTDATE = cca.REPORTDATE");
//    sql.LEFT_OUTER_JOIN("c_credit_corporation_loan ccl on cor.ORGANIZATIONCODE = substr(ccl.SOCIALCREDITCODE, 9, 9) and ccl.REPORTDATE=date_format(date_add(date_format(concat(cca.REPORTDATE,'01'),'%Y%m%d'), interval 1 month),'%Y%m')");
//    sql.WHERE("cca.PAPERTYPE <> '2'");
//    sql.AND();
//    sql.WHERE("ccl.REPORTDATE >= #{monthBegin} and ccl.REPORTDATE <= #{monthEnd}");
//    sql.AND();
//    sql.WHERE("cca.ENTERPRISENAME = #{custName}");
//    sql.GROUP_BY("cca.ENTERPRISENAME");
//    return sql.toString();
//}

