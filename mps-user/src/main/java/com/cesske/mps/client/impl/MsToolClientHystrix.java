package com.cesske.mps.client.impl;

import com.cesske.mps.client.IMsToolClient;
import com.cesske.mps.model.ServiceResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/*
 * 熔断机制的处理
 * 当调用mps-tool服务的方法，mps-tool返回异常时，处理返回异常的方法
 */
@Component
public class MsToolClientHystrix implements IMsToolClient {
    @Override
    public ServiceResponse wechatOauth(String code) {
        return ServiceResponse.CLIENT_FAIL_RESPONSE;
    }

    @Override
    public ServiceResponse sendEmail(String mailSubject, String mailContent) {
        return ServiceResponse.CLIENT_FAIL_RESPONSE;
    }

    @Override
    public ServiceResponse sendCodeSms(String traceId, String phone, Integer type) {
        return ServiceResponse.CLIENT_FAIL_RESPONSE;
    }

    @Override
    public ServiceResponse manMachineVerify(String traceId, String sessionId, String sig, String token, String scene) {
        return ServiceResponse.CLIENT_FAIL_RESPONSE;
    }

    @Override
    public ServiceResponse upload(MultipartFile file) {
        return ServiceResponse.CLIENT_FAIL_RESPONSE;
    }
}
