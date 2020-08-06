package com.cesske.mps.client;

import com.cesske.mps.client.impl.MsToolClientHystrix;
import com.cesske.mps.config.FeignConfig;
import com.cesske.mps.config.MultipartSupportConfig;
import com.cesske.mps.model.ServiceResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


/**
 * mps-tool 微服务
 * @author deray.wang
 */
@FeignClient(name = "mps-tool", configuration = MultipartSupportConfig.class, fallback = MsToolClientHystrix.class)
public interface IMsToolClient {

    /**
     * 从微信获取openid
     * @param code
     * @return
     */
    @RequestMapping(value = "/v1/wechat/oauth", method = RequestMethod.GET)
    ServiceResponse<String> wechatOauth(@RequestParam("code") String code);

    //发送邮件
    @RequestMapping(value = "/v1/send/email", method = RequestMethod.POST)
    ServiceResponse sendEmail(@RequestParam(value = "mailSubject") String mailSubject,
                              @RequestParam(value = "mailContent") String mailContent);


    /**
     * 微服务间使用，发送验证码
     * @param traceId 调用链id
     * @param phone 手机号
     * @param type 验证码类型 1, "手机登录"  2, "手机注册" 3, "找回密码"
     * @return
     */
    @RequestMapping(value = "/v1/sms/code/send/inner", method = RequestMethod.POST)
    ServiceResponse sendCodeSms(
            @RequestParam(value = "traceId") String traceId,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "type") Integer type
    );

    /**
     * 人机验证校验接口
     * @param traceId 调用链id
     * @param sessionId 人机验证 sessionId
     * @param sig  人机验证 sig
     * @param token 人机验证 token
     * @param scene 人机验证 scene
     * @return
     */
    @RequestMapping(value = "/v1/manMachine/verify", method = RequestMethod.POST)
    ServiceResponse<Boolean> manMachineVerify(
            @RequestParam(value = "traceId") String traceId,
            @RequestParam(value = "sessionId") String sessionId,
            @RequestParam(value = "sig") String sig,
            @RequestParam(value = "token") String token,
            @RequestParam(value = "scene") String scene
    );

    //上传用户头像
    @RequestMapping(value = "/v1/file/upload",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ServiceResponse upload(@RequestPart("file") MultipartFile file);
}
