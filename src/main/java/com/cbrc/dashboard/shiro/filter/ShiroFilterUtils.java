package com.cbrc.dashboard.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.utils.LoggerUtils;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ShiroFilterUtils {
    final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;
    //登录页面
    static final String LOGIN_URL = "/home/login.shtml";
    //踢出登录提示
    final static String KICKED_OUT = "/open/kickedOut.shtml";
    //没有权限提醒
    final static String UNAUTHORIZED = "/open/unauthorized.shtml";

    /**
     * response 输出JSON
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void out(ServletResponse response, JSONObject result) {

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.println(result);
        } catch (Exception e) {
            LoggerUtils.fmtError(CLAZZ, e, "输出JSON报错。");
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
