package com.cbrc.dashboard.cache;

import com.cbrc.dashboard.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.cache
 * @author: herry
 * @date: 2018-05-20  下午10:49
 * @Description: TODO
 */

@Component("redisOps")
public class RedisOps {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key
     * @return
     */
    public <K> boolean exists(K key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public <K> void del(K... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================  /**

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <K, V> V get(K key) {
        ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
        return key == null ? null : valueOperations.get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public <K, V> boolean set(K key, V value) {
        try {
            ValueOperations<K, V> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public <K, V> boolean set(K key, V value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================  /**

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================  /**

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sAdd(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sAddAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //===============================zSet===================================/**

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public boolean zAddOne(String key, Object value, double scoure) {
        try {
            return redisTemplate.opsForZSet().add(key, value, scoure);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 有序集合批量添加
     *
     * @param key
     * @param tuples
     * @return
     */
    public Long zAddBatch(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        try {
            return redisTemplate.opsForZSet().add(key, tuples);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 传入map 向有序集合批量添加
     *
     * @param key
     * @param objs
     * @return
     */
    public Long zAddBatch(String key, Map<String, Double> objs) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<>();
            for (Map.Entry<String, Double> entry : objs.entrySet()) {
                ZSetOperations.TypedTuple<Object> tuple = new DefaultTypedTuple<Object>(entry.getKey(), entry.getValue());
                tuples.add(tuple);
            }
            return redisTemplate.opsForZSet().add(key, tuples);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取set中值所处位置
     *
     * @param key
     * @param value
     * @return
     */
    public Long zRank(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 批量删除有序集合中数据
     *
     * @param key
     * @param value
     * @return
     */
    public Long zDel(String key, Object... value) {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据范围批量删除
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zRangeDel(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据索引从有序集合中获取数据
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> zRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据打分从有序集合获取数据
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> zRangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    //===============================list=================================  /**

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取key的序列化值
     *
     * @param id
     * @return
     */
    private <K> byte[] getKey(K id) {
        RedisSerializer serializer = redisTemplate.getKeySerializer();
        return serializer.serialize(id);
    }

    public <K> void setByte(K key, byte[] val) {
        byte[] keyBytes = getKey(key);
        RedisConnection redisConnection = getConnection();

        if (null != redisConnection) {
            try {
                redisConnection.set(keyBytes, val);
            } finally {
                releaseConnection(redisConnection);
            }
        } else {
            LoggerUtils.fmtError(getClass(), "获取redis连接失败，setByte");
        }
    }

    /**
     * 设置byte类型值
     *
     * @param key
     * @param val
     * @param <K>
     */
    public <K> void setByteEx(K key, byte[] val, Long expireTime) {
        byte[] keyBytes = getKey(key);
        RedisConnection redisConnection = getConnection();

        if (null != redisConnection) {
            try {
                redisConnection.setEx(keyBytes, expireTime, val);
            } finally {
                releaseConnection(redisConnection);
            }
        } else {
            LoggerUtils.fmtError(getClass(), "获取redis连接失败，setByteEx");
        }
    }

    /**
     * 获取byte类型值
     *
     * @param key
     * @param <K>
     * @return
     */
    public <K> byte[] getByte(K key) {
        byte[] keyBytes = getKey(key);
        byte[] result = null;

        RedisConnection redisConnection = getConnection();

        if (null != redisConnection) {
            try {
                result = redisConnection.get(keyBytes);
            } finally {
                releaseConnection(redisConnection);
            }
        } else {
            LoggerUtils.fmtError(getClass(), "获取redis连接失败，getByte");
        }

        return result;
    }

    /**
     * 删除指定对象
     *
     * @param key
     * @return
     */
    public <K> long delByte(K key) {
        RedisConnection redisConnection = getConnection();
        long rlt = 0L;

        if (null != redisConnection) {
            try {
                rlt = redisConnection.del(getKey(key));
            } finally {
                releaseConnection(redisConnection);
            }
        } else {
            LoggerUtils.fmtError(getClass(), "获取redis连接失败，delByte");
        }
        return rlt;
    }

    public <K> Set<byte[]> getByteByPattern(K pattern) {
        Set<byte[]> result = new HashSet<>();
        RedisConnection redisConnection = getConnection();
        if (null != redisConnection) {
            try {
                Set<K> keys = redisTemplate.keys(pattern);
                for (K key : keys) {
                    byte[] tmp = getByte(key);
                    if (tmp != null) {
                        result.add(tmp);
                    }
                }
            } catch (Exception e) {
                LoggerUtils.fmtError(getClass(), e, "批量查找session失败");
            } finally {
                releaseConnection(redisConnection);
            }
        } else {
            LoggerUtils.fmtError(getClass(), "获取redis连接失败，getByteByPattern");
        }
        return result;
    }

    /**
     * 获取连接
     *
     * @return
     */
    private RedisConnection getConnection() {
        return redisTemplate.getConnectionFactory().getConnection();
    }

    /**
     * 释放连接
     *
     * @param redisConnection
     */
    private void releaseConnection(RedisConnection redisConnection) {
        if (null != redisConnection && null != redisTemplate) {
            RedisConnectionFactory redisConnectionFactory = redisTemplate.getConnectionFactory();

            if (null != redisConnectionFactory) {
                RedisConnectionUtils.releaseConnection(redisConnection, redisConnectionFactory);
            }
        }
    }
}
