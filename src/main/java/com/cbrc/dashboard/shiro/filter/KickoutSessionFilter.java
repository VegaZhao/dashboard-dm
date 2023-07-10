package com.cbrc.dashboard.shiro.filter;

import com.cbrc.dashboard.utils.LoggerUtils;
import com.cbrc.dashboard.utils.WebUtil;
import com.cbrc.dashboard.cache.RedisOps;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.shiro.session.ShiroSessionRepository;
import com.cbrc.dashboard.shiro.token.manager.TokenManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.LinkedHashMap;

@SuppressWarnings({"unchecked", "static-access"})
public class KickoutSessionFilter extends AccessControlFilter {
    //静态注入
    static String kickoutUrl;
    //在线用户
    final static String ONLINE_USER = KickoutSessionFilter.class.getCanonicalName() + "_online_user";
    //踢出状态，true标示踢出
    final static String KICKOUT_STATUS = KickoutSessionFilter.class.getCanonicalName() + "_kickout_status";
    //static VCache cache;

    @Autowired
    private ShiroSessionRepository shiroSessionRepository;

    @Autowired
    private RedisOps redisOps;

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) {

        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        String url = httpRequest.getRequestURI();
        Subject subject = getSubject(request, response);
        //如果是相关目录 or 如果没有登录 就直接return true
        if (url.startsWith("/open/") || (!subject.isAuthenticated() && !subject.isRemembered())) {
            return Boolean.FALSE; // 原:return Boolean.TRUE
        }
        Session session = subject.getSession();
        Serializable sessionId = session.getId();
        /**
         * 判断是否已经踢出
         * 1.如果是Ajax 访问，那么给予json返回值提示。
         * 2.如果是普通请求，直接跳转到登录页
         */
        Boolean marker = (Boolean) session.getAttribute(KICKOUT_STATUS);
        if (null != marker && marker) {
            //判断是不是Ajax请求
            if (WebUtil.isAjax((HttpServletRequest) request)) {
                LoggerUtils.debug(getClass(), "当前用户已经在其他地方登录，并且是Ajax请求！");
                ShiroFilterUtils.out(response, WebUtil.netStatus2Json(NetStatusEnum.N_10009));
            }
            return Boolean.FALSE;
        }


        //从缓存获取用户-Session信息 <UserId,SessionId>
        LinkedHashMap<String, Serializable> infoMap = redisOps.get(ONLINE_USER);
        //如果不存在，创建一个新的
        infoMap = null == infoMap ? new LinkedHashMap<>() : infoMap;

        //获取tokenId
        Integer tokeId = TokenManager.getUserId();

        //如果已经包含当前Session，并且是同一个用户，跳过。
        if (infoMap.containsKey(tokeId) && infoMap.containsValue(sessionId)) {
            //更新存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
            redisOps.set(ONLINE_USER, infoMap, (long) 3600);
            return Boolean.TRUE;
        }
        //如果用户相同，Session不相同，那么就要处理了
        /**
         * 如果用户Id相同,Session不相同
         * 1.获取到原来的session，并且标记为踢出。
         * 2.继续走
         */
        if (infoMap.containsKey(tokeId.toString()) && !infoMap.containsValue(sessionId)) {
            Serializable oldSessionId = infoMap.get(tokeId.toString());
            Session oldSession = shiroSessionRepository.getSession(oldSessionId.toString());
            if (null != oldSession) {
                //标记session已经踢出
                oldSession.setAttribute(KICKOUT_STATUS, Boolean.TRUE);
                shiroSessionRepository.saveSession(oldSession);//更新session
                LoggerUtils.fmtDebug(getClass(), "kickout old session success,oldId[%s]", oldSessionId);
            } else {
                //shiroSessionRepository.deleteSession(oldSessionId.toString()); //这句是否必要，是否可以删除。
                infoMap.remove(tokeId.toString());
                //存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
                redisOps.set(ONLINE_USER, infoMap, (long) 3600);
            }
            return Boolean.TRUE;
        }

        if (!infoMap.containsKey(tokeId.toString()) && !infoMap.containsValue(sessionId)) {
            infoMap.put(tokeId.toString(), sessionId);
            //存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
            redisOps.set(ONLINE_USER, infoMap, (long) 3600);
        }
        return Boolean.TRUE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        //先退出
        Subject subject = getSubject(request, response);
        subject.logout();
        WebUtils.getSavedRequest(request);
        //再重定向
        WebUtils.issueRedirect(request, response, kickoutUrl);
        return false;
    }

    /**
     * 避免被Spring 自动注册
     *
     * @param kickoutSessionFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(KickoutSessionFilter kickoutSessionFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(kickoutSessionFilter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }

}
