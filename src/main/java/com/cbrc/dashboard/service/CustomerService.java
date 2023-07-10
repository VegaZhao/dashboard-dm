package com.cbrc.dashboard.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.cbrc.dashboard.bo.BankStatDataBo;
import com.cbrc.dashboard.bo.CustomerStatDataBo;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.po.GroupType;
import com.cbrc.dashboard.entity.CustomPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    List<Map> getGroupList(String monthBegin, String monthEnd);
    List<Map> getCustList(String monthBegin, String monthEnd, String custName, Integer num);
    List<GroupType> getGroupCustList(String monthBegin, String monthEnd, String groupName, Integer num);//集团选择器
    List<BankStatDataBo> getCustStateData(String monthBegin, String monthEnd, String custName);
    List<BankStatDataBo> getGroupStateData(String monthBegin, String monthEnd, String custName);//集团统计
    List<Map> getCustStateIssueMoney(String monthBegin, String monthEnd, String custName);
    List<Map> getGroupStateIssueMoney(String monthBegin, String monthEnd, String custName);//集团树状图
    List<Map> getGroupCustStateIssueMoney(String monthBegin, String monthEnd, String custName);//集团树状图-2
  }
