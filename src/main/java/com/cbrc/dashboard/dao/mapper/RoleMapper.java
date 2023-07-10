package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cbrc.dashboard.dao.mapper.provider.RoleSqlProvider;
import com.cbrc.dashboard.dao.po.Role;
import com.cbrc.dashboard.dao.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.mapper
 * @author: herry
 * @date: 2018-05-16  下午2:10
 * @Description: TODO
 */

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @SelectProvider(type = RoleSqlProvider.class, method = "findRolesByUserId")
    List<Role> findRolesByUserId(@Param("userId") Integer userId);

    @SelectProvider(type = RoleSqlProvider.class, method = "findUsersByRoleId")
    List<User> findUsersByRoleId(@Param("roleId") Integer roleId);

    @InsertProvider(type = RoleSqlProvider.class, method = "insertRolePermissionRelByBatch")
    int insertRolePermissionRelByBatch(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds);

    @DeleteProvider(type = RoleSqlProvider.class, method = "delRolePermissionRel")
    int delRolePermissionRel(@Param("roleId") Integer roleId);
}
