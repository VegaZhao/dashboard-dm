package com.cbrc.dashboard.service;

import com.cbrc.dashboard.bo.BankStatDataBo;
import com.cbrc.dashboard.dao.po.BankType;

import java.util.List;
import java.util.Map;

public interface BankService {
    List<Map> getBankTypeList();
    List<Map> getBankList(String bankType);
    List<BankStatDataBo> getBankStatData(String monthBegin, String monthEnd, String bankType, String bankName);
    List<Map> getBankStateIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName);
}
