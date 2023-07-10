package com.cbrc.dashboard.shiro.filter;

import com.cbrc.dashboard.utils.LoggerUtils;
import com.cbrc.dashboard.utils.WebUtil;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.shiro.session.CustomSessionManager;
import com.cbrc.dashboard.shiro.session.SessionStatus;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class SimpleAuthFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {

        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        SessionStatus sessionStatus = (SessionStatus) session.getAttribute(CustomSessionManager.SESSION_STATUS);
        if (null != sessionStatus && !sessionStatus.isOnlineStatus()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        subject.logout();
        //判断是不是Ajax请求
        if (WebUtil.isAjax((HttpServletRequest) request)) {
            LoggerUtils.debug(getClass(), "Ajax ====> 当前用户已失效");
            ShiroFilterUtils.out(response, WebUtil.netStatus2Json(NetStatusEnum.N_20013));
        }

        return Boolean.FALSE;
    }


    /**
     * 避免被Spring 自动注册
     *
     * @param simpleAuthFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(SimpleAuthFilter simpleAuthFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(simpleAuthFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
}
