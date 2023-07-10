package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("srp_vulnerabilities_list")
public class VulnerabilitiesList {
    @TableField(value = "id")
    private String id;

    @TableField(value = "system_name")
    private String system_name;

    @TableField(value = "disc_resource")
    private String disc_resource;

    @TableField(value = "category")
    private String category;

    @TableField(value = "func_scene")
    private String func_scene;

    @TableField(value = "title")
    private String title;

    @TableField(value = "description")
    private String description;

    @TableField(value = "severity")
    private String severity;

    @TableField(value = "disc_date")
    private Date disc_date;

    @TableField(value = "repaire_status")
    private String repaire_status;

    @TableField(value = "repaired_date")
    private Date repaired_date;

    @TableField(value = "discoverer")
    private String discoverer;

    @TableField(value = "remarks")
    private String remarks;
}
