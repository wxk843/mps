package com.cesske.mps.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请求响应的基础参数
 */
@ApiModel
@Data
public class ApiParameterBase {

    @ApiModelProperty("调用链id")
    private String traceId;
//    @ApiModelProperty("token用户凭证")
//    private String access_token;

}
