package com.cbrc.dashboard.dao.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("dict_industry_type")
public class IndustryType {
    @TableField(value = "id")
    private String id;

    @TableField(value = "name")
    private String name;
}
