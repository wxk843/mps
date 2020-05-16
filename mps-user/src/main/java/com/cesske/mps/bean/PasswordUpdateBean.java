package com.cesske.mps.bean;

import com.cesske.mps.constants.RegexpConstant;
import com.cesske.mps.model.ApiParameterBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 找回密码
 * @author
 */
@Data
@ApiModel
@ToString
public class PasswordUpdateBean extends ApiParameterBase {

    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = RegexpConstant.REGEXP_PHONE, message = "请输入正确的手机号码")
    @ApiModelProperty("手机号")
    private String phone;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

}
