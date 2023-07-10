package com.cbrc.dashboard.utils;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.exception.CommonJsonException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: hxy
 * @description: 本后台接口系统常用的json工具类
 * @date: 2017/10/24 10:12
 */
public class CommonUtil {


    /**
     * 将request参数值转为json
     *
     * @param request
     * @return
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     *
     * @param request
     * @param requiredColumns
     * @return
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     * @param jsonObject
     * @return
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!StringUtils.isBlank(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringUtils.isBlank(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringUtils.isBlank(missCol)) {
                jsonObject.clear();
                jsonObject.put(WebUtil.CODE, NetStatusEnum.N_10001.getNetStatusCode());
                jsonObject.put(WebUtil.MESSAGE, "缺少必填参数:" + missCol.trim());
                throw new CommonJsonException(jsonObject);
            }
        }
    }

    public static <T> Map<String,T> mapKeyCaseTransform(Map<String,T> origin,String type) {
        Map<String,T> result = new HashMap<>();
        if("upper".equals(type)) {
            for(Map.Entry<String,T> iter : origin.entrySet()) {
                result.put(iter.getKey().toUpperCase(),iter.getValue());
            }
        }else if("lower".equals(type)) {
            for(Map.Entry<String,T> iter : origin.entrySet()) {
                result.put(iter.getKey().toLowerCase(),iter.getValue());
            }
        }
        return result;
    }
}
