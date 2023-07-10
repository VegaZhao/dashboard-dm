package com.cbrc.dashboard.enums;

/**
 * @author: hxy
 * @date: 2017/10/24 10:16
 */
public enum NetStatusEnum {
    /*
     * 错误信息
     * */
    N_200("200", "请求成功"),

    N_400("400", "请求处理异常，请稍后再试"),

    N_401("401", "权限不足"),

    N_500("500", "请求方式有误,请检查 GET/POST"),

    N_501("501", "请求路径不存在"),

    N_10001("10001", "缺少必填参数"),

    N_10008("10008", "角色禁用或者更新失败,尚有在线用户属于此角色"),

    N_10009("10009", "账户已存在"),

    N_10010("10010", "账户删除失败"),

    N_10011("10011", "账户不存在!"),

    N_10012("10012", "角色不存在!"),

    N_10013("10013", "角色删除失败!"),

    N_10014("10014", "权限删除或更新失败,权限所属角色未失效!"),

    N_20010("20010", "用户名或者密码不正确!"),

    N_20011("20011", "登陆已过期,请重新登陆!"),

    N_20012("20012", "登陆次数过多，账户被禁用!"),

    N_20013("20013", "账户已失效!"),

    N_20014("20014", "新增操作失败!"),

    N_20015("20015", "删除操作失败!"),

    N_20016("20016", "更新操作失败!"),

    N_20017("20017", "上传文件失败!"),

    N_49999("49999", "自定义错误信息");


    private String netStatusCode;

    private String netStatusMsg;

    NetStatusEnum() {
    }

    NetStatusEnum(String sefDefErrorMsg) {
        this.netStatusCode = "49999";
        this.netStatusMsg = sefDefErrorMsg;
    }

    NetStatusEnum(String errorCode, String errorMsg) {
        this.netStatusCode = errorCode;
        this.netStatusMsg = errorMsg;
    }

    public String getNetStatusCode() {
        return netStatusCode;
    }

    public void setNetStatusCode(String netStatusCode) {
        this.netStatusCode = netStatusCode;
    }

    public String getNetStatusMsg() {
        return netStatusMsg;
    }

    public NetStatusEnum setNetStatusMsg(String netStatusMsg) {
        this.netStatusMsg = netStatusMsg;
        return this;
    }
}
