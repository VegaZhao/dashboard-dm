package com.cbrc.dashboard.utils;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.enums.NetStatusEnum;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    public static final String DATA = "data";

    public static final String MESSAGE = "msg";

    public static final String CODE = "code";

    /**
     * 封装结果
     *
     * @param object
     * @return
     */
    public static JSONObject result(Object object) {
        JSONObject result = new JSONObject();
        result.put(DATA, object);
        result.put(CODE, NetStatusEnum.N_200.getNetStatusCode());
        return result;
    }

    /**
     * 成功
     *
     * @return
     */
    public static JSONObject success() {
        JSONObject result = new JSONObject();
        result.put(MESSAGE, NetStatusEnum.N_200.getNetStatusMsg());
        result.put(CODE, NetStatusEnum.N_200.getNetStatusCode());
        return result;
    }

    public static JSONObject success(String msg) {
        JSONObject result = new JSONObject();
        result.put(MESSAGE, msg);
        result.put(CODE, NetStatusEnum.N_200.getNetStatusCode());
        return result;
    }

    public static JSONObject success(String msg, Object object) {
        JSONObject result = new JSONObject();
        result.put(DATA, object);
        result.put(MESSAGE, msg);
        result.put(CODE, NetStatusEnum.N_200.getNetStatusCode());
        return result;
    }

    /**
     * 内部处理错误
     *
     * @param msg
     * @return
     */
    public static JSONObject error(String msg) {
        JSONObject result = new JSONObject();
        result.put(MESSAGE, msg);
        result.put(CODE, NetStatusEnum.N_400.getNetStatusCode());
        return result;
    }

    /**
     * 内部处理错误
     *
     * @param msg
     * @return
     */
    public static JSONObject error(int errorCode, String msg) {
        JSONObject result = new JSONObject();
        result.put(MESSAGE, msg);
        result.put(CODE, errorCode);
        return result;
    }

    /**
     * 内部处理错误
     *
     * @param object
     * @return
     */
    public static JSONObject error(int errorCode, String msg, Object object) {
        JSONObject result = new JSONObject();
        result.put(DATA, object);
        result.put(MESSAGE, msg);
        result.put(CODE, errorCode);
        return result;
    }

    /**
     * 返回错误信息JSON
     *
     * @param errorEnum 错误码的errorEnum
     * @return
     */
    public static JSONObject netStatus2Json(NetStatusEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put(CODE, errorEnum.getNetStatusCode());
        resultJson.put(MESSAGE, errorEnum.getNetStatusMsg());
        return resultJson;
    }


    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        return requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest");
    }
}
