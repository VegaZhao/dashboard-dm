package com.cbrc.dashboard.dao.dto;

import lombok.Data;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.dao.dto
 * @author: Herry
 * @Date: 2020/9/11 10:36
 * @Description: TODO
 */
@Data
public class CreditCorporationLoanDto {

    private String bankName; // 银行名称

    private String bankId;//银行代码

    private Integer reportDate; //上报时间

    private String socialCreditCode; //统一社会信用代码

    private String customerName; //客户名称

    private Double issueMoney; // 发放金额

    private Double amt; // 发放金额合计

    private Integer count;//发放笔数合计

    private Integer rank;//序号

    private Integer loanWay; //贷款发放方式

    private String startDate; //发放日期

    private String dueDate; //到期日期

    private Integer vocationCode; //投向行业类型

    private Integer loanType; //业务品种

    private Integer guaranteeType; //担保方式

    private String vocationCodeDesc; //投向行业类型描述

    private String groupID; //集团编号

    private String groupName; //集团名称

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getReportDate() {
        return reportDate;
    }

    public void setReportDate(Integer reportDate) {
        this.reportDate = reportDate;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getIssueMoney() {
        return issueMoney;
    }

    public void setIssueMoney(Double issueMoney) {
        this.issueMoney = issueMoney;
    }

    public Integer getLoanWay() {
        return loanWay;
    }

    public void setLoanWay(Integer loanWay) {
        this.loanWay = loanWay;
    }

    public Integer getVocationCode() {
        return vocationCode;
    }

    public void setVocationCode(Integer vocationCode) {
        this.vocationCode = vocationCode;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public Integer getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(Integer guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public String getVocationCodeDesc() {
        return vocationCodeDesc;
    }

    public void setVocationCodeDesc(String vocationCodeDesc) {
        this.vocationCodeDesc = vocationCodeDesc;
    }

    public String getLoanTypeDesc() {
        return loanTypeDesc;
    }

    public void setLoanTypeDesc(String loanTypeDesc) {
        this.loanTypeDesc = loanTypeDesc;
    }

    public String getGuaranteeTypeDesc() {
        return guaranteeTypeDesc;
    }

    public void setGuaranteeTypeDesc(String guaranteeTypeDesc) {
        this.guaranteeTypeDesc = guaranteeTypeDesc;
    }

    public String getLoanWayDesc() {
        return loanWayDesc;
    }

    public void setLoanWayDesc(String loanWayDesc) {
        this.loanWayDesc = loanWayDesc;
    }

    private String loanTypeDesc; //业务品种描述

    private String guaranteeTypeDesc; //担保方式描述

    private String loanWayDesc; //贷款发放方式描述

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
