package com.cbrc.dashboard.shiro.realm;

import com.cbrc.dashboard.service.PermissionService;
import com.cbrc.dashboard.service.RoleService;
import com.cbrc.dashboard.service.UserService;
import com.cbrc.dashboard.shiro.token.ShiroToken;
import com.cbrc.dashboard.shiro.token.manager.TokenManager;
import com.cbrc.dashboard.dao.dto.PermissionDto;
import com.cbrc.dashboard.dao.dto.RoleDto;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.enums.ItemStatusEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MyShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    public MyShiroRealm() {
        super();
    }

    /**
     * 认证信息，主要针对用户登录，
     */
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        // LoggerUtils.fmtDebug(MyShiroRealm.class,"=====进入 MyShiroRealm AuthenticationInfo ====");

        ShiroToken token = (ShiroToken) authcToken;
        User user = userService.getUserByName(token.getUsername());

        // 如果用户不存在，抛出 UnknownAccountException
        if (null == user) {
            throw new UnknownAccountException();
        }
        // 如果用户的status为禁用，抛出 DisabledAccountException
        if (user != null && user.getStatus() == ItemStatusEnum.INVALID.getCode()) {
            throw new DisabledAccountException();
        }


        //更新登录时间 last login time
        User u = new User();
        u.setUserId(user.getUserId());
        u.setUpdateTime(new Date());
        userService.updateByPrimaryKey(u);

        //使用盐加密验证
        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(
                        user,
                        user.getPassword(),
                        ByteSource.Util.bytes(user.getSalt()),
                        user.getUserName()
                );
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // LoggerUtils.fmtDebug(MyShiroRealm.class,"=====进入 MyShiroRealm AuthorizationInfo ====");

        Integer userId = TokenManager.getUserId();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据用户ID查询角色（role），放入到Authorization里。
        List<RoleDto> roles = roleService.getRoleByUserId(userId);
        Set<String> roleNames = new HashSet<>();
        Set<Integer> roleIds = new HashSet<>();
        roles.forEach(role -> {
            if (ItemStatusEnum.getByCode(role.getStatus()) == ItemStatusEnum.VALID) {
                roleIds.add(role.getRoleId());
                roleNames.add(role.getRoleName());
            }
        });
        info.setRoles(roleNames);
        //根据用户ID查询权限（permission），放入到Authorization里。
        List<PermissionDto> permissions = permissionService.getPermissionByRoleIds(roleIds);
        Set<String> permissionUrls = new HashSet<>();
        permissions.forEach(permissionDto -> permissionUrls.add(permissionDto.getActionCode()));
        info.setStringPermissions(permissionUrls);
        logger.info("当前用户权限为：" + permissionUrls);
        return info;
    }

    /**
     * 清空当前用户权限信息
     */
    public void clearCachedAuthorizationInfo() {
        PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 指定principalCollection 清除
     */
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principalCollection, getName());
        super.clearCachedAuthorizationInfo(principals);
    }
}
