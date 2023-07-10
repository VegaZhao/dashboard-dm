package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.utils.FileUtil;
import com.cbrc.dashboard.utils.LoggerUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.cbrc.dashboard.controller
 * @author: Herry
 * @Date: 2020/12/18 10:42
 * @Description: TODO
 */
@RestController
@RequestMapping("/dashboard/file")
public class FileController {


    @ApiOperation(value = "下载模板文件", notes = "下载模板文件")
    @PostMapping(value = "/download")
    public void templateDownload(HttpServletResponse response, @RequestBody JSONObject params) {

        String filename = "../files/chrome.zip";

        // 如果文件名不为空，则进行下载
        if (filename != null) {
            File file = new File(filename);
            try{
                FileUtil.fileDownload(response,file);
            }catch (IOException e) {
                LoggerUtils.error(this.getClass(),"下载" + filename + "失败",e);
            }
        }
        // return WebUtil.error("下载" + filename + "失败!");
    }
}
