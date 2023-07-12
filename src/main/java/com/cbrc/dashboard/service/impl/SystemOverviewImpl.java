package com.cbrc.dashboard.service.impl;

import com.cbrc.dashboard.dao.mapper.ISystemListMapper;
import com.cbrc.dashboard.service.ISystemOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SystemOverviewImpl implements ISystemOverviewService {
    @Autowired
    private ISystemListMapper systemListMapper;

    @Override
    public List<Map> getSystemListSer() {
        List<Map> list = systemListMapper.getSystemListMap();
        return list;
    }

}
