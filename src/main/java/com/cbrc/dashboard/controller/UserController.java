package com.cbrc.dashboard.controller;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.service.UserService;
import com.cbrc.dashboard.utils.CommonUtil;
import com.cbrc.dashboard.utils.WebUtil;
import com.cbrc.dashboard.dao.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.qdbank.dm.controller
 * @author: Herry
 * @Date: 2018/8/20 20:29
 * @Description: TODO
 */

@RestController
@RequestMapping("/dashboard/system/user-manager")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息列表", notes = "获取用户信息列表，信息中带有用户角色等")
    @RequiresPermissions("system:user_manage:list")
    @PostMapping("user-list")
    public JSONObject getUserList(@RequestBody JSONObject params) {
        CommonUtil.hasAllRequired(params, "startPage,pageSize,query");
        int startPage = params.getInteger("startPage");
        int pageSize = params.getInteger("pageSize");
        JSONObject query = params.getJSONObject("query");
        CustomPage<UserDto> data = userService.selectUserListWithPage(startPage, pageSize,query);
        return WebUtil.result(data);
    }


    @ApiOperation(value = "添加用户", notes = "添加用户")
    @RequiresPermissions("system:user_manage:add")
    @PostMapping("user-add")
    public JSONObject addUser(@RequestBody JSONObject user) {
        CommonUtil.hasAllRequired(user, "userName,password,status,roles");
        User u = user.toJavaObject(User.class);
        List<Integer> roleIds = (List<Integer>) user.get("roles");
        return WebUtil.netStatus2Json(userService.addUserWithRoleIds(u, roleIds));
    }

    @ApiOperation(value = "删除用户", notes = "根据用户ID列表删除用户")
    @RequiresPermissions("system:user_manage:delete")
    @PostMapping("user-del")
    public JSONObject delUser(@RequestBody JSONObject userIds) {
        CommonUtil.hasAllRequired(userIds, "userIds");
        List<Integer> ids = (List<Integer>) userIds.get("userIds");
        return WebUtil.netStatus2Json(userService.delUserWithUserIds(ids));
    }


    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @RequiresPermissions("system:user_manage:update")
    @PostMapping("user-update")
    public JSONObject changeUserStatus(@RequestBody JSONObject user) {
        CommonUtil.hasAllRequired(user, "userId,userName,status,roles");
        User u = user.toJavaObject(User.class);
        List<Integer> roleIds = (List<Integer>) user.get("roles");
        return WebUtil.netStatus2Json(userService.updateUserWithRoleIds(u, roleIds));
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @RequiresPermissions("system:user_manage:update")
    @PostMapping("user-change-pass")
    public JSONObject changePassword(@RequestBody JSONObject pass) {
        CommonUtil.hasAllRequired(pass, "userId,passwordInChangePass,passwordCheckInChangePass");
        if (!pass.getString("passwordInChangePass").equals(pass.getString("passwordCheckInChangePass"))) {
            return WebUtil.error("两次密码不一致!");
        }
        Integer userId = pass.getInteger("userId");
        String password = pass.getString("passwordInChangePass");
        User u = new User();
        u.setUserId(userId);
        u.setPassword(password);
        return userService.updateUserPass(u);
    }
}
