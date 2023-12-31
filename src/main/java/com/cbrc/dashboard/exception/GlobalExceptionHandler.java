package com.cbrc.dashboard.exception;

import com.alibaba.fastjson.JSONObject;
import com.cbrc.dashboard.enums.NetStatusEnum;
import com.cbrc.dashboard.utils.StringUtils;
import com.cbrc.dashboard.utils.WebUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: herry
 * @description: 统一异常拦截
 * @date: 2017/10/24 10:31
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("GlobalExceptionHandler");

    @ExceptionHandler(value = Exception.class)
    public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) {
        String errorPosition = "";
        //如果错误堆栈信息存在
        if (e.getStackTrace().length > 0) {
            StackTraceElement element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
            int lineNumber = element.getLineNumber();
            errorPosition = fileName + ":" + lineNumber;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(WebUtil.CODE, NetStatusEnum.N_400.getNetStatusCode());

        if(StringUtils.isBlank(e.getMessage())){
            jsonObject.put(WebUtil.MESSAGE, NetStatusEnum.N_400.getNetStatusMsg());
        }
        else{
            jsonObject.put(WebUtil.MESSAGE, e.getMessage());
        }

        JSONObject errorObject = new JSONObject();
        errorObject.put("errorLocation", e.toString() + "    错误位置:" + errorPosition);
        jsonObject.put("returnData", errorObject);
        logger.error("异常", e);
        return jsonObject;
    }

    /**
     * GET/POST请求方法错误的拦截器
     * 因为开发时可能比较常见,而且发生在进入controller之前,上面的拦截器拦截不到这个错误
     * 所以定义了这个拦截器
     *
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JSONObject httpRequestMethodHandler() {
        return WebUtil.netStatus2Json(NetStatusEnum.N_500);
    }

    /**
     * 本系统自定义错误的拦截器
     * 拦截到此错误之后,就返回这个类里面的json给前端
     * 常见使用场景是参数校验失败,抛出此错,返回错误信息给前端
     *
     * @param commonJsonException
     * @return
     */
    @ExceptionHandler(CommonJsonException.class)
    public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
        return commonJsonException.getResultJson();
    }

    /**
     * 权限不足报错拦截
     *
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public JSONObject unauthorizedExceptionHandler() {
        return WebUtil.netStatus2Json(NetStatusEnum.N_401);
    }

    /**
     * 未登录报错拦截
     * 在请求需要权限的接口,而连登录都还没登录的时候,会报此错
     *
     * @return
     */
    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public JSONObject unauthenticatedException() {
        return WebUtil.netStatus2Json(NetStatusEnum.N_20011);
    }
}
