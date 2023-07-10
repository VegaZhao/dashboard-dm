package com.cbrc.dashboard.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cbrc.dashboard.bo.UserOnlineBo;
import com.cbrc.dashboard.dao.dto.RoleDto;
import com.cbrc.dashboard.dao.dto.UserDto;
import com.cbrc.dashboard.dao.mapper.UserMapper;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.entity.CustomPage;
import com.cbrc.dashboard.enums.ItemStatusEnum;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.service.RoleService;
import com.cbrc.dashboard.service.UserService;
import com.cbrc.dashboard.shiro.token.manager.TokenManager;
import com.cbrc.dashboard.utils.RandomGenerator;
import com.cbrc.dashboard.utils.WebUtil;
import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.service.impl
 * @author: herry
 * @date: 2018-05-15  下午2:08
 * @Description: TODO
 */

@Service("UserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${shiro.hashIterations}")
    private Integer hashIterations;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;


    /**
     * 根据用户名和密码查询用户（登陆）
     *
     * @param name
     * @return
     */
    @Override
    public User getUserByName(String name) {
        User user = new User();
        user.setUserName(name);
        User result = userMapper.selectOne(user);
        return result;
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * 使用主键更新用户
     *
     * @param u
     * @return
     */
    @Override
    public int updateByPrimaryKey(User u) {
        return userMapper.updateById(u);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum addUserWithRoleIds(User user, List<Integer> roleIds) {
        user.setSalt(RandomGenerator.UUIdGen());
        SimpleHash simpleHash = new SimpleHash("md5", user.getPassword(), user.getSalt(), hashIterations);
        user.setPassword(simpleHash.toString());
        user.setRegTime(new Date());
        user.setUpdateTime(new Date());
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        if (this.selectCount(wrapper) > 0) {
            return NetStatusEnum.N_10009;
        }
        if (this.insert(user)) {
            userMapper.insertUserRoleRelByBatch(user.getUserId(), roleIds);
        }
        return NetStatusEnum.N_200;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param roleIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NetStatusEnum updateUserWithRoleIds(User user, List<Integer> roleIds) {
        TokenManager.getCustomSessionManager().changeSessionStatusByUserId(user.getUserId(), user.getStatus());
        User curUser = this.selectById(user.getUserId());
        if (null == curUser) {
            return NetStatusEnum.N_10011;
        }
        user.setUpdateTime(new Date());
        this.updateByPrimaryKey(user);
        userMapper.delUserRoleRel(user.getUserId());
        userMapper.insertUserRoleRelByBatch(user.getUserId(), roleIds);
        return NetStatusEnum.N_200;
    }


    @Override
    public JSONObject updateUserPass(User user) {
        User curUser = this.selectById(user.getUserId());
        if (curUser == null) {
            return WebUtil.error("用户不存在!");
        }
        SimpleHash simpleHash = new SimpleHash("md5", user.getPassword(), curUser.getSalt(), hashIterations);
        user.setPassword(simpleHash.toString());
        user.setUpdateTime(new Date());
        this.updateByPrimaryKey(user);
        return WebUtil.netStatus2Json(NetStatusEnum.N_200);
    }


    /**
     * 分页查找用户列表
     *
     * @param startPage
     * @param pageSize
     * @return
     */
    @Override
    public CustomPage<UserDto> selectUserListWithPage(int startPage, int pageSize,JSONObject query) {
        Page<User> page = new Page(startPage, pageSize);
        String searchKey = query.getString("searchKey");
        String searchValue = query.getString("searchValue");

        EntityWrapper<User> wrapper = new EntityWrapper<>();
        if (!Strings.isNullOrEmpty(searchKey) && !Strings.isNullOrEmpty(searchValue)) {
            wrapper.like(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, searchKey), searchValue);
        }
        Page<User> users = page.setRecords(userMapper.selectPage(page, wrapper));

        List<UserOnlineBo> userOnlineBos = TokenManager.getAllOnlineUser();

        List<UserDto> userDtos = new ArrayList<>();

        for (User u : users.getRecords()) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(u, userDto);
            List<RoleDto> roles = roleService.getRoleByUserId(u.getUserId());
            userDto.setRoles(roles);
            userDto.setStatusDesc(ItemStatusEnum.getByCode(userDto.getStatus()).getName());
            for (UserOnlineBo userOnlineBo : userOnlineBos) {
                if (userOnlineBo.getUserId().equals(userDto.getUserId())) {
                    userDto.setSessionStatus(userOnlineBo.getSessionStatus());
                }
            }
            userDtos.add(userDto);
        }
        CustomPage<UserDto> result = CustomPage.getCustomPage(page, userDtos);
        return result;
    }

    /**
     * 按照用户ID列表批量删除,同时删除用户角色关联关系
     *
     * @param ids
     * @return
     */
    @Override
    public NetStatusEnum delUserWithUserIds(List<Integer> ids) {
        for (Integer id : ids) {
            TokenManager.getCustomSessionManager().changeSessionStatusByUserId(id, 2);
        }
        if (this.deleteBatchIds(ids)) {
            for (Integer id : ids) {
                userMapper.delUserRoleRel(id);
            }
            return NetStatusEnum.N_200;
        } else
            return NetStatusEnum.N_10010;
    }
}
