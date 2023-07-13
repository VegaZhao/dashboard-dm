package com.cbrc.dashboard.service;

import com.cbrc.dashboard.bo.VulnStatDataBo;
import com.cbrc.dashboard.dao.po.SystemListPo;
import com.cbrc.dashboard.entity.CustomPage;

import java.util.List;

public interface IProfileService {
    List<SystemListPo> getSystemListSer();

    CustomPage<VulnStatDataBo> getVulnerabilityTableBySystemSer(String systemName, String searchKey, String searchValue, int startPage, int pageSize);

}

