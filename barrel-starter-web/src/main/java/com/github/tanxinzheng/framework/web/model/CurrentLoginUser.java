package com.github.tanxinzheng.framework.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentLoginUser implements Serializable {

    private String id;
    private String username;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private Set<String> roles;

}
