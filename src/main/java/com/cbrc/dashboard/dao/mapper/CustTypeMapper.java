package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cbrc.dashboard.dao.mapper.provider.BankTypeSqlProvider;
import com.cbrc.dashboard.dao.mapper.provider.CustTypeSqlProvider;
import com.cbrc.dashboard.dao.po.BankType;
import com.cbrc.dashboard.dao.po.GroupType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustTypeMapper extends BaseMapper<BankType> {
    @SelectProvider(type = CustTypeSqlProvider.class, method = "getGroupList")
    List<Map> getGroupList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd);

    @SelectProvider(type = CustTypeSqlProvider.class, method = "getCustList")
    List<Map> getCustList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName, @Param("num") Integer num);

    @SelectProvider(type = CustTypeSqlProvider.class, method = "getGroupCustList")
    List<GroupType> getGroupCustList(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("groupName") String groupName, @Param("num") Integer num);
}
