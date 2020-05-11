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
 * 登陆参数类
 * @author hubin
 */
@Data
@ApiModel
@ToString
public class LoginBean extends ApiParameterBase {

    @ApiModelProperty("登陆方式 1：验证码登陆  2：密码登录")
    private int type;

    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = RegexpConstant.REGEXP_PHONE, message = "请输入正确的手机号码")
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("密码")
    private String password;

}