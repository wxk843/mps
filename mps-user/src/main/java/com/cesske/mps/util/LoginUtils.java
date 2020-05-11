package com.cesske.mps.util;

import com.cesske.mps.utils.RSAEncrypterUtil;
import com.cesske.mps.cache.CommonRedisKey;
import com.cesske.mps.utils.CommonUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.Map;

@Slf4j
@Component
public class LoginUtils {

//    PublicKey
//    MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdxyfTh65md9tJSN+P9vq1zmBkfLRmw26Ke/QBFgIxAiIb/oriXXaapu1AA480MP9b5aXHvOJvJlt/0mFzHJ6LYqjimXvkFCjgvy3ktgwRtqACTrzxkI2HLRJgDztkihbe4zi+zi61q8No/zxMqBx+z1tJcoRcDuYkbHvuuOQBmQIDAQAB
//    PrivateKey
//    MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN3HJ9OHrmZ320lI34/2+rXOYGR8tGbDbop79AEWAjECIhv+iuJddpqm7UADjzQw/1vlpce84m8mW3/SYXMcnotiqOKZe+QUKOC/LeS2DBG2oAJOvPGQjYctEmAPO2SKFt7jOL7OLrWrw2j/PEyoHH7PW0lyhFwO5iRse+645AGZAgMBAAECgYAo5+r4oTYRwFm6Eq7ppuxnTEGLR1Ue/z0MXgCLkrukvnf8Id8MEaEmtVNdU0q+nar8EEebp8M8LR8DXJKM4OJ9CxQo57YhsKOc/kL/EDxrK3ex70AIEx9y5trrTf8YedRcRoHMuRL7T8ljvX2g3NCCvEyVafmgAT7Q0BeGspNsAQJBAPv95Zq1O0le+X6vZhU3UVty0d8kLgNJqfVY6ZEVTEZqSbyZyVpAgysZXLYe94wZIpaSwD+z+zHTBz7uZc6FUVECQQDhTjqVah/BBOnA6vuYST49otMbAy00qpO5NfGlEMeZTOXnzijlntBY6179A51m3prOkZswH6CBnE3TaXkdUlnJAkAaYnU+A6fXgNhCyX64zff4yUbsRE+FDJt9Evgxtrcr0Ek0NC3/Ay44vwkUEJ3+z0rt1SPIB8JPbzcdAb0rKI5hAkEAyIr3PlNt3ELhReRi+dQH4JjzyxFyxXQndS383u4mm9+ErGYmpOxNizhGHnN/QTdXGBMmzRj5pyRXaZxyP5UzcQJABi1SCvZMogCDegW/sIdd504amdTlFAWgFeyz4WehuvV8Eh6RfhF36UigMKS0owhhsfzOfxi/rZBpKf1ug0/5uQ==

//    @Value("${common.security.rsa.publicKey}")
//    private String rsaPublicKeyString;

    @Value("${common.security.rsa.privateKey}")
    private String rsaPrivateKeyString;

    @Autowired
    private RedisTemplate redisTemplate;

    public String descryptPassword(String password) {
        if(StringUtils.isEmpty(password)) {
            return null;
        }
        byte[] descrypt = RSAEncrypterUtil.decryptByPrivateKey(Base64Utils.decodeFromString(password), RSAEncrypterUtil.getPrivateKey(rsaPrivateKeyString));
        if(descrypt == null || descrypt.length == 0) {
            return null;
        }
        return new String(descrypt);
    }

    public boolean checkCanPasswordLogin(String phone) {
        if(StringUtils.isEmpty(phone)) {
            return false;
        }
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String wrongTimesStr = hashOperations.get(CommonRedisKey.wrongPasswordLoginKey(), phone);
        Long wrongTimes = NumberUtils.createLong(wrongTimesStr);
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("phone", phone);
        logMap.put("wrongTimes", wrongTimes);
        log.info(CommonUtils.genLogString("检查密码登录错误次数", "check_wrong_password_login_times", logMap));
        if(wrongTimes != null && wrongTimes >= 10) {
            return false;
        }
        return true;
    }

    public long increaseWrongPasswordLogin(String phone) {
        if(StringUtils.isEmpty(phone)) {
            return 0L;
        }
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        return hashOperations.increment(CommonRedisKey.wrongPasswordLoginKey(), phone, 1L);
    }

    public long leftWrongPasswordTimes(String phone) {
        if(StringUtils.isEmpty(phone)) {
            return 0L;
        }
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String wrongTimesStr = hashOperations.get(CommonRedisKey.wrongPasswordLoginKey(), phone);
        Long wrongTimes = NumberUtils.createLong(wrongTimesStr);
        return 10 - (wrongTimes == null ? 0 : wrongTimes);
    }

    public void clearWrongPasswordLogin(String phone) {
        if(StringUtils.isEmpty(phone)) {
            return ;
        }
        HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(CommonRedisKey.wrongPasswordLoginKey(), phone);
    }

}
