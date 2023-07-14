package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.cbrc.dashboard.bo.VulnStatDataBo;
import com.cbrc.dashboard.dao.mapper.provider.ProfileSqlProvider;
import com.cbrc.dashboard.dao.po.SystemListPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface IProfileMapper {
    @SelectProvider(type = ProfileSqlProvider.class, method = "getSystemListPro")
    List<SystemListPo> getSystemListMap();

    @SelectProvider(type = ProfileSqlProvider.class, method = "getVulnerabilityTableBySystemPro")
    List<VulnStatDataBo> getVulnerabilityTableBySystemMap(Page page, @Param("systemName") String systemName, @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);
}
