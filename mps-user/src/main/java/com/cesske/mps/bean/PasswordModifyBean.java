package com.cesske.mps.bean;

import com.cesske.mps.model.ApiParameterBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 修改密码
 * @author hubin
 */
@Data
@ApiModel
@ToString
public class PasswordModifyBean extends ApiParameterBase {

    @NotNull(message = "wmID不能为空")
    @ApiModelProperty("wmID")
    private String wmId;

    @NotNull(message = "旧密码不能为空")
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotNull(message = "新密码不能为空")
    @ApiModelProperty("新密码")
    private String newPassword;

}
