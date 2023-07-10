package com.cbrc.dashboard.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cbrc.dashboard.dao.mapper.provider.UserSqlProvider;
import com.cbrc.dashboard.dao.po.User;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.dao.mapper
 * @author: herry
 * @date: 2018-05-15  下午2:10
 * @Description: TODO
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @InsertProvider(type = UserSqlProvider.class, method = "insertUserRoleRelByBatch")
    int insertUserRoleRelByBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);

    @DeleteProvider(type = UserSqlProvider.class, method = "delUserRoleRel")
    int delUserRoleRel(@Param("userId") Integer userId);

    @DeleteProvider(type = UserSqlProvider.class, method = "delUserRoleRelByRoleId")
    int delUserRoleRelByRoleId(@Param("roleId") Integer roleId);
}
