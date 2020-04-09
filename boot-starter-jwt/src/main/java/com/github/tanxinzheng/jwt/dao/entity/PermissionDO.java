package com.github.tanxinzheng.jwt.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "permission", keepGlobalPrefix = true)
public class PermissionDO {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    @TableField(value = "permission_key")
    private String permissionKey;
    @TableField(value = "permission_url")
    private String permissionUrl;
    @TableField(value = "permission_action")
    private String permissionAction;
    @TableField(value = "permission_name")
    private String permissionName;
    @TableField(value = "description")
    private String description;
    @TableField(value = "active")
    private Boolean active;
    private String groupCode;
}
