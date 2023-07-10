package com.cbrc.dashboard.dao.dto;

import lombok.Data;
import java.util.List;

@Data
public class YearlogDto {
    private String month;
    private List<MonthWorkDto> monthwork;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<MonthWorkDto> getMonthwork() {
        return monthwork;
    }

    public void setMonthwork(List<MonthWorkDto> monthwork) {
        this.monthwork = monthwork;
    }
}
