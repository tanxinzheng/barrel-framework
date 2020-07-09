package com.github.tanxinzheng.jwt.support;

import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Created by tanxinzheng on 17/8/18.
 */
@Data
public class JwtUser implements UserDetails {

    private String id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String salt;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<String> roles;
    private Set<String> permissions;
    private Collection<SimpleGrantedAuthority> authorities;

    public JwtUser(){}

    public JwtUser(String id, String name, String username, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
        Collection<SimpleGrantedAuthority> collection = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(roles)){
            roles.stream().forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                collection.add(simpleGrantedAuthority);
            });
        }
    }

    public JwtUser(String id, String name, String username, Set<String> roles, Set<String> permissions) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
        Collection<SimpleGrantedAuthority> collection = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(roles)){
            roles.stream().forEach(role -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
                collection.add(simpleGrantedAuthority);
            });
        }
        if(CollectionUtils.isNotEmpty(permissions)){
            permissions.stream().forEach(permission -> {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
                collection.add(simpleGrantedAuthority);
            });
        }
        this.authorities = collection;
    }
}
