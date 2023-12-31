package com.cbrc.dashboard.shiro.filter;

import com.cbrc.dashboard.utils.LoggerUtils;
import com.cbrc.dashboard.utils.WebUtil;
import com.cbrc.dashboard.enums.NetStatusEnum;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermissionFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {

        //先判断带参数的权限判断
        Subject subject = getSubject(request, response);

        if (null != mappedValue) {
            String[] arra = (String[]) mappedValue; //使用字符串类型的权限判断
            for (String permission : arra) {
                if (subject.isPermitted(permission)) {
                    return Boolean.TRUE;
                }
            }
        }
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        /**
         * 此处是改版后，为了兼容项目不需要部署到root下，也可以正常运行，但是权限没设置目前必须到root 的URI，
         * 原因：如果你把这个项目叫 ShiroDemo，那么路径就是 /ShiroDemo/xxxx.shtml ，那另外一个人使用，又叫Shiro_Demo,那么就要这么控制/Shiro_Demo/xxxx.shtml
         * 理解了吗？
         * 所以这里替换了一下，使用根目录开始的URI
         */

        String uri = httpRequest.getRequestURI();//获取URI
        String basePath = httpRequest.getContextPath();//获取basePath
        if (null != uri && uri.startsWith(basePath)) {
            uri = uri.replaceFirst(basePath, "");
        }
        if (subject.isPermitted(uri)) {
            return Boolean.TRUE;
        }
        if (WebUtil.isAjax((HttpServletRequest) request)) {
            LoggerUtils.debug(getClass(), "当前用户没有访问该地址的权限，并且是Ajax请求！");
            ShiroFilterUtils.out(response, WebUtil.netStatus2Json(NetStatusEnum.N_401));
        }
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {//表示没有登录，重定向到登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request, response, ShiroFilterUtils.LOGIN_URL);
        } else {
            if (StringUtils.hasText(ShiroFilterUtils.UNAUTHORIZED)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response, ShiroFilterUtils.UNAUTHORIZED);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return Boolean.FALSE;
    }


    /**
     * 避免被Spring 自动注册
     *
     * @param permissionFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(PermissionFilter permissionFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(permissionFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

}
