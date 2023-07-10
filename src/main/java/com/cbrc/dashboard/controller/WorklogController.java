package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.service.IWorklogService;
import com.cbrc.dashboard.utils.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worklog")
public class WorklogController {
    @Autowired
    private IWorklogService worklogService;

    @PostMapping("/gettimeline")
    public JSONObject getWorklog(@RequestBody JSONObject params) {
        return WebUtil.result(worklogService.getWorklogList());
    }
}

