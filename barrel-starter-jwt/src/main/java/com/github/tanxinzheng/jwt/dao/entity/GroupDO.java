package com.github.tanxinzheng.jwt.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "group", keepGlobalPrefix = true)
public class GroupDO {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    @TableField(value = "group_code")
    private String groupCode;
    @TableField(value = "group_name")
    private String groupName;
    @TableField(value = "description")
    private String description;
    @TableField(value = "group_type")
    private String groupType;
    @TableField(value = "active")
    private Boolean active;
}
