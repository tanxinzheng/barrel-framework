package com.github.tanxinzheng.framework.secure.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "认证信息")
public class AuthUser implements Serializable {
    @ApiModelProperty(value = "令牌")
    private String accessToken;
    @ApiModelProperty(value = "令牌类型")
    private String tokenType;
    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;
    @ApiModelProperty(value = "头像")
    private String avatar = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";
    @ApiModelProperty(value = "角色名")
    private String authority;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "过期时间")
    private long expiresIn;
    @ApiModelProperty(value = "许可证")
    private String license = "powered by barrel";
}
