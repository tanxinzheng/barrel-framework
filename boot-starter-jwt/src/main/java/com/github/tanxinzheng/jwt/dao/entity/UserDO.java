package com.github.tanxinzheng.jwt.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user", keepGlobalPrefix = true)
public class UserDO {

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    @TableField(value = "name")
    private String name;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "salt")
    private String salt;
    @TableField(value = "email")
    private String email;

}
