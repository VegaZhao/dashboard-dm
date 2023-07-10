package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.dto.UserDto;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.entity.LoginParam;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.service.LoginService;
import com.cbrc.dashboard.utils.WebUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.qdbank.dm.controller
 * @author: Herry
 * @Date: 2018/8/9 14:39
 * @Description: TODO
 */
@RestController
@RequestMapping("/dashboard/login")
public class LoginController {

    @Autowired
    private LoginService loginService;



    @ApiOperation(value = "用户登录", notes = "需要输入用户名,用户密码,具备记住我功能")
    @PostMapping("auth")
    public JSONObject authLogin(@RequestBody LoginParam loginParam) {

        User userDto = new User();
        userDto.setUserName(loginParam.getUserName());
        userDto.setPassword(loginParam.getPassword());

        NetStatusEnum error = loginService.authLogin(userDto, loginParam.isRememberMe());

        if (error == NetStatusEnum.N_200) {
            Map<String, String> token = new HashMap();
            token.put("token", userDto.getUserName());
            return WebUtil.success(error.getNetStatusMsg(), token);
        } else {
            return WebUtil.netStatus2Json(error);
        }
    }

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @PostMapping("user-info")
    @RequiresUser
    public JSONObject getUserInfo(@RequestBody JSONObject params) {

        String userName = params.getString("token");

        UserDto userDto = loginService.getUserInfo(userName);
        if (userDto != null) {
            return WebUtil.result(userDto);
        } else {
            return WebUtil.error("获取用户信息失败");
        }
    }

    @ApiOperation(value = "用户退出登陆", notes = "用户退出登陆")
    @PostMapping("logout")
    @RequiresUser
    public JSONObject logout(@RequestBody JSONObject params) {
        String userName = params.getString("token");
        if (loginService.logout(userName)) {
            return WebUtil.success();
        } else {
            return WebUtil.error("退出登陆失败");
        }

    }

}
