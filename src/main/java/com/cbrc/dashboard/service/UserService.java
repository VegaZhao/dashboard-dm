package com.cbrc.dashboard.service;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.dao.dto.UserDto;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.enums.NetStatusEnum;

import java.util.List;

public interface UserService {

    User getUserByName(String name);

    User getUserById(Integer id);

    int updateByPrimaryKey(User u);

    NetStatusEnum addUserWithRoleIds(User user, List<Integer> roleIds);

    NetStatusEnum updateUserWithRoleIds(User user, List<Integer> roleIds);

    JSONObject updateUserPass(User user);

    CustomPage<UserDto> selectUserListWithPage(int startPage, int pageSize,JSONObject query);

    NetStatusEnum delUserWithUserIds(List<Integer> ids);
}