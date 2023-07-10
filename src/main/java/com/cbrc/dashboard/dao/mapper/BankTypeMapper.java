package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cbrc.dashboard.dao.mapper.provider.BankTypeSqlProvider;
import com.cbrc.dashboard.dao.po.BankType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface BankTypeMapper extends BaseMapper<BankType> {
    @SelectProvider(type = BankTypeSqlProvider.class, method = "getBankTypeList")
    List<Map> getBankTypeList();

    @SelectProvider(type = BankTypeSqlProvider.class, method = "getBankList")
    List<Map> getBankList(@Param("bankType") String bankType);
}
