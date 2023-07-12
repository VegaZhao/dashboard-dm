package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.service.ISystemOverviewService;
import com.cbrc.dashboard.service.IVulnerabilityService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class SystemProfileController {
    @Autowired
    private IVulnerabilityService vulnerabilityService;

    @Autowired
    private ISystemOverviewService systemOverviewService;

    @PostMapping("vuln-list")
    public JSONObject getVulnerabilityList(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"systemName");
        String systemName = params.getString("systemName");
        return WebUtil.result(vulnerabilityService.getVulnerabilityList(systemName));
    }

    @ApiOperation(value = "获取系统名单", notes = "获取系统名单")
    @PostMapping("system-list")
    public JSONObject getSystemListCtrl() {
        return WebUtil.result(systemOverviewService.getSystemListSer());
    }

}

