package com.github.tanxinzheng.jwt.access;

import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tanxinzheng on 2018/10/22.
 */
public class PermissionGrantedAuthority implements Serializable  {

    private String url;
    private RequestMethod requestMethod;
    private List<String> roles;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
