package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cbrc.dashboard.dao.dto.CreditCorporationLoanDto;
import com.cbrc.dashboard.dao.dto.HomeMapDto;
import com.cbrc.dashboard.dao.mapper.provider.CreditCoporationLoanProvider;
import com.cbrc.dashboard.dao.po.CreditCorporationLoan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.dao.mapper
 * @author: Herry
 * @Date: 2020/9/11 10:38
 * @Description: TODO
 */
@Mapper
public interface CreditCoporationLoanMapper extends BaseMapper<CreditCorporationLoan> {


    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanByDateRange")
    List<CreditCorporationLoanDto> getCreditCorporationLoanByDateRange(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getBankStateData")
    List<CreditCorporationLoanDto> getBankStateData(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("bankType") String bankType, @Param("bankName") String bankName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanByAssureType")
    List<CreditCorporationLoanDto> getCreditCorporationLoanByAssureType(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
                                                                        @Param("assureType") String assureType, @Param("loanWay") String loanWay,
                                                                        @Param("industryType") String industryType, @Param("loanType") String loanType);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanOnCustomer")
    List<CreditCorporationLoanDto> getCreditCorporationLoanOnCustomer(Page page,@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
                                                                  @Param("assureType") String assureType, @Param("loanWay") String loanWay,
                                                                  @Param("industryType") String industryType, @Param("loanType") String loanType,
                                                                      @Param("bankType") String bankType, @Param("bankName") String bankName,
                                                                   @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);


    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanOnBank")
    List<CreditCorporationLoanDto> getCreditCorporationLoanOnBank(Page page,@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
                                                                        @Param("assureType") String assureType, @Param("loanWay") String loanWay,
                                                                        @Param("industryType") String industryType, @Param("loanType") String loanType,
                                                                        @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);

    //集团-客户表
    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanOnGroup")
    List<CreditCorporationLoanDto> getCreditCorporationLoanOnGroup(Page page,@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
                                                                    @Param("groupName") String groupName,
                                                                  @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getBankStateIssueMoney")
    List<Map> getBankStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("bankType") String bankType, @Param("bankName") String bankName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanByData")
    List<CreditCorporationLoanDto> getCreditCorporationLoanByData(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);

    //表格金额汇总
    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanByDay")
    List<CreditCorporationLoanDto> getCreditCorporationLoanByDay(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd,@Param("dueDate") String dueDate,
                                                                 @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCustStateData")
    List<CreditCorporationLoanDto> getCustStateData(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getGroupStateData")
    List<CreditCorporationLoanDto> getGroupStateData(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationTableByDay")
    List<CreditCorporationLoanDto> getCreditCorporationTableByDay(Page page,@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd,@Param("dueDate") String dueDate,
                                                                  @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCustStateIssueMoney")
    List<Map> getCustStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    //集团树状图
    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getGroupStateIssueMoney")
    List<Map> getGroupStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    //集团树状图-2
    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getGroupCustStateIssueMoney")
    List<Map> getGroupCustStateIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCustRelat")
    List<CreditCorporationLoanDto> getCustRelat(Page page, @Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCustLoanBnak")
    List<CreditCorporationLoanDto> getCustLoanBnak(Page page, @Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCustIssueMoneySum")
    List<CreditCorporationLoanDto> getCustIssueMoneySum(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getRelatIssueMoneySum")
    List<CreditCorporationLoanDto> getRelatIssueMoneySum(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("custName") String custName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getGroupRank")
    List<CreditCorporationLoanDto> getGroupRank(Page page,@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
                                                @Param("assureType") String assureType, @Param("loanWay") String loanWay,
                                                @Param("industryType") String industryType, @Param("loanType") String loanType,
                                                @Param("bankType") String bankType, @Param("bankID") String bankID,
                                                @Param("searchKey") String searchKey, @Param("searchValue") String searchValue);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getHomeTreeIssueMoney")
    List<HomeMapDto> getHomeTreeIssueMoney(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd, @Param("bankType") String bankType, @Param("bankName") String bankName);

    @SelectProvider(type = CreditCoporationLoanProvider.class, method = "getCreditCorporationLoanOnCustDetail")
    List<CreditCorporationLoanDto> getCreditCorporationLoanOnCustDetail(Page page, @Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd,
                                                                        @Param("custName") String custName, @Param("bankName") String bankName,
                                                                        @Param("assureID") String assureID, @Param("businessID") String businessID,
                                                                        @Param("industryID") String industryID, @Param("loanID") String loanID);

}
