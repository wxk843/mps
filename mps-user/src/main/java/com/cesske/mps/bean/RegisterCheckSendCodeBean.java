package com.cesske.mps.bean;

import com.cesske.mps.constants.RegexpConstant;
import com.cesske.mps.model.ApiParameterBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel
public class RegisterCheckSendCodeBean extends ApiParameterBase {

    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = RegexpConstant.REGEXP_PHONE, message = "请输入正确的手机号码")
    @ApiModelProperty("手机号")
    private String phone;

    @NotNull
    @ApiModelProperty("人机验证 sessionId")
    private String sessionId;
    @NotNull
    @ApiModelProperty("人机验证 sig")
    private String sig;
    @NotNull
    @ApiModelProperty("人机验证 token")
    private String token;
    @NotNull
    @ApiModelProperty("人机验证 scene")
    private String scene;
}
