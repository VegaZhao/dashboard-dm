package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("c_group_members")
public class GroupMembers {
    @TableField(value = "MEMBERID")
    private String memberId;

    @TableField(value = "REPORTDATE")
    private  String reportDate;

    @TableField(value = "MEMBERNAME")
    private  String memberName;

    @TableField(value = "ORGANIZATIONCODE")
    private  String organizationCode;

    @TableField(value = "GROUPID")
    private  String groupId;

}
