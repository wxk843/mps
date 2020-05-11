package com.cesske.mps.service.user.impl;

import com.cesske.mps.cache.CommonRedisKey;
import com.cesske.mps.cache.RedisUtil;
import com.cesske.mps.service.user.IVerifyCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    private static final String PROFILE_RELEASE = "release";
    private static final String SUPER_CODE = "888999";

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private RedisUtil<String> redisUtil;

    @Override
    public boolean checkCode(String phone, String code, int type) {
        // 非上线版开启 888999 验证码
        if (!PROFILE_RELEASE.equals(profile) && SUPER_CODE.equals(code)) {
//        if (SUPER_CODE.equals(code)) {
            return true;
        }
        String key = CommonRedisKey.smsCodeKey(phone, code, type);
        return StringUtils.isNotEmpty(redisUtil.get(key));
    }

}
