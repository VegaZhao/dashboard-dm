package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.po.CreditCorporationLoan;
import com.cbrc.dashboard.service.CreditCorporationLoanService;
import com.cbrc.dashboard.service.CustomerService;
import com.cbrc.dashboard.service.HomeStatService;
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
@RequestMapping("dashboard/stat/home-stat")
public class HomeStatController {

    @Autowired
    private HomeStatService homeStatService;

    @ApiOperation(value = "获取银行矩形树图数据", notes = "获取银行矩形树图数据")
    //@RequiresPermissions(value = {"stat:bank_stat:overview", "stat:bank_stat:*"}, logical = Logical.OR)
    @PostMapping("treemapdata")
    public JSONObject getTreeMapData(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"monthBegin, monthEnd");
        String monthBegin = params.getString("monthBegin");
        String monthEnd = params.getString("monthEnd");
        String bankType = params.getString("bankType");
        String bankName = params.getString("bankName");
        return WebUtil.result(homeStatService.getHomeTreeIssueMoney(monthBegin, monthEnd, bankType, bankName));
    }
}

