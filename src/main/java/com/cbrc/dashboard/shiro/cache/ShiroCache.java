package com.cbrc.dashboard.shiro.cache;

import com.cbrc.dashboard.cache.RedisOps;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.shiro.cache
 * @author: herry
 * @date: 2018-05-21  下午11:24
 * @Description: TODO
 */

public class ShiroCache<K, V> implements Cache<K, V> {

    @Getter
    @Setter
    private RedisOps redisOps;

    @Getter
    @Setter
    private String name;

    public ShiroCache(String name) {
        this.name = name;
    }

    @Override
    public V get(K k) throws CacheException {
        return redisOps.get(k);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisOps.set(k, v);
        return v;
    }

    /**
     * 设置该条记录的超时时间，单位是秒
     *
     * @param k
     * @param v
     * @param expireTime
     * @return
     * @throws CacheException
     */
    public V put(K k, V v, Long expireTime) throws CacheException {
        redisOps.set(k, v, expireTime);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V result = redisOps.get(k);
        redisOps.del(k);
        return result;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        if (keys() == null) {
            return 0;
        } else {
            return keys().size();
        }
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

}
