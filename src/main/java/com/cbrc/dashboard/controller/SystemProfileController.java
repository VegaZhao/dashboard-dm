package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.service.IProfileService;
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
    private IProfileService profileService;

    @ApiOperation(value = "系统漏洞清单", notes = "系统漏洞清单")
    @PostMapping("vuln-table")
    public JSONObject getVulnerabilityTableBySystemCtrl(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params,"systemName");
        String systemName = params.getString("systemName");
        String searchKey = params.getString("searchKey");
        String searchValue = params.getString("searchValue");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        return WebUtil.result(profileService.getVulnerabilityTableBySystemSer(systemName,searchKey, searchValue,startPage,pageSize));
    }

    @ApiOperation(value = "获取系统名单", notes = "获取系统名单")
    @PostMapping("system-list")
    public JSONObject getSystemListCtrl() {
        return WebUtil.result(profileService.getSystemListSer());
    }

}

