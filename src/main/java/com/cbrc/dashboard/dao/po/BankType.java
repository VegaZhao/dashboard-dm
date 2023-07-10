package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("dict_bank_type")
public class BankType {
    @TableField(value = "type_id")
    private int type_id;

    @TableField(value = "type_name")
    private String type_name;
}
