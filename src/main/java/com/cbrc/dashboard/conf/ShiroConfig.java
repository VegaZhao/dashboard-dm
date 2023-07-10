package com.cbrc.dashboard.conf;

import com.cbrc.dashboard.shiro.cache.ShiroCacheManager;
import com.cbrc.dashboard.shiro.filter.*;
import com.cbrc.dashboard.shiro.listenter.ShiroSessionListener;
import com.cbrc.dashboard.shiro.realm.MyShiroRealm;
import com.cbrc.dashboard.shiro.realm.RetryLimitHashedCredentialsMatcher;
import com.cbrc.dashboard.shiro.session.*;
import com.cbrc.dashboard.utils.LoggerUtils;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.conf
 * @author: herry
 * @date: 2018-05-16  上午10:11
 * @Description: TODO
 */

@Data
@Configuration
public class ShiroConfig {

    @Value("${shiro.credentialsRetryMax}")
    private Integer credentialsRetryMax;

    @Value("${shiro.hashAlgorithmName}")
    private String hashAlgorithmName;

    @Value("${shiro.hashIterations}")
    private Integer hashIterations;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        LoggerUtils.debug(ShiroConfig.class, "=====加载Shiro配置====");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置filterMap
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("login", new LoginFilter());
        filterMap.put("role", new RoleFilter());
        filterMap.put("simple", new SimpleAuthFilter());
        filterMap.put("permission", new PermissionFilter());
        filterMap.put("kickout", new KickoutSessionFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //swagger页面
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");

        // 配置不会被拦截的连接，按照配置的顺序执行
        // authc:所有链接必须认证通过才能访问，anon则所有url都可以访问
        // 过滤链定义，从上向下执行，一般将**放到最下面
        filterChainDefinitionMap.put("/dashboard/", "anon");
        filterChainDefinitionMap.put("/dashboard/static/**", "anon");
        filterChainDefinitionMap.put("/dashboard/login/test", "anon");
        filterChainDefinitionMap.put("/dashboard/login/auth", "anon");
        filterChainDefinitionMap.put("/dashboard/login/logout", "anon");
        filterChainDefinitionMap.put("/file/**", "anon");

        // 测试接口
        filterChainDefinitionMap.put("/dashboard/test/**", "anon");

        filterChainDefinitionMap.put("/**", "login,simple");

        //设置过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * realm
     *
     * @return
     */
    @Bean("myShiroRealm")
    public MyShiroRealm myShiroRealm(
            @Qualifier("redisCacheManager") CacheManager cacheManager,
            @Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCacheManager(cacheManager);
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myShiroRealm;
    }

    /**
     * 安全管理器
     *
     * @return
     */
    @Bean("securityManager")
    public SecurityManager securityManager(
            @Qualifier("myShiroRealm") MyShiroRealm myShiroRealm,
            @Qualifier("cookieRememberMeManager") CookieRememberMeManager cookieRememberMeManager,
            @Qualifier("sessionManager") DefaultSessionManager sessionManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myShiroRealm);
        defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        return defaultWebSecurityManager;
    }

    @Bean("sessionManager")
    public DefaultSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionValidationInterval(1800000);//相隔多久检查一次session的有效性 单位毫秒 当前是半小时
        defaultWebSessionManager.setGlobalSessionTimeout(5 * 60 * 60 * 1000); //session 有效时间为5小时 （毫秒单位）
        defaultWebSessionManager.setSessionDAO(shiroSessionDAO()); //设置sessionDao
        List<SessionListener> listeners = new ArrayList<>();
        listeners.add(shiroSessionListener());
        defaultWebSessionManager.setSessionListeners(listeners); //设置session监听器
        defaultWebSessionManager.setSessionValidationSchedulerEnabled(true); //是否开启 检测，默认开启
        //defaultWebSessionManager.setSessionValidationScheduler(sessionValidationScheduler()); //间隔多少时间检查，不配置是60分钟
        defaultWebSessionManager.setDeleteInvalidSessions(true); //是否删除无效的，默认也是开启
        defaultWebSessionManager.setSessionFactory(new MallSessionFactory());
        //defaultWebSessionManager.setSessionIdCookie(sessionIdCookie()); //会话Cookie模板
        return defaultWebSessionManager;
    }

    @Bean("redisCacheManager")
    public CacheManager cacheManager() {
        ShiroCacheManager redisCacheManager = new ShiroCacheManager();
        return redisCacheManager;
    }


    @Bean("shiroSessionDAO")
    public ShiroSessionDAO shiroSessionDAO() {
        return new ShiroSessionDAO();
    }

    @Bean("shiroSessionRepository")
    public ShiroSessionRepository shiroSessionRepository() {
        return new ShiroSessionRepositoryImpl();
    }

    @Bean("customSessionManager")
    public CustomSessionManager customSessionManager() {
        return new CustomSessionManager();
    }

    @Bean
    public ShiroSessionListener shiroSessionListener() {
        return new ShiroSessionListener();
    }

    @Bean
    public SessionValidationScheduler sessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler executorServiceSessionValidationScheduler =
                new ExecutorServiceSessionValidationScheduler();
        executorServiceSessionValidationScheduler.setInterval(1800000); //每半个小时检查一次
        //executorServiceSessionValidationScheduler.setSessionManager(sessionManager());
        return executorServiceSessionValidationScheduler;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("v_v-re-mall");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000); //30天时间，记住我30天
        return simpleCookie;
    }

    /**
     * 记住我管理器 cookie管理对象;
     *
     * @return
     */
    @Bean("cookieRememberMeManager")
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("dlaioje@982k#lsdlao_122%"));
        return cookieRememberMeManager;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(
            @Qualifier("redisCacheManager") CacheManager cacheManager) {
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        retryLimitHashedCredentialsMatcher.setRetryMax(credentialsRetryMax); //最大尝试次数
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName); // 散列算法:这里使用MD5算法;
        retryLimitHashedCredentialsMatcher.setHashIterations(hashIterations);// 散列的次数
        retryLimitHashedCredentialsMatcher.setExpireTime((long) 600); //超时时间10分钟(若调用超时put方法)
        return retryLimitHashedCredentialsMatcher;
    }


    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}