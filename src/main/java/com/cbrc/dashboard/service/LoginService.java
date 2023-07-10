package com.cbrc.dashboard.service;

import com.cbrc.dashboard.dao.dto.UserDto;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.enums.NetStatusEnum;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.service
 * @author: Herry
 * @Date: 2018/8/9 20:47
 * @Description: TODO
 */

public interface LoginService {

    NetStatusEnum authLogin(User user, boolean isRememberMe);

    UserDto getUserInfo(String userName);

    boolean logout(String userName);
}
