package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("c_group")
public class GroupType {
    @TableField(value = "GROUPID")
    private String groupId;

    @TableField(value = "REPORTDATE")
    private  String reportDate;

    @TableField(value = "CUSTOMERNAME")
    private  String customerName;

}
