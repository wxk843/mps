package com.cesske.mps.constants;

/**
 * 正则表达式常量
 */
public class RegexpConstant {

    /**
     * 手机号码正则
     */
    public static final String REGEXP_PHONE = "^1[0-9]{10}$";
    /**
     * 密码验证规则
     */
    public static final String REGEXP_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
    /**
     * 邮箱格式验证 允许空串的正则
     */
    public static final String REGEXP_EMAIL_IS_NULL = "^(\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+)?$";
    /**
     * 邮箱格式验证 不允许为空的正则
     */
    public static final String REGEXP_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]+$";
    /**
     * 用户姓名规则
     */
    //public static final String USER_NAME = "^[\\u4e00-\\u9fa5\\-_A-Za-z0-9][\\s\\u4e00-\\u9fa5\\-_A-Za-z0-9]{0,18}[\\u4e00-\\u9fa5\\-_A-Za-z0-9]$";
    public static final String USER_NAME = "^[\\u4e00-\\u9fa5\\-_A-Za-z0-9(（][\\s\\u4e00-\\u9fa5\\-_A-Za-z0-9()（）]{0,38}[\\u4e00-\\u9fa5\\-_A-Za-z0-9)）]$";

    /**
     * 有效的金额正则 格式xxxxx.xx
     */
    public static final String REGEXP_MONEY = "^\\d+(\\.\\d{1,2})?$";
    /**
     * 专利信息来源正则
     */
    public static final String REGEXP_PATENT_ORAINAL = "^0[1-3]\\d?$";

    /**
     * 专利研发阶段正则
     */
    public static final String REGEXP_PATENT_DEV_PHASE = "^0[1-6]\\d?$";

    /**
     * 日期正则 格式为 yyyy-mm-dd
     */
    public static final String REGEXP_DATA_YYYY_MM_DD = "^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1]))$";
    /**
     * 0 1 正则
     */
    public static final String REGEXP_BOOLEAN = "^[0-1]\\d?$";
    /**
     * 证件类型-身份证
     */
    public static final String REGEXP_ID_CARD_SFZ = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    /**
     * 证件类型-other
     */
    public static final String REGEXP_ID_CARD_OTH = "^[A-Za-z0-9\\u4E00-\\u9FA5]{2,20}$";
    /**
     * 角色、权限的正则
     */
    public static final String REGEXP_ROLE_PERMISSION = "^[\\w\\-\\:]+$";

    private RegexpConstant() {
    }


}
