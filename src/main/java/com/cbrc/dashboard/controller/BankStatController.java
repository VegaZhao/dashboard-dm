package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.po.BankType;
import com.cbrc.dashboard.service.BankService;
import com.cbrc.dashboard.service.BankStatService;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.controller
 * @author: Herry
 * @Date: 2020/8/25 10:16
 * @Description: TODO
 */
@RestController
@RequestMapping("/dashboard/stat/bank-stat")
public class BankStatController {

    @Autowired
    private BankService bankService;

    @Autowired
    private CreditCorporationLoanService creditCorporationLoanService;

    @ApiOperation(value = "获取银行类型列表", notes = "获取银行类型列表")
    @PostMapping("bank-type-list")
    public JSONObject getBankTypeList() {
        return WebUtil.result(bankService.getBankTypeList());
    }

    @ApiOperation(value = "获取银行列表", notes = "获取银行列表")
    @PostMapping("bank-list")
    public JSONObject getBankList(@RequestBody JSONObject params) {
        String bankType = params.getString("bankType");
        return WebUtil.result(bankService.getBankList(bankType));
    }


    @ApiOperation(value = "获取多维分析统计结果", notes = "获取多维分析统计结果")
    @PostMapping("multi-stat")
    public JSONObject getBankStatData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String bankType = params.getString("bankType");
        String bankName = params.getString("bankName");
        return WebUtil.result(bankService.getBankStatData(monthBegin, monthEnd, bankType, bankName));
    }

    @ApiOperation(value = "获取银行矩形树图数据", notes = "获取银行矩形树图数据")
    //@RequiresPermissions(value = {"stat:bank_stat:overview", "stat:bank_stat:*"}, logical = Logical.OR)
    @PostMapping("treemapdata")
    public JSONObject getTreeMapData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String bankType = params.getString("bankType");
        String bankName = params.getString("bankName");
        return WebUtil.result(bankService.getBankStateIssueMoney(monthBegin, monthEnd, bankType, bankName));
    }

    @ApiOperation(value = "获取客户表格数据", notes = "获取客户表格数据")
    @RequiresPermissions(value = {"stat:bank_stat:custTable-list", "stat:bank_stat:*"}, logical = Logical.OR)
    @PostMapping("custTable-list")
    public JSONObject getCustTable(@RequestBody JSONObject params){
        CommonUtil.hasAllRequired(params,"monthBegin,monthEnd,assureType,loanWay,industryType,loanType");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String assureType = params.getString("assureType");
        String loanWay = params.getString("loanWay");
        String industryType = params.getString("industryType");
        String loanType = params.getString("loanType");
        String bankType = params.getString("bankType");
        String bankName = params.getString("bankName");
        String searchKey = params.getString("searchKey");
        String searchValue = params.getString("searchValue");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        return WebUtil.result(creditCorporationLoanService.getCreditCorporationLoanOnCustomer
                (monthBegin,monthEnd,assureType,loanWay,industryType,loanType,bankType,bankName, searchKey, searchValue,startPage,pageSize));
    }

    @ApiOperation(value = "获取集团排名表格", notes = "获取集团排名表格")
    @PostMapping("group-rank-table")
    public JSONObject getGroupRank(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd, assureType,loanWay,industryType,loanType");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String bankID = params.getString("bankID");
        String bankType = params.getString("bankType");
        String assureType = params.getString("assureType");
        String loanWay = params.getString("loanWay");
        String industryType = params.getString("industryType");
        String loanType = params.getString("loanType");
        String searchKey = params.getString("searchKey");
        String searchValue = params.getString("searchValue");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        return WebUtil.result(creditCorporationLoanService.getGroupRank(monthBegin, monthEnd, assureType, loanWay, industryType, loanType, bankType, bankID, searchKey, searchValue, startPage,pageSize));
    }
}
