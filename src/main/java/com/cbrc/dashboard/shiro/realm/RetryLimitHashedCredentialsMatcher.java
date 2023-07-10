package com.cbrc.dashboard.shiro.realm;

import com.cbrc.dashboard.shiro.cache.ShiroCache;
import com.cbrc.dashboard.shiro.token.ShiroToken;
import com.cbrc.dashboard.contants.RedisPrefixConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.shiro.realm
 * @author: Herry
 * @Date: 2018/8/11 7:01
 * @Description: TODO
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //最大尝试次数
    @Getter
    @Setter
    private Integer retryMax;

    //超时时间
    @Getter
    @Setter
    private Long expireTime;

    private ShiroCache<String, Integer> cache;


    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        cache = (ShiroCache) cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        ShiroToken shiroToken = (ShiroToken) token;
        String userName = shiroToken.getUsername();
        Integer retryCount = cache.get(RedisPrefixConstants.REDIS_SHIRO_LOGIN_COUNT + userName);
        if (null == retryCount) {
            retryCount = new Integer(1);
            cache.put(RedisPrefixConstants.REDIS_SHIRO_LOGIN_COUNT + userName, retryCount, expireTime);
        } else {
            if (retryCount >= retryMax) {

                throw new ExcessiveAttemptsException(/*String.format(ResponseConstants.RES_LOGIN_NUMBER_OVER_LIMIT,retryMax,expireTime/60)*/);
            }

            cache.put(RedisPrefixConstants.REDIS_SHIRO_LOGIN_COUNT + userName, ++retryCount, expireTime);
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if (matches) {
            cache.remove(RedisPrefixConstants.REDIS_SHIRO_LOGIN_COUNT + userName);
        } else {
            throw new IncorrectCredentialsException(/*String.format(ResponseConstants.RES_LOGIN_PASSWORD_ERROR,retryCount,retryMax)*/);
        }
        return true;
    }


}
