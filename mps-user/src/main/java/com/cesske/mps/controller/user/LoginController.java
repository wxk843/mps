package com.cesske.mps.controller.user;

import com.cesske.mps.bean.*;
import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.model.entity.user.User;
import com.cesske.mps.service.user.ILoginService;
import com.cesske.mps.service.user.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录、注册相关接口
 * @author
 */
@Api(value = "登录", description = "登录相关接口")
@RestController
@RequestMapping()
public class LoginController {
    @Autowired
    private ILoginService loginService;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登录接口", notes = "用户登录操作", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ServiceResponse<Map<String, Object>> login(@Validated @RequestBody LoginBean loginBean) {
        return loginService.login(loginBean);
    }

    @ApiOperation(value = "退出登录接口", notes = "用户退出登录操作", httpMethod = "POST")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ServiceResponse logout(
            HttpServletRequest request,
            @RequestParam(value = "traceId", required = false) String traceId,
            @RequestParam(value = "wmId", required = false) String wmId) {
        String token = request.getHeader("Authorization");
        return loginService.logout(wmId, token, traceId);
    }

    @ApiOperation(value = "注册接口", notes = "用户注册操作", httpMethod = "POST")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ServiceResponse<Map<String, Object>> register(@Validated @RequestBody RegisterBean registerBean) {
        return loginService.register(registerBean);
    }

    @ApiOperation(value = "注册检测+发送短信接口", notes = "注册检测+发送短信接口", httpMethod = "POST")
    @RequestMapping(value = "/password/change/precheck", method = RequestMethod.POST)
    public ServiceResponse<Boolean> checkRegisterAndSendCode(@Validated @RequestBody RegisterCheckSendCodeBean registerCheckSendCodeBean) {
        return loginService.checkRegisterAndSendCode(registerCheckSendCodeBean);
    }

    @ApiOperation(value = "验证码校验", notes = "验证码校验操作", httpMethod = "POST")
    @RequestMapping(value = "/code/check", method = RequestMethod.POST)
    public ServiceResponse<Boolean> checkCode(
            @RequestParam(value = "traceId", required = false)
                    String traceId,
            @ApiParam(value = "手机号", required = true)
            @RequestParam(value = "phone")
                    String phone,
            @ApiParam(value = "验证码", required = true)
            @RequestParam(value = "code")
                    String code,
            @ApiParam(value = "验证码类型", required = true)
            @RequestParam(value = "codeType")
                    Integer type) {
        return loginService.checkCode(traceId, phone, code, type);
    }

    @ApiOperation(value = "登录检测", notes = "登录检测校验token", httpMethod = "GET")
    @RequestMapping(value = "/token/check", method = RequestMethod.GET)
    public ServiceResponse<User> checkToken(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "timestamp", required = false) Long timestamp,
            @RequestParam(value = "traceId", required = false) String traceId) {
        return loginService.checkToken(token, timestamp, traceId);
    }

    @ApiOperation(value = "找回密码", notes = "找回密码操作", httpMethod = "POST")
    @RequestMapping(value = "/password/update", method = RequestMethod.POST)
    public ServiceResponse<Boolean> findPassword(@Validated @RequestBody PasswordUpdateBean passwordModifyBean) {
        return loginService.upPassword(passwordModifyBean);
    }

    @ApiOperation(value = "修改密码", notes = "修改密码操作", httpMethod = "POST")
    @RequestMapping(value = "/password/modify", method = RequestMethod.POST)
    public ServiceResponse<Boolean> modifyPassword(@Validated @RequestBody PasswordModifyBean passwordModifyBean) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        if(!userService.checkWmid4Token(passwordModifyBean.getWmId(),request)){
//            return ServiceResponse.createFailResponse(null, ResultCodeConst.NOT_AUTHORIZED,"越权访问");
//        }
        return loginService.modifyPassword(passwordModifyBean);
    }
}
