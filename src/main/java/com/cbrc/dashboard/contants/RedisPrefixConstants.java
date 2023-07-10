package com.cbrc.dashboard.contants;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.contants
 * @author: Herry
 * @Date: 2018/8/11 6:30
 * @Description: TODO
 */
public class RedisPrefixConstants {

    // 在redis中存储的shiro缓存前缀
    public static final String REDIS_SHIRO_SESSION = "mall-shiro-session:";

    // 用户登陆尝试次数
    public static final String REDIS_SHIRO_LOGIN_COUNT = "shiro_login_count:";

    // 用户是否被锁
    public static final String REDIS_SHIRO_IS_LOCKED = "shiro_is_locked:";

}
