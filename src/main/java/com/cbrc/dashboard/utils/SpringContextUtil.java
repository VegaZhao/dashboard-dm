package com.cbrc.dashboard.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    // 实现
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) throws BeansException {
        try {
            return applicationContext.getBean(name);
        } catch (Exception e) {
            LoggerUtils.error(SpringContextUtil.class, "获取的Bean不存在，Bean名称为:" + name);
            throw e;
        }
    }

    public static <T> T getBean(String name, Class<T> requiredType)
            throws BeansException {
        try {
            return applicationContext.getBean(name, requiredType);
        } catch (Exception e) {
            LoggerUtils.error(SpringContextUtil.class, "获取的Bean不存在，Bean名称为:" + name);
            throw e;
        }

    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    public static String[] getAliases(String name)
            throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }

}