package com.cbrc.dashboard.service;

import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.dto.HomeMapDto;
import com.cbrc.dashboard.entity.CustomPage;

import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.service
 * @author: Herry
 * @Date: 2020/9/16 16:00
 * @Description: TODO
 */
public interface CreditCorporationLoanService {

    List<CreditCorporationLoanDto> getCreditCorporationLoanByDateRange(String monthBegin, String monthEnd);

    List<CreditCorporationLoanDto> getBankStateData(String monthBegin, String monthEnd, String bankType, String bankName);

    List<CreditCorporationLoanDto> getCreditCorporationLoanByAssureType
            (String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType);

    CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnCustomer
            (String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType,String bankType,String bankName, String searchKey,String searchValue, int startPage, int pageSize);

    CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnBank
            (String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType,String searchKey,String searchValue, int startPage, int pageSize);

    CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnGroup
            (String monthBegin, String monthEnd, String groupName,String searchKey,String searchValue, int startPage, int pageSize);

    List<Map> getBankStateIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName);

    List<CreditCorporationLoanDto> getCreditCorporationLoanByData(String dateBegin, String dateEnd);

    CustomPage<CreditCorporationLoanDto> getCreditCorporationTableByDay(String dateBegin, String dateEnd, String dueDate, String searchKey, String searchValue,int startPage, int pageSize);

    List<CreditCorporationLoanDto> getCreditCorporationLoanByDay(String dateBegin, String dateEnd, String dueDate,String searchKey, String searchValue);

    List<CreditCorporationLoanDto> getCustStateData(String monthBegin, String monthEnd, String custName);

    List<CreditCorporationLoanDto> getGroupStateData(String monthBegin, String monthEnd, String custName);

    List<Map> getCustStateIssueMoney(String monthBegin, String monthEnd, String custName);

    List<Map> getGroupStateIssueMoney(String monthBegin, String monthEnd, String custName);//集团矩形树图

    List<Map> getGroupCustStateIssueMoney(String monthBegin, String monthEnd, String custName);//集团矩形树图

    CustomPage<CreditCorporationLoanDto> getCustRelatData(String monthBegin, String monthEnd, String custName, int startPage, int pageSize);

    CustomPage<CreditCorporationLoanDto> getCustLoanBnak(String monthBegin, String monthEnd, String custName, int startPage, int pageSize);

    List<CreditCorporationLoanDto> getCustIssueMoneySum(String monthBegin, String monthEnd, String custName);

    List<CreditCorporationLoanDto> getRelatIssueMoneySum(String monthBegin, String monthEnd, String custName);

    CustomPage<CreditCorporationLoanDto> getGroupRank(String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType, String bankType, String bankID, String searchKey, String searchValue, int startPage,int pageSize) ;

    List<HomeMapDto> getHomeTreeIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName);

    CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnCustDetail(String monthBegin, String monthEnd, String custName, String bankName, String assureID, String businessID, String industryID, String loanID, int startPage,int pageSize);
}
