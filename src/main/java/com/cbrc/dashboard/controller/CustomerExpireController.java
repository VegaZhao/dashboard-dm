package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.service.CustomerExpireService;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/dashboard/stat/customer-expire")
public class CustomerExpireController {

    @Autowired
    private CustomerExpireService customerExpireService;

    @Autowired
    private CreditCorporationLoanService CreditCorporationLoanService;

        @ApiOperation(value = "贷款到期折线图", notes = "贷款到期折线图")
//    @RequiresPermissions(value = {"stat:customer_expire:customer-expire-line", "stat:customer_expire:*"}, logical = Logical.OR)
    @PostMapping("customer-expire-line")
    public JSONObject getCustomerExpireLineData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"dateBegin,dateEnd");
        String dateBegin = params.getString("dateBegin");
        String dateEnd = params.getString("dateEnd");

        return WebUtil.result(customerExpireService.getCustomerExpireLineData(dateBegin,dateEnd));
    }

    @ApiOperation(value = "贷款到期明细", notes = "贷款到期明细")
//    @RequiresPermissions(value = {"stat:customer_expire:customer-expire", "stat:customer_expire:*"}, logical = Logical.OR)
    @PostMapping("customer-expire")
    public JSONObject getCreditCorporationTableByDay(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"dateBegin,dateEnd");
        String dateBegin = params.getString("dateBegin");
        String dateEnd = params.getString("dateEnd");
        String dueDate = params.getString("dueDate");
        String searchKey = params.getString("searchKey");
        String searchValue = params.getString("searchValue");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");

        return WebUtil.result(CreditCorporationLoanService.getCreditCorporationTableByDay(dateBegin,dateEnd,dueDate,searchKey, searchValue,startPage,pageSize));
    }

    @ApiOperation(value = "贷款到期明细金额汇总", notes = "贷款到期明细金额汇总")
//    @RequiresPermissions(value = {"stat:customer_expire:customer-expire-line", "stat:customer_expire:*"}, logical = Logical.OR)
    @PostMapping("customer-expire-table")
    public JSONObject getCustomerExpireTable(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"dateBegin,dateEnd");
        String dateBegin = params.getString("dateBegin");
        String dateEnd = params.getString("dateEnd");
        String dueDate = params.getString("dueDate");
        String searchKey = params.getString("searchKey");
        String searchValue = params.getString("searchValue");

        return WebUtil.result(CreditCorporationLoanService.getCreditCorporationLoanByDay(dateBegin,dateEnd,dueDate,searchKey, searchValue));
    }
}
