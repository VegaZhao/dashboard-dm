package com.cbrc.dashboard.service;

import com.cbrc.dashboard.dao.dto.TimelineDto;
import com.cbrc.dashboard.dao.dto.WorklogDto;

import java.util.List;

public interface IWorklogService {
        List<TimelineDto> getWorklogList();
}
