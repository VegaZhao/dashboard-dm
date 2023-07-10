package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.mapper.provider
 * @author: Herry
 * @Date: 2018/8/15 15:47
 * @Description: TODO
 */
public class PermissionSqlProvider {

    public String findPermissionByRoleId(@Param("roleId") Integer roleId) {
        return new SQL() {
            {
                SELECT("p.*");
                FROM("permission p");
                JOIN("role_permission_rel rpr on p.permission_id = rpr.permission_id");
                JOIN("role r on rpr.role_id = r.role_id");
                WHERE("r.role_id = #{roleId}");
            }
        }.toString();
    }

    public String findRoleByPermissionId(@Param("permissionId") Integer permissionId) {
        return new SQL() {
            {
                SELECT("r.*");
                FROM("permission p");
                JOIN("role_permission_rel rpr on p.permission_id = rpr.permission_id");
                JOIN("role r on rpr.role_id = r.role_id");
                WHERE("p.permission_id = #{permissionId}");
            }
        }.toString();
    }

    public String delRolePermissionRelByPermissionId(@Param("permissionId") Integer permissionId) {
        return new SQL() {
            {
                DELETE_FROM("role_permission_rel");
                WHERE("permission_id = #{permissionId}");
            }
        }.toString();
    }
}
