package com.cbrc.dashboard.shiro.filter;

import com.cbrc.dashboard.utils.LoggerUtils;
import com.cbrc.dashboard.utils.WebUtil;
import com.cbrc.dashboard.dao.po.User;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.shiro.token.manager.TokenManager;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class LoginFilter extends AccessControlFilter {
    final static Class<LoginFilter> CLASS = LoginFilter.class;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {

        return Boolean.TRUE;
//        User token = TokenManager.getToken();
//
//        if (null != token || isLoginRequest(request, response)) {// && isEnabled()
//            LoggerUtils.fmtDebug(LoginFilter.CLASS, "=====进入 登陆成功 ====");
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        if (WebUtil.isAjax((HttpServletRequest) request)) {// ajax请求
            LoggerUtils.debug(getClass(), "Ajax ====> 当前用户没有登录");
            ShiroFilterUtils.out(response, WebUtil.netStatus2Json(NetStatusEnum.N_20011));
        }
        return Boolean.FALSE;
    }


    /**
     * 避免被Spring 自动注册
     *
     * @param loginFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(LoginFilter loginFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(loginFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }


}
