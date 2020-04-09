package com.github.tanxinzheng.framework.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import java.util.Set;

@Data
public class CurrentLoginUser {

    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private Set<String> roles;

}
