package com.cbrc.dashboard.dao.mapper.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.mapper.provider
 * @author: Herry
 * @Date: 2018/9/26 15:05
 * @Description: TODO
 */
public class UserSqlProvider {

    public String insertUserRoleRelByBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds) {
        if (roleIds.size() == 0) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("insert into user_role_rel(user_id,role_id) values");
        for (Integer roleId : roleIds) {
            sb.append("(" + userId + "," + roleId + "),");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String delUserRoleRel(@Param("userId") Integer userId) {
        return new SQL() {
            {
                DELETE_FROM("user_role_rel");
                WHERE("user_id = #{userId}");
            }
        }.toString();
    }

    public String delUserRoleRelByRoleId(@Param("roleId") Integer roleId) {
        return new SQL() {
            {
                DELETE_FROM("user_role_rel");
                WHERE("role_id = #{roleId}");
            }
        }.toString();
    }
}
