package com.cbrc.dashboard.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.cache
 * @author: herry
 * @date: 2018-05-20  下午9:14
 * @Description: TODO
 */

public class RedisCacheOps extends AbstractCacheOps {

    @Autowired
    private CacheManager cacheManager;

    public Cache getCache(String key) {
        return cacheManager.getCache(key);
    }

    public boolean put(String key, Object val) {
        return putCache(getCache(key), key, val);
    }

    public Object get(String key) {
        return getFromCache(getCache(key), key);
    }

    public boolean evict(String key) {
        return evictFromCache(getCache(key), key);
    }

}
