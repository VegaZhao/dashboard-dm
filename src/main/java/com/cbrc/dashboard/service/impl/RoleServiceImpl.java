package com.cbrc.dashboard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cbrc.dashboard.bo.UserOnlineBo;
import com.cbrc.dashboard.dao.dto.RoleDto;
import com.cbrc.dashboard.dao.mapper.PermissionMapper;
import com.cbrc.dashboard.dao.mapper.RoleMapper;
import com.cbrc.dashboard.dao.mapper.UserMapper;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.dao.po.Role;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.enums.ItemStatusEnum;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.service.RoleService;
import com.cbrc.dashboard.shiro.token.manager.TokenManager;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.service.impl
 * @author: herry
 * @date: 2018-05-16  下午2:10
 * @Description: TODO
 */

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 通过用户ID 获取用户角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleDto> getRoleByUserId(Integer userId) {
        List<Role> roles = roleMapper.findRolesByUserId(userId);
        List<RoleDto> result = new ArrayList<>();
        for (Role role : roles) {
            RoleDto rd = new RoleDto();
            BeanUtils.copyProperties(role, rd);
            result.add(rd);
        }
        return result;
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @Override
    public List<RoleDto> getRoleList() {
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        List<Role> roles = roleMapper.selectList(wrapper);
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role r : roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(r, roleDto);
            List<Permission> permissions = permissionMapper.findPermissionByRoleId(r.getRoleId());
            roleDto.setStatusDesc(ItemStatusEnum.getByCode(r.getStatus()).getName());
            roleDto.setPermissions(permissions);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    @Override
    public CustomPage<RoleDto> getRoleListWithPage(int startPage, int pageSize, JSONObject query) {
        Page<Role> page = new Page(startPage, pageSize);
        String searchKey = query.getString("searchKey");
        String searchValue = query.getString("searchValue");

        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        if (!Strings.isNullOrEmpty(searchKey) && !Strings.isNullOrEmpty(searchValue)) {
            wrapper.like(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, searchKey), searchValue);
        }
        List<Role> roles = roleMapper.selectPage(page, wrapper);
        List<RoleDto> roleDtos = new ArrayList<>();
        for (Role r : roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(r, roleDto);
            List<Permission> permissions = permissionMapper.findPermissionByRoleId(r.getRoleId());
            roleDto.setStatusDesc(ItemStatusEnum.getByCode(r.getStatus()).getName());
            roleDto.setPermissions(permissions);
            roleDtos.add(roleDto);
        }
        CustomPage<RoleDto> result = CustomPage.getCustomPage(page, roleDtos);
        return result;
    }

    /**
     * 添加角色
     *
     * @param role
     * @param permissionIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum addRoleWithPermissionIds(Role role, List<Integer> permissionIds) {
        if (this.insert(role)) {
            roleMapper.insertRolePermissionRelByBatch(role.getRoleId(), permissionIds);
        }
        return NetStatusEnum.N_200;
    }

    /**
     * 更新角色，若存在在线用户使用该角色，则禁止更新
     *
     * @param role
     * @param permissionIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum updateRoleWithPermissionIds(Role role, List<Integer> permissionIds) {
        Role curRole = this.selectById(role.getRoleId());
        if (null == curRole) {
            return NetStatusEnum.N_10012;
        }

        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(role.getRoleId());
//        if (isActiveUserHasCurRole(roleIds))
//            return NetStatusEnum.N_10008;

        this.updateById(role);
        roleMapper.delRolePermissionRel(role.getRoleId());
        roleMapper.insertRolePermissionRelByBatch(role.getRoleId(), permissionIds);
        return NetStatusEnum.N_200;
    }


    /**
     * 删除角色，同时删除角色权限关联关系、用户角色关联关系
     * 若存在在线用户使用该角色，则禁止删除
     *
     * @param roleIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum delRoleWithRoleIds(List<Integer> roleIds) {
//        if (isActiveUserHasCurRole(roleIds))
//            return NetStatusEnum.N_10008;
        if (this.deleteBatchIds(roleIds)) {
            for (Integer roleId : roleIds) {
                roleMapper.delRolePermissionRel(roleId);
                userMapper.delUserRoleRelByRoleId(roleId);
            }
            return NetStatusEnum.N_200;
        }
        return NetStatusEnum.N_10013;
    }

    /**
     * 判断是否有在线用户使用该角色
     *
     * @param roleIds
     * @return
     */
    private boolean isActiveUserHasCurRole(List<Integer> roleIds) {
        List<UserOnlineBo> onlineBos = TokenManager.getAllOnlineUser();
        for (Integer roleId : roleIds) {
            List<User> users = roleMapper.findUsersByRoleId(roleId);
            for (UserOnlineBo userOnlineBo : onlineBos) {
                for (User u : users) {
                    if (userOnlineBo.getSessionStatus() && u.getUserId().equals(userOnlineBo.getUserId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
