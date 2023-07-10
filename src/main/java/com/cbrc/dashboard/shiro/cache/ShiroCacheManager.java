package com.cbrc.dashboard.shiro.cache;


import com.cbrc.dashboard.cache.RedisOps;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.shiro.cache
 * @author: herry
 * @date: 2018-05-21  下午11:13
 * @Description: TODO
 */

public class ShiroCacheManager implements CacheManager, Destroyable {

    @Autowired
    private RedisOps redisOps;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        ShiroCache shiroCache = new ShiroCache(s);
        shiroCache.setRedisOps(redisOps);
        return shiroCache;
    }

    @Override
    public void destroy() {

    }
}
