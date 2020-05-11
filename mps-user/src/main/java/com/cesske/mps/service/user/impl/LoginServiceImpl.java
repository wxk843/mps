package com.cesske.mps.service.user.impl;

import com.cesske.mps.bean.*;
import com.cesske.mps.client.IMsToolClient;
import com.cesske.mps.constants.ResultCodeConst;
import com.cesske.mps.enmus.ResultMsg;
import com.cesske.mps.enmus.SmsType;
import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.model.entity.user.User;
import com.cesske.mps.service.user.ILoginService;
import com.cesske.mps.service.user.IUserService;
import com.cesske.mps.service.user.IVerifyCodeService;
import com.cesske.mps.util.JwtUtils;
import com.cesske.mps.util.LoginUtils;
import com.cesske.mps.utils.CommonUtils;
import com.cesske.mps.utils.RegExpValidatorUtils;
import com.cesske.mps.utils.TraceId;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

/**
 * 登录、注册、TOKEN校验、修改密码 相关业务
 * @author
 */
@Slf4j
@Service
public class LoginServiceImpl implements ILoginService {

    private static final int LOGIN_TYPE_SMS_CODE = 1;
    private static final int LOGIN_TYPE_PASSWORD = 2;

    @Value("${spring.profiles.active}")
    private String profile;
    @Autowired
    private IVerifyCodeService verifyCodeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMsToolClient toolClient;
    @Autowired
    private TraceId traceId;
    @Autowired
    private LoginUtils loginUtils;

    /**
     * 登录业务
     * @param loginBean
     * @return
     */
    @Transactional
    @Override
    public ServiceResponse login(LoginBean loginBean) {
        Preconditions.checkNotNull(loginBean, "loginBean is null");
        String logFormater = String.format("login_%s_%d", loginBean.getPhone(), loginBean.getType());
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("loginBean", loginBean);
        // 验证码登录
        if(loginBean.getType() == LOGIN_TYPE_SMS_CODE) {
            if(StringUtils.isEmpty(loginBean.getCode())) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "验证码不能为空");
            }
            // 验证码校验
            if (!verifyCodeService.checkCode(loginBean.getPhone(), loginBean.getCode(),SmsType.MOBILE_LOGIN.getIndex())) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "验证码错误");
            }
            User user = userService.findOneByMobile(loginBean.getPhone());
            // 平台没有该用户
            if(user == null) {
                log.info(CommonUtils.genLogString("平台没有该用户", logFormater, logMap));
                // 获取 TOKEN 检验 CRM 上是否已经存在

            } else {
                logMap.put("ecpUser", user);
                log.info(CommonUtils.genLogString("平台存在该用户", logFormater, logMap));
                // 获取 TOKEN 检验 CRM 上是否已经存在

            }

        } else if(loginBean.getType() == LOGIN_TYPE_PASSWORD) {
            if(StringUtils.isEmpty(loginBean.getPassword())) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "密码不能为空");
            }
            String plainPassword = loginUtils.descryptPassword(loginBean.getPassword());
            if(StringUtils.isEmpty(plainPassword)) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "密码错误");
            }
            if(!RegExpValidatorUtils.checkPassword(plainPassword)) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "请输入8~20个字符,由字母和数字组成的密码");
            }
            if(!loginUtils.checkCanPasswordLogin(loginBean.getPhone())) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "您已经多次输入错误，请找回密码或通过验证码登录");
            }
            log.info(CommonUtils.genLogString("用户名密码登录方式", logFormater, logMap));
            User user = userService.findOneByMobile(loginBean.getPhone());


            log.info(CommonUtils.genLogString("用户名密码登录方式 登录失败", logFormater, logMap));
            log.info(CommonUtils.genLogString("用户名密码登录方式 登录失败", "login_error_action", logMap));
            // 登录失败，失败计数自增
            loginUtils.increaseWrongPasswordLogin(loginBean.getPhone());
            long leftRetryTimes = loginUtils.leftWrongPasswordTimes(loginBean.getPhone());
            if(leftRetryTimes > 0) {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), String.format("密码输入错误，还可尝试%d次", leftRetryTimes));
            } else {
                return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "您已经多次输入错误，请找回密码或通过验证码登录");
            }

        } else {
            log.info(CommonUtils.genLogString("不支持的登录方式", "login_error_action", logMap));
            return ServiceResponse.createFailResponse(loginBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "不支持的登录方式");
        }
        return ServiceResponse.createSuccessResponse("","");
    }

    /**
     * 退出登录业务
     * @param superId
     * @param token
     * @param traceIdStr
     * @return
     */
    @Override
    public ServiceResponse logout(String superId, String token, String traceIdStr) {
        // String logFormater = String.format("logout %s", superId);
        String logFormater = String.format("logout_%s", superId);
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("superId", superId);
        logMap.put("token", token);
        log.info(CommonUtils.genLogString("调用退出登录接口", logFormater, logMap));
        if(StringUtils.isNotEmpty(token)) {
            log.info(CommonUtils.genLogString("CRM退出登录", logFormater, logMap));
        }

        return ServiceResponse.createSuccessResponse(traceIdStr, "ok");
    }

    /**
     * 注册业务
     * @param registerBean
     * @return
     */
    @Transactional
    @Override
    public ServiceResponse register(RegisterBean registerBean) {
        Preconditions.checkNotNull(registerBean, "registerBean is null");
//        String logFormater = String.format("register %s", registerBean.getPhone());
        String logFormater = String.format("register_%s", registerBean.getPhone());
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("registerBean", registerBean);
        // 验证码校验
        if (!verifyCodeService.checkCode(registerBean.getPhone(), registerBean.getCode(),SmsType.MOBILE_REGISTER.getIndex())) {
            return ServiceResponse.createFailResponse(registerBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "验证码错误");
        }
        String plainPassword = loginUtils.descryptPassword(registerBean.getPassword());
        if(StringUtils.isEmpty(plainPassword)) {
            return ServiceResponse.createFailResponse(registerBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "密码错误");
        }
        if(!RegExpValidatorUtils.checkPassword(plainPassword)) {
            return ServiceResponse.createFailResponse(registerBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "请输入8~20个字符,由字母和数字组成的密码");
        }
        log.info(CommonUtils.genLogString("新用户注册", logFormater, logMap));
        return ServiceResponse.createSuccessResponse("","");
    }

    /**
     * 检查验证码业务
     * @param traceId
     * @param phone
     * @param code
     * @param type
     * @return
     */
    @Override
    public ServiceResponse checkCode(String traceId, String phone, String code, int type) {
        if(!RegExpValidatorUtils.isTelephone(phone)) {
            return ServiceResponse.createFailResponse(traceId, ResultCodeConst.PROMPT_ERROR, "请输入正确的手机号码");
        }
        boolean verify = verifyCodeService.checkCode(phone, code, type);
        return ServiceResponse.createSuccessResponse(traceId, verify);
    }

    /**
     * 重置密码业务
     * @param passwordUpdateBean
     * @return
     */
    @Override
    public ServiceResponse upPassword(PasswordUpdateBean passwordUpdateBean) {
        Preconditions.checkNotNull(passwordUpdateBean, "passwordUpdateBean is null");
        // 验证码校验
        if(!verifyCodeService.checkCode(passwordUpdateBean.getPhone(), passwordUpdateBean.getCode(),SmsType.MOBILE_FIND_PWD.getIndex())) {
            return ServiceResponse.createFailResponse(passwordUpdateBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "验证码错误");
        }
        String plainPassword = loginUtils.descryptPassword(passwordUpdateBean.getPassword());
        if(StringUtils.isEmpty(plainPassword)) {
            return ServiceResponse.createFailResponse(passwordUpdateBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "密码错误");
        }
        if(!RegExpValidatorUtils.checkPassword(plainPassword)) {
            return ServiceResponse.createFailResponse(passwordUpdateBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "请输入8~20个字符,由字母和数字组成的密码");
        }
//        String logFormater = String.format("upPassword %s", passwordUpdateBean.getPhone());
        String logFormater = String.format("upPassword_%s", passwordUpdateBean.getPhone());
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("passwordUpdate", passwordUpdateBean);
        return ServiceResponse.createSuccessResponse("","");
    }

    /**
     * 修改密码业务
     * @param passwordModifyBean
     * @return
     */
    @Override
    public ServiceResponse modifyPassword(PasswordModifyBean passwordModifyBean) {
        Preconditions.checkNotNull(passwordModifyBean, "passwordModifyBean is null");


        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("passwordModify", passwordModifyBean);

        String plainOldPassword = loginUtils.descryptPassword(passwordModifyBean.getOldPassword());
        String plainNewPassword = loginUtils.descryptPassword(passwordModifyBean.getNewPassword());
        if(StringUtils.isEmpty(plainOldPassword) || StringUtils.isEmpty(plainNewPassword)) {
            return ServiceResponse.createFailResponse(passwordModifyBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "密码错误");
        }
        if(!RegExpValidatorUtils.checkPassword(plainOldPassword) || !RegExpValidatorUtils.checkPassword(plainNewPassword)) {
            return ServiceResponse.createFailResponse(passwordModifyBean.getTraceId(), ResultMsg.PROMPT_ERROR.getIndex(), "请输入8~20个字符,由字母和数字组成的密码");
        }

        return ServiceResponse.createSuccessResponse(passwordModifyBean.getTraceId(), true);

    }

    /**
     * TOKEN 校验业务
     * @param token
     * @param timestamp
     * @param requestTraceId
     * @return
     */
    @Transactional
    @Override
    public ServiceResponse checkToken(String token, Long timestamp, String requestTraceId) {
//        String logFormater = String.format("checkToken %s", token);
        String logFormater = String.format("checkToken_%s", JwtUtils.parseTokenSuperId(token));
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("token", token);
        logMap.put("timestamp", timestamp);
        // 电商预售中使用的检测，直接搬过来
        if (timestamp != null) {
            log.info(CommonUtils.genLogString("checkToken带有时间戳", logFormater, logMap));
            long currentTime = System.currentTimeMillis();
            // 超过30s（暂定）则无效
            if (Math.abs(timestamp - currentTime / 1000) > 30) {
                logMap.put("currentTime", currentTime/1000);
                log.info(CommonUtils.genLogString("checkToken时间戳超时了", logFormater, logMap));
                return ServiceResponse.createFailResponse(requestTraceId, ResultMsg.LOGIN_REQUIRED.getIndex(), ResultMsg.LOGIN_REQUIRED.getMsg());
            }
            log.info(CommonUtils.genLogString("checkToken时间戳通过", logFormater, logMap));
        }


        return ServiceResponse.createSuccessResponse(requestTraceId, "user");

    }

    @Override
    public ServiceResponse checkRegisterAndSendCode(RegisterCheckSendCodeBean checkSendCodeBean) {
        ServiceResponse<Boolean> verifyResponse = toolClient.manMachineVerify(checkSendCodeBean.getTraceId(), checkSendCodeBean.getSessionId(), checkSendCodeBean.getSig(), checkSendCodeBean.getToken(), checkSendCodeBean.getScene());
        if(!RegExpValidatorUtils.isTelephone(checkSendCodeBean.getPhone())) {
            return ServiceResponse.createFailResponse(checkSendCodeBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "请输入正确的手机号码");
        }
        // 人机验证不通过
        if(!(verifyResponse.isSuccess() && verifyResponse.getData())) {
            return ServiceResponse.createFailResponse(checkSendCodeBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, ResultMsg.MAN_MACHINE_CHECK_ERROR.getMsg());
        }

        // 发送短信验证码
        ServiceResponse sendCodeResponse = toolClient.sendCodeSms(checkSendCodeBean.getTraceId(), checkSendCodeBean.getPhone(), SmsType.MOBILE_FIND_PWD.getIndex());
        if(!sendCodeResponse.isSuccess()) {
            return ServiceResponse.createFailResponse(checkSendCodeBean.getTraceId(), ResultCodeConst.PROMPT_ERROR, "发送验证码失败");
        }
        return ServiceResponse.createSuccessResponse(checkSendCodeBean.getTraceId(), true);
    }

    @Override
    public User getUserProfile(String superId, String logFormater) {
        return null;
    }


}
