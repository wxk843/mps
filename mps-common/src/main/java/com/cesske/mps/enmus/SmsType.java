package com.cesske.mps.enmus;

import java.util.List;

/**
 * 短信验证码发送类型
 */
public enum SmsType implements IndexedEnum {


    /**
     * 短信类型
     */
    MOBILE_LOGIN(1, "手机登录"),
    MOBILE_REGISTER(2, "手机注册"),
    MOBILE_FIND_PWD(3, "找回密码"),
    USER_ORDER_FINISH(8, "整车订单成功支付"),
    ADMIN_ORDER_REFUND_APPLY(9, "预售订单运营人员同意退款"),
    TEST_TO_CONFIRM(10, "试驾确认短信"),
    ORDER_NOT_PAID(11, "生成订单还未支付"),
    CREDIT_ORDER_SUBMIT(12, "征信订单成功提交"),
    CREDIT_ORDER_SUBMIT_PASS(13, "征信订单通过"),
    CREDIT_ORDER_SUBMIT_NO_PASS(14, "征信订单不通过"),
    SUBSCRIBE_PUT_CAR(15, "预约提车"),
    CAR_ARRIVE_SHOP(16, "车辆到店"),
    INTENTION_PAID(17, "订单支付意向金"),
    INTENTION_REFUND(18, "意向金退款运营人员同意退款"),
    ;

    private static final List<SmsType> INDEXS = IndexedEnum.IndexedEnumUtil.toIndexes(SmsType.values());

    /**
     * 索引
     */
    private int index;
    private String name;

    SmsType(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据index 获取指定SmsType
     *
     * @param index
     * @return
     */
    public static SmsType indexOf(final int index) {
        return IndexedEnum.IndexedEnumUtil.valueOf(INDEXS, index);
    }


}
