package com.cbrc.dashboard.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.cbrc.dashboard.bo.VulnStatDataBo;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.mapper.IProfileMapper;
import com.cbrc.dashboard.dao.po.SystemListPo;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfileImpl implements IProfileService {
    @Autowired
    private IProfileMapper profileMapper;

    @Override
    public List<SystemListPo> getSystemListSer() {
        List<SystemListPo> list = profileMapper.getSystemListMap();
        return list;
    }

    @Override
    public CustomPage<VulnStatDataBo> getVulnerabilityTableBySystemSer(String systemName, String searchKey, String searchValue, int startPage, int pageSize) {
        Page<VulnStatDataBo> page = new Page<>(startPage, pageSize);
        List<VulnStatDataBo> vulnerablilityTable = profileMapper.getVulnerabilityTableBySystemMap(page, systemName, searchKey, searchValue);
        CustomPage<VulnStatDataBo> result = CustomPage.getCustomPage(page, vulnerablilityTable);
        return result;
    }

}
