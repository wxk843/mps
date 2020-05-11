package com.cesske.mps.service.user;

/**
 * 验证码服务
 */
public interface IVerifyCodeService {

    /**
     * 校验验证码
     * @param mobile
     * @param code
     * @param type
     * @return
     */
    public boolean checkCode(String mobile, String code, int type);

}
