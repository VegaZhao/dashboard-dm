package com.cbrc.dashboard.dao.mapper;

import com.cbrc.dashboard.dao.dto.WorklogDto;
import com.cbrc.dashboard.dao.mapper.provider.WorklogSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface IWorklogMapper {
    @SelectProvider(type = WorklogSqlProvider.class, method = "getWorklogList")
    List<WorklogDto> getWorklogList();
}

