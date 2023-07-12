package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cbrc.dashboard.dao.mapper.provider.SystemListSqlProvider;
import com.cbrc.dashboard.dao.po.SystemList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface ISystemListMapper extends BaseMapper<SystemList> {
    @SelectProvider(type = SystemListSqlProvider.class, method = "getSystemListPro")
    List<Map> getSystemListMap();
}
