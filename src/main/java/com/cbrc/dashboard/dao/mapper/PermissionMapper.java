package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cbrc.dashboard.dao.mapper.provider.PermissionSqlProvider;
import com.cbrc.dashboard.dao.po.Permission;
import com.cbrc.dashboard.dao.po.Role;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.checkerframework.common.aliasing.qual.MaybeAliased;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @SelectProvider(type = PermissionSqlProvider.class, method = "findPermissionByRoleId")
    List<Permission> findPermissionByRoleId(@Param("roleId") Integer roleId);

    @SelectProvider(type = PermissionSqlProvider.class, method = "findRoleByPermissionId")
    List<Role> findRoleByPermissionId(@Param("permissionId") Integer permissionId);

    @DeleteProvider(type = PermissionSqlProvider.class, method = "delRolePermissionRelByPermissionId")
    int delRolePermissionRelByPermissionId(@Param("permissionId") Integer permissionId);
}
