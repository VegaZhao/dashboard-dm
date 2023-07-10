package com.cbrc.dashboard.dao.dto;

import com.cbrc.dashboard.dao.Entity;
import lombok.Data;

//返回前端的数据结构-工作日志条目
@Data
public class WorkContentDto extends Entity {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;
}
