package com.cbrc.dashboard.service.impl;

import com.cbrc.dashboard.service.LoginService;
import com.cbrc.dashboard.dao.dto.RoleDto;
import com.cbrc.dashboard.dao.dto.UserDto;
import com.cbrc.dashboard.dao.mapper.PermissionMapper;
import com.cbrc.dashboard.dao.mapper.RoleMapper;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.dao.po.Role;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.enums.ItemStatusEnum;
import com.cbrc.dashboard.shiro.token.manager.TokenManager;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.service.impl
 * @author: Herry
 * @Date: 2018/8/10 13:22
 * @Description: TODO
 */
@Service
public class LoginServiceImpl implements LoginService {

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    @Override
    public NetStatusEnum authLogin(User user, boolean isRememberMe) {

        try {
            User userDto = TokenManager.login(user, isRememberMe);
            return NetStatusEnum.N_200;
        } catch (IncorrectCredentialsException e) {
            return NetStatusEnum.N_20010;
        } catch (UnknownAccountException e) {
            return NetStatusEnum.N_20010;
        } catch (ExcessiveAttemptsException e) {
            return NetStatusEnum.N_20012;
        } catch (DisabledAccountException e) {
            return NetStatusEnum.N_20013;
        } catch (Exception e) {
            return NetStatusEnum.N_400;
        }
    }


    @Override
    public UserDto getUserInfo(String userName) {
        User user = TokenManager.getToken();

        if (!userName.equals(user.getUserName())) {
            return null;
        }

        List<Role> roles = roleMapper.findRolesByUserId(user.getUserId());

        List<RoleDto> roleDtos = new LinkedList<>();

        for (Role r : roles) {
            //判断角色是否有效
            if (r.getStatus() == ItemStatusEnum.VALID.getCode()) {
                RoleDto roleDto = new RoleDto();
                BeanUtils.copyProperties(r, roleDto);
                List<Permission> permissions = permissionMapper.findPermissionByRoleId(roleDto.getRoleId());
                roleDto.setPermissions(permissions);
                roleDtos.add(roleDto);
            }
        }

        UserDto result = new UserDto();
        BeanUtils.copyProperties(user, result);
        result.setRoles(roleDtos);

        return result;
    }

    @Override
    public boolean logout(String userName) {
        TokenManager.logout();
        return true;
    }
}
