package com.cbrc.dashboard.bo;

import lombok.Data;

@Data
public class CustomerStatDataBo {

    private String customerName; //客户名称

    private Double issueMoney; // 发放金额

    private Integer loanWay; //贷款发放方式

    private String startDate; //发放日期

    private String dueDate; //到期日期

    private Integer loanNum; // 贷款比数合计

    private Double loanBalance;//贷款金额合计
}
