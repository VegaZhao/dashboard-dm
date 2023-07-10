package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.dao.po
 * @author: Herry
 * @Date: 2020/9/11 10:24
 * @Description: TODO
 */
@Data
@TableName("v_c_credit_corporation_loan")
public class CreditCorporationLoan {

    @TableField(value = "ID")
    private String ID; // 自增编号

    @TableField(value = "BANKNAME")
    private String bankName; // 银行名称

    @TableField(value = "REPORTDATE")
    private Integer reportDate; //上报时间

    @TableField(value = "SOCIALCREDITCODE")
    private String socialCreditCode; //统一社会信用代码

    @TableField(value = "CUSTOMERNAME")
    private String customerName; //客户名称

    @TableField(value = "ISSUEMONEY")
    private Double issueMoney; // 发放金额

    @TableField(value = "LOANWAY")
    private Integer loanWay; //贷款发放方式

    @TableField(value = "STARTDATE")
    private String startDate; //发放日期

    @TableField(value = "DUEDATE")
    private String dueDate; //到期日期

    @TableField(value = "VOCATIONCODE")
    private Integer vocationCode; //投向行业类型

    @TableField(value = "LOANTYPE")
    private Integer loanType; //业务品种

    @TableField(value = "GUARANTEETYPE")
    private Integer guaranteeType; //担保方式
}
