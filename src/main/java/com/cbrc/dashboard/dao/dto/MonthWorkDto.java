package com.cbrc.dashboard.dao.dto;

import lombok.Data;
import java.util.List;

@Data
public class MonthWorkDto {
    private String workdate;

    public String getWorkdate() {
        return workdate;
    }

    public void setWorkdate(String workdate) {
        this.workdate = workdate;
    }

    public List<WorkContentDto> getWorkcnt() {
        return workcnt;
    }

    public void setWorkcnt(List<WorkContentDto> workcnt) {
        this.workcnt = workcnt;
    }

    private List<WorkContentDto> workcnt;
}
