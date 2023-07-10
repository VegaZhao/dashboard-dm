package com.cbrc.dashboard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cbrc.dashboard.dao.dto.PermissionDto;
import com.cbrc.dashboard.dao.mapper.PermissionMapper;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.dao.po.Role;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.enums.ItemStatusEnum;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.service.PermissionService;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.service.impl
 * @author: herry
 * @date: 2018-05-16  下午2:13
 * @Description: TODO
 */

@Service("PermissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionList() {
        EntityWrapper<Permission> wrapper = new EntityWrapper<>();
        return this.selectList(wrapper);
    }

    /**
     * 分页获取权限信息
     *
     * @param startPage
     * @param pageSize
     * @return
     */
    @Override
    public CustomPage<PermissionDto> getPermissionListWithPage(int startPage, int pageSize, JSONObject query) {
        Page<Permission> page = new Page<>(startPage, pageSize);
        String searchKey = query.getString("searchKey");
        String searchValue = query.getString("searchValue");

        EntityWrapper<Permission> wrapper = new EntityWrapper<>();
        if (!Strings.isNullOrEmpty(searchKey) && !Strings.isNullOrEmpty(searchValue)) {
            wrapper.like(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, searchKey), searchValue);
        }

        Page<Permission> permissions = this.selectPage(page, wrapper);
        List<PermissionDto> permissionDtos = new ArrayList<>();
        for (Permission p : permissions.getRecords()) {
            PermissionDto permissionDto = new PermissionDto();
            BeanUtils.copyProperties(p, permissionDto);
            permissionDtos.add(permissionDto);
        }
        CustomPage<PermissionDto> result = CustomPage.getCustomPage(page, permissionDtos);
        return result;
    }

    /**
     * 增加权限
     *
     * @param permission
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum addPermission(Permission permission) {
        this.insert(permission);
        return NetStatusEnum.N_200;
    }

    /**
     * 删除权限
     *
     * @param permissionIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum delPermission(Set<Integer> permissionIds) {
        //  if(this.isActiveRoleHasCurPermission(permissionIds))
        //      return NetStatusEnum.N_10014;
        if (this.deleteBatchIds(permissionIds)) {
            for (Integer id : permissionIds) {
                permissionMapper.delRolePermissionRelByPermissionId(id);
            }
        }
        return NetStatusEnum.N_200;
    }

    /**
     * 更新权限
     *
     * @param permission
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum updatePermission(Permission permission) {
        Set<Integer> ids = new HashSet<>();
        ids.add(permission.getPermissionId());
        //    if(isActiveRoleHasCurPermission(ids)) {
        //        return NetStatusEnum.N_10014;
        //    }
        this.updateById(permission);
        return NetStatusEnum.N_200;
    }

    /**
     * 通过角色id获取权限列表
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<PermissionDto> getPermissionByRoleIds(Set<Integer> roleIds) {
        List<PermissionDto> result = new ArrayList<>();
        for (Integer rId : roleIds) {
            List<Permission> permissions = permissionMapper.findPermissionByRoleId(rId); //获取角色所有权限信息
            for (Permission p : permissions) {
                boolean exist = Boolean.FALSE;
                for (PermissionDto pd : result) {
                    if (pd.getPermissionId() == p.getPermissionId()) {
                        exist = Boolean.TRUE;
                        break;
                    }
                }
                if (!exist) {
                    PermissionDto tmpPd = new PermissionDto();
                    BeanUtils.copyProperties(p, tmpPd);
                    result.add(tmpPd);
                }
            }
        }
        return result;
    }

    private boolean isActiveRoleHasCurPermission(Set<Integer> permissionIds) {
        for (Integer id : permissionIds) {
            List<Role> roles = permissionMapper.findRoleByPermissionId(id);
            for (Role r : roles) {
                if (ItemStatusEnum.getByCode(r.getStatus()) == ItemStatusEnum.VALID) {
                    return true;
                }
            }
        }
        return false;
    }
}
