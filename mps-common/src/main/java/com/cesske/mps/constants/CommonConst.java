package com.cesske.mps.constants;

import java.nio.charset.Charset;

/**
 * 一些公用的常量
 */
public class CommonConst {
    /**
     * 系统默认编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 系统默认编码
     */
    public static final Charset DEFAULT_CHARSET_OBJ = Charset.forName(DEFAULT_CHARSET);


    public static final String API_PATH_VERSION_1 = "/v1";
    public static final String ADMIN_API_PATH_VERSION_1 = "/admin/v1";
    public static final String OPEN_API_PATH_VERSION_1 = "/open/v1";

    /**
     * 调用链id
     */
    public static final String TRACE_ID = "traceId";
    public static final String TOKEN = "token";

    /**
     * redis的key前缀
     */
    public static final String REDIS_KEY_PREFIX = "cn.com.driven.ecp:";

    //数据库记录有效与否标识   1有效 0无效
    public static final Integer DB_RECORD_VALID = 1;
    public static final Integer DB_RECORD_INVALID = 0;

    //活动名称字段
    public static final String ACTIVITY_1212_NAME="推荐配置";
    public static final String ACTIVITY_1212_INVALIDREASON_TIMEOUT="活动结束";
    public static final String ACTIVITY_1212_INVALIDREASON_EMPTY="库存已售罄";

    private CommonConst() {

    }

}
