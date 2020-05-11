package com.cesske.mps.cache;

import com.cesske.mps.constants.CommonConst;

/**
 * Redis Key
 * @author hubin
 */
public class CommonRedisKey {

    public static final String PREFIX = CommonConst.REDIS_KEY_PREFIX;

    /**
     * 保存短信验证码的 key
     * @param phone
     * @param code
     * @param type
     * @return
     */
    public static String smsCodeKey(String phone, String code, int type) {
        return String.format("%ssmsCode:mobile-%s_code-%s_type-%d", PREFIX, phone, code, type);
    }

    /**
     * 2018.12.12 双12活动库存的 key
     * @return
     */
    public static String activityInventory20181212Key() {
        return String.format("%sactivity:%s:%s", PREFIX, "20181212", "car-inventory");
    }

    /**
     * 密码登陆失败次数校验 Key
     * @return
     */
    public static String wrongPasswordLoginKey() {
        return String.format("%swrongPasswordLogin", PREFIX);
    }

}
