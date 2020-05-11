package com.cesske.mps.enmus;

/**
 * msg 定义
 */
public enum ResultMsg {

    /**
     * 系统级别的错误码
     */
    ERROR_MSG(99, "服务器处理失败.错误码:%s"),
    FAULT(0, "失败"),
    SUCCESS(1, "成功"),
    LOGIN_REQUIRED(3, "需要登录"),
    PROMPT_ERROR(4, "提示错误"),
    NO_PERMISSION(5, "权限不足"),
    ACCOUNT_NOT_ENABLED(6, "账户无效"),
    ACCOUNT_TOKEN_FAULT(7, "token过期"),
    FREQUENT_ACCESS(8, "访问频繁"),
    DATA_IS_NULL(9, "数据不存在"),

    /**
     * 用户登录相关错误码
     */
    PARAMETER_ERROR(100, "服务器参数校验未通过"),
    NOT_REG(101, "用户还未注册"),
    LOGIN_ERROR(102, "用户名或密码错误"),
    ALREADY_REG(103, "用户名或密码不能为空"),
    REGISTER_TYPE_ERROR(104, "用户不存下"),
    LOGIN_TYPE_ERROR(105, "登录类型不存在"),
    WM_ID_IS_NULL(106, "wmId不存在!"),
    OLD_PASSWD_WRONG(107, "旧密码错误!"),
    ACCOUNT_ILLEGAL(108, "非法操作,账户异常!"),
    MAN_MACHINE_CHECK_ERROR(120, "人机验证失败!"),

    /**
     * 其他相关错误码待定应
     */

    /*
    *   三方平台相关
    * */
    TD_USER_NOT_FOUND(200, "三方平台账户不存在"),
    TD_WRONG_PASSWD(201, "三方平台密码错误"),
    TD_USER_NOT_MATCH(202, "三方平台token错误"),
    TD_PLATFORM_NO_CODE(202, "三方平台修改需要带code"),

    /*
    *   三方平台动作相关
    * */
    TA_ACTIVITY_NOT_FOUND(250, "三方平台动作不存在"),
    TA_RULLS_NOT_FOUND(251, "三方平台规则不存在"),


    /**
     * 客户维度相关
     */
    MODEL_MASTER_DATA_ATTI(300, "选项值，请选择主数据"),


    /*
    *   adapter 相关
    * */
    ADAPTER_ERROR_NO_RECORD(400,"未注册平台"),
    ADAPTER_ERROR_NOT_EHOUGH_PARAMS(401,"参数不足"),

    SUPER_ID_NOT_EXEIT(600,"superId 不存在"),

    SYSTEM_ERROR(500, "系统异常");

    /**
     * code
     */
    private int index;
    /**
     * 描述
     */
    private String msg;

    ResultMsg(int index, String msg) {
        this.index = index;
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
