package com.cbrc.dashboard.dao.dto;

import lombok.Data;

@Data
public class HomeMapDto {

    private String bankName; // 银行名称

    private String bankId;//银行代码

    private String typeName; // 银行大类名称

    private String typeId;//银行大类代码

    private String socialCreditCode; //统一社会信用代码

    private String customerName; //客户名称

    private Double issueMoney; // 发放金额
}
