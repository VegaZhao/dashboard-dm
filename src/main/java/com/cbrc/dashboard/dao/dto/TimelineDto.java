package com.cbrc.dashboard.dao.dto;

import lombok.Data;
import java.util.List;

@Data
public class TimelineDto {
    private String year;
    private List<YearlogDto> yearlog;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<YearlogDto> getYearlog() {
        return yearlog;
    }

    public void setYearlog(List<YearlogDto> yearlog) {
        this.yearlog = yearlog;
    }

}
