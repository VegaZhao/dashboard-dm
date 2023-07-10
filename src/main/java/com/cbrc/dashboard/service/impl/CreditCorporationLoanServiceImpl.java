package com.cbrc.dashboard.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.dto.HomeMapDto;
import com.cbrc.dashboard.dao.dto.PermissionDto;
import com.cbrc.dashboard.dao.mapper.CreditCoporationLoanMapper;
import com.cbrc.dashboard.dao.po.CreditCorporationLoan;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.service.impl
 * @author: Herry
 * @Date: 2020/9/16 16:00
 * @Description: TODO
 */
@Service
public class CreditCorporationLoanServiceImpl implements CreditCorporationLoanService {

    @Autowired
    private CreditCoporationLoanMapper creditCoporationLoanMapper;


    @Override
    public List<CreditCorporationLoanDto> getCreditCorporationLoanByDateRange(String monthBegin, String monthEnd) {
        //EntityWrapper<CreditCorporationLoan> wrapper = new EntityWrapper<>();
        //wrapper.between("REPORTDATE",monthBegin,monthEnd);
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCreditCorporationLoanByDateRange(monthBegin,monthEnd);

        return creditCorporationLoans;
    }

    @Override
    public List<CreditCorporationLoanDto> getBankStateData(String monthBegin, String monthEnd, String bankType, String bankName) {
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getBankStateData(monthBegin,monthEnd,bankType,bankName);

        return creditCorporationLoans;
    }

    @Override
    public List<Map> getBankStateIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName) {
        List<Map> bankStateIssueMoney = creditCoporationLoanMapper.getBankStateIssueMoney(monthBegin,monthEnd,bankType,bankName);

        return bankStateIssueMoney;
    }

    //集团矩形树图-2
    @Override
    public List<Map> getGroupCustStateIssueMoney(String monthBegin, String monthEnd, String custName) {
        List<Map> groupStateIssueMoney = creditCoporationLoanMapper.getGroupCustStateIssueMoney(monthBegin, monthEnd, custName);

        return groupStateIssueMoney;
    }

    @Override
    public List<CreditCorporationLoanDto> getCreditCorporationLoanByAssureType(String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType) {
        List<CreditCorporationLoanDto> CreditCorporationLoanByAssureType = creditCoporationLoanMapper.getCreditCorporationLoanByAssureType(monthBegin,monthEnd,assureType,loanWay,industryType,loanType);

        return CreditCorporationLoanByAssureType;
    }

    //客户排名明细表格数据获取
    @Override
    public CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnCustomer(
            String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType,String bankType,String bankName, String searchKey, String searchValue,int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> CreditCorporationLoanOnCustomer = creditCoporationLoanMapper.getCreditCorporationLoanOnCustomer(
                page,monthBegin,monthEnd,assureType,loanWay,industryType,loanType,bankType,bankName, searchKey,searchValue);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, CreditCorporationLoanOnCustomer);
        return result;
    }

    //银行排名明细表格数据获取
    @Override
    public CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnBank(String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType, String searchKey, String searchValue,int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> CreditCorporationLoanOnBank = creditCoporationLoanMapper.getCreditCorporationLoanOnBank
                (page,monthBegin,monthEnd,assureType,loanWay,industryType,loanType,searchKey,searchValue);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, CreditCorporationLoanOnBank);
        return result;
    }

    //集团-客户表20201123
    @Override
    public CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnGroup(String monthBegin, String monthEnd, String groupName, String searchKey, String searchValue,int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> CreditCorporationLoanOnGroup = creditCoporationLoanMapper.getCreditCorporationLoanOnGroup
                (page,monthBegin,monthEnd,groupName,searchKey,searchValue);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, CreditCorporationLoanOnGroup);
        return result;
    }

    @Override
    public List<CreditCorporationLoanDto> getCreditCorporationLoanByData(String dateBegin, String dateEnd) {
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCreditCorporationLoanByData(dateBegin,dateEnd);

        return creditCorporationLoans;
    }

    @Override
    public CustomPage<CreditCorporationLoanDto> getCreditCorporationTableByDay(String dateBegin, String dateEnd, String dueDate,String searchKey, String searchValue,int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCreditCorporationTableByDay(page,dateBegin,dateEnd,dueDate,searchKey, searchValue);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, creditCorporationLoans);
        return result;
    }

    @Override
    //表格金额汇总
    public List<CreditCorporationLoanDto> getCreditCorporationLoanByDay(String dateBegin, String dateEnd, String dueDate, @Param("searchKey") String searchKey, @Param("searchValue") String searchValue) {
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCreditCorporationLoanByDay(dateBegin,dateEnd,dueDate,searchKey, searchValue);

        return creditCorporationLoans;
    }

    @Override
    public List<CreditCorporationLoanDto> getCustStateData(String monthBegin, String monthEnd, String custName) {
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCustStateData(monthBegin, monthEnd, custName);

        return creditCorporationLoans;
    }

    @Override
    public List<CreditCorporationLoanDto> getGroupStateData(String monthBegin, String monthEnd, String custName) {
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getGroupStateData(monthBegin, monthEnd, custName);

        return creditCorporationLoans;
    }

    @Override
    public List<Map> getCustStateIssueMoney(String monthBegin, String monthEnd, String custName) {
        List<Map> custStateIssueMoney = creditCoporationLoanMapper.getCustStateIssueMoney(monthBegin, monthEnd, custName);

        return custStateIssueMoney;
    }

    //集团矩形树图
    @Override
    public List<Map> getGroupStateIssueMoney(String monthBegin, String monthEnd, String custName) {
        List<Map> groupStateIssueMoney = creditCoporationLoanMapper.getGroupStateIssueMoney(monthBegin, monthEnd, custName);

        return groupStateIssueMoney;
    }

    @Override
    public CustomPage<CreditCorporationLoanDto> getCustRelatData(String monthBegin, String monthEnd, String custName,int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCustRelat(page, monthBegin, monthEnd, custName);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, creditCorporationLoans);
        return result;
    }

    @Override
    public CustomPage<CreditCorporationLoanDto> getCustLoanBnak(String monthBegin, String monthEnd, String custName,int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> creditCorporationLoans = creditCoporationLoanMapper.getCustLoanBnak(page, monthBegin, monthEnd, custName);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, creditCorporationLoans);
        return result;
    }

    @Override
    public List<CreditCorporationLoanDto> getCustIssueMoneySum(String monthBegin, String monthEnd, String custName) {
        List<CreditCorporationLoanDto> custStateIssueMoney = creditCoporationLoanMapper.getCustIssueMoneySum(monthBegin, monthEnd, custName);

        return custStateIssueMoney;
    }


    @Override
    public List<CreditCorporationLoanDto> getRelatIssueMoneySum(String monthBegin, String monthEnd, String custName) {
        List<CreditCorporationLoanDto> custStateIssueMoney = creditCoporationLoanMapper.getRelatIssueMoneySum(monthBegin, monthEnd, custName);

        return custStateIssueMoney;
    }

    @Override
    public CustomPage<CreditCorporationLoanDto> getGroupRank(String monthBegin, String monthEnd, String assureType,String loanWay,String industryType,String loanType, String bankType, String bankID, String searchKey, String searchValue, int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> getGroupRank = creditCoporationLoanMapper.getGroupRank(page,monthBegin,monthEnd,assureType,loanWay,industryType,loanType, bankType, bankID, searchKey, searchValue);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, getGroupRank);
        return result;
    }

    @Override
    public List<HomeMapDto> getHomeTreeIssueMoney(String monthBegin, String monthEnd, String bankType, String bankName) {
        List<HomeMapDto> getHomeTreeIssueMoney = creditCoporationLoanMapper.getHomeTreeIssueMoney(monthBegin,monthEnd,bankType,bankName);

        return getHomeTreeIssueMoney;
    }

    @Override
    public CustomPage<CreditCorporationLoanDto> getCreditCorporationLoanOnCustDetail(String monthBegin, String monthEnd, String custName, String bankName, String assureID, String businessID, String industryID, String loanID, int startPage,int pageSize) {
        Page<CreditCorporationLoanDto> page = new Page<>(startPage, pageSize);
        List<CreditCorporationLoanDto> getCreditCorporationLoanOnCustDetail = creditCoporationLoanMapper.getCreditCorporationLoanOnCustDetail(page, monthBegin, monthEnd, custName, bankName, assureID, businessID, industryID, loanID);
        CustomPage<CreditCorporationLoanDto> result = CustomPage.getCustomPage(page, getCreditCorporationLoanOnCustDetail);
        return result;
    }
}
