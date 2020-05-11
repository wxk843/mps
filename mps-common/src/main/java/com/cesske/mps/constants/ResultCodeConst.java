package com.cesske.mps.constants;

import com.cesske.mps.model.ServiceResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回结果中 code 值的定义
 */
@ApiModel
public final class ResultCodeConst {

    public static final String ERROR_MSG = "服务器处理失败.错误码:%s";
    /**
     * 成功
     */
    @ApiModelProperty("响应成功的code值")
    public static final int SUCCESS = ServiceResponse.SUCCESS_KEY;
    /**
     * 失败
     */
    public static final int FAULT = ServiceResponse.FAIL_KEY;

    /**
     * 需要登录
     */
    @ApiModelProperty("需要登录的code值")
    public static final int LOGIN_REQUIRED = 3;

    /**
     * 提示错误
     */
    public static final int PROMPT_ERROR = 4;

    /**
     * 权限不足
     */
    public static final int NO_PERMISSION = 5;

    /**
     * 账户无效
     */
    public static final int ACCOUNT_NOT_ENABLED = 6;

    /**
     * 需要登录
     */
    public static final int LOGIN_FIRST = 7;

    /**
     * 操作频繁
     */
    public static final int TOO_MUCH_ERROR = 8;

    /**
     * 只能下一个有效订单
     */
    public static final int HAS_VALID_ORDER = 9;
    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR = 99;

    /**
     * 服务器参数校验未通过
     */
    public static final int PARAMETER_ERROR = 100;

    /**
     * token无效
     */
    public static final int TOKEN_ERROR = 403;

    /**
     * 越权访问
     */
    public static final int NOT_AUTHORIZED = 413;
    /**
     * 活动支付检测错误
     */
    public static final int ACTIVITY_1212_ERROR = 1212;
    /**
     * 活动超时错误
     */
    public static final int ACTIVITY_1212_TIMEOUT = 1213;
    /**
     * 导出订单时候任务超出
     */
    public static final int TASK_TOO_MANY = 1214;
    private ResultCodeConst() {
    }
}
