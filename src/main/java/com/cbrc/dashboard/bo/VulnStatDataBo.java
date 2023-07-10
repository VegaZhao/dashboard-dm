package com.cbrc.dashboard.bo;

import lombok.Data;

import java.util.Date;

@Data
public class VulnStatDataBo {
    private Integer rn; // 序列号

    private Integer id; // 序号

    private String systemName; // 系统名称

    private String title;  // 漏洞名称

    private String description; // 漏洞描述

    private String severity;  // 漏洞等级

    private String discResource; // 漏洞来源

    private String category;  //漏洞分类

    private String funcScene;  // 业务功能场景

    private Date discDate;  // 漏洞发现日期

    private String repaireStatus;  // 修复情况

    private Date repairedDate;  // 修复日期

    private String discoverer;  // 漏洞发现人

    private String remarks;  // 备注
}
