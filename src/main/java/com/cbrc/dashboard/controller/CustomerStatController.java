package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.po.CreditCorporationLoan;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.service.CustomerService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard/stat/customer-stat")
public class CustomerStatController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CreditCorporationLoanService  creditCorporationLoanService;

    @ApiOperation(value = "获取集团列表", notes = "获取集团列表")
    @PostMapping("group-list")
    public JSONObject getGroupList(@RequestBody JSONObject params) {
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        return WebUtil.result(customerService.getGroupList(monthBegin, monthEnd));
    }

    @ApiOperation(value = "获取公司列表", notes = "获取公司列表")
    @PostMapping("cust-list")
    public JSONObject getCustList(@RequestBody JSONObject params) {
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        Integer num = params.getInteger("num");
        return WebUtil.result(customerService.getCustList(monthBegin, monthEnd, custName, num));
    }

    @ApiOperation(value = "获取集团选择器列表", notes = "获取集团选择器列表")
    @PostMapping("groupCust-list")
    public JSONObject getGroupCustList(@RequestBody JSONObject params) {
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String groupName = params.getString("groupName");
        Integer num = params.getInteger("num");
        return WebUtil.result(customerService.getGroupCustList(monthBegin, monthEnd, groupName, num));
    }

    @ApiOperation(value = "获取客户多维分析统计结果", notes = "获取客户多维分析统计结果")
    @PostMapping("multi-stat")
    public JSONObject getCustStatData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        return WebUtil.result(customerService.getCustStateData(monthBegin, monthEnd, custName));
    }

    @ApiOperation(value = "获取集团多维分析统计结果", notes = "获取集团多维分析统计结果")
    @PostMapping("group-multi-stat")
    public JSONObject getGroupStatData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        return WebUtil.result(customerService.getGroupStateData(monthBegin, monthEnd, custName));
    }

    @ApiOperation(value = "集团-客户表格数据", notes = "集团-客户表格数据")
    @RequiresPermissions(value = {"stat:customer_stat:groupTable-list", "stat:customer_stat:*"}, logical = Logical.OR)
    @PostMapping("groupTable-list")
    public JSONObject getCreditCorporationLoanOnGroup(@RequestBody JSONObject params){
        CommonUtil.hasAllRequired(params,"monthBegin,monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String groupName = params.getString("groupName");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        String searchKey = params.getString("searchKey");
        String searchValue = params.getString("searchValue");
        return WebUtil.result(creditCorporationLoanService.getCreditCorporationLoanOnGroup
                (monthBegin,monthEnd,groupName,searchKey,searchValue,startPage,pageSize));
    }

    @ApiOperation(value = "获取公司矩形树图数据", notes = "获取公司矩形树图数据")
    @PostMapping("treemapdata")
    public JSONObject getTreeMapData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        return WebUtil.result(customerService.getCustStateIssueMoney(monthBegin, monthEnd, custName));
    }

    @ApiOperation(value = "获取集团矩形树图数据", notes = "获取集团矩形树图数据")
    @PostMapping("grouptreedata")
    public JSONObject getGroupStateIssueMoney(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        //return WebUtil.result(customerService.getGroupStateIssueMoney(monthBegin, monthEnd, custName));集团树状图
        return WebUtil.result(customerService.getGroupCustStateIssueMoney(monthBegin, monthEnd, custName));//集团树状图-2
    }

    @ApiOperation(value = "获取关联公司明细", notes = "获取关联公司明细")
    @PostMapping("related-detail")
    public JSONObject getCustomerRelatTable(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        return WebUtil.result(creditCorporationLoanService.getCustRelatData(monthBegin, monthEnd, custName, startPage, pageSize));
    }

    @ApiOperation(value = "获取公司贷款银行明细", notes = "获取公司贷款银行明细")
    @PostMapping("loan-bank-detail")
    public JSONObject getCustLoanBankTable(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        return WebUtil.result(creditCorporationLoanService.getCustLoanBnak(monthBegin, monthEnd, custName, startPage, pageSize));
    }

    @ApiOperation(value = "获取公司汇总贷款金额", notes = "获取公司汇总贷款金额")
    @PostMapping("cust-issuemoney-sum")
    public JSONObject getCustIssueMoneySum(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        return WebUtil.result(creditCorporationLoanService.getCustIssueMoneySum(monthBegin, monthEnd, custName));
    }

    @ApiOperation(value = "获取关联公司汇总贷款金额", notes = "获取关联公司汇总贷款金额")
    @PostMapping("relat-issuemoney-sum")
    public JSONObject getRelatIssueMoneySum(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        return WebUtil.result(creditCorporationLoanService.getRelatIssueMoneySum(monthBegin, monthEnd, custName));
    }

    @ApiOperation(value = "获取新发放贷款明细", notes = "获取新发放贷款明细")
    @PostMapping("customer-loan-detail")
    public JSONObject getCreditCorporationLoanOnCustDetail(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String custName = params.getString("custName");
        String bankName = params.getString("bankName");
        String assureID = params.getString("assureID");
        String businessID = params.getString("businessID");
        String industryID = params.getString("industryID");
        String loanID = params.getString("loanID");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        return WebUtil.result(creditCorporationLoanService.getCreditCorporationLoanOnCustDetail(monthBegin, monthEnd, custName, bankName, assureID, businessID, industryID, loanID, startPage, pageSize));
    }

}
