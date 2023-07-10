package com.cbrc.dashboard.bo;

import lombok.Data;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.bo
 * @author: Herry
 * @Date: 2020/9/16 15:51
 * @Description: TODO
 */
@Data
public class AssureStatDataBo {

    private Integer assureType; // 担保类型

    private String assureTypeDesc; // 担保类型描述

    private Integer loanNum; // 贷款比数合计

    private Double loanBalance; // 贷款金额合计

    private Integer loanWay; //贷款发放方式

    private String loanWayDesc; //贷款发放方式描述

    private Integer loanWayNum; // 贷款比数合计

    private Double loanWayBalance; // 贷款金额合计

    private Integer industryCode; //投向行业类型

    private String industryCodeDesc; //投向行业类型描述

    private Integer industryNum; // 贷款比数合计

    private Double industryBalance; // 贷款金额合计

    private Integer loanType; //业务品种

    private String loanTypeDesc; //业务品种描述

    private Integer loanTypeNum; // 贷款比数合计

    private Double loanTypeBalance; // 贷款金额合计

    private String bankId;//银行代码

    private String bankName;//银行名称

    private Double bankLoanBalance;//银行贷款金额合计

    private String customerName;//客户名

    private Double costLoanBalance;//客户贷款金额总计

    private String groupID; //集团编号

    private String groupName; //集团名称
}
