package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.mapper.provider
 * @author: Herry
 * @Date: 2018/8/15 14:26
 * @Description: TODO
 */

public class RoleSqlProvider {

    public String findRolesByUserId(@Param("userId") Integer userId) {
        return new SQL() {
            {
                SELECT("r.*");
                FROM("user u");
                JOIN("user_role_rel urr on u.user_id = urr.user_id");
                JOIN("role r on urr.role_id = r.role_id");
                WHERE("u.user_id = #{userId}");
            }
        }.toString();
    }

    public String findUsersByRoleId(@Param("roleId") Integer roleId) {
        return new SQL() {
            {
                SELECT("u.*");
                FROM("user u");
                JOIN("user_role_rel urr on u.user_id = urr.user_id");
                JOIN("role r on urr.role_id = r.role_id");
                WHERE("r.role_id = #{roleId}");
            }
        }.toString();
    }

    public String insertRolePermissionRelByBatch(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds) {
        if (permissionIds.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("insert into role_permission_rel(role_id,permission_id) values");
        for (Integer permissionId : permissionIds) {
            sb.append("(" + roleId + "," + permissionId + "),");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String delRolePermissionRel(@Param("roleId") Integer roleId) {
        return new SQL() {
            {
                DELETE_FROM("role_permission_rel");
                WHERE("role_id = #{roleId}");
            }
        }.toString();
    }
}
