package com.github.tanxinzheng.framework.secure.domain;

import com.github.tanxinzheng.framework.model.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "用户信息")
public class AuthUser implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "姓名")
    private String nickname;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "盐码")
    private String salt;
    @ApiModelProperty(value = "电子邮箱")
    private String email;
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;
    @ApiModelProperty(value = "是否禁用")
    private boolean disable;
    @ApiModelProperty(value = "头像")
    private String avatar = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";
    @ApiModelProperty(value = "所属角色组")
    private List<String> roles;
    @ApiModelProperty(value = "拥有的菜单")
    private List<TreeNode> menus;
}
