package com.cesske.mps.client.impl;

import com.cesske.mps.client.IMsToolClient;
import com.cesske.mps.model.ServiceResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

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

}
