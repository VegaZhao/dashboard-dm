package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("srp_dict_system_list")
public class SystemListPo {
    @TableField(value = "syscode")
    private String syscode;

    @TableField(value = "sysname")
    private String sysname;
}