package com.github.tanxinzheng.jwt.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel(value = "认证请求参数")
@Data
public class LoginRequest implements Serializable {

    @NotBlank(message = "请输入用户名")
    @ApiModelProperty(value = "用户名")
    private String username;
    @NotBlank(message = "请输入密码")
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String validCode;
}
