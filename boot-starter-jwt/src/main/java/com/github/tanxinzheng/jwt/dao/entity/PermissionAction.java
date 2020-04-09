package com.github.tanxinzheng.jwt.dao.entity;

import org.springframework.web.bind.annotation.RequestMethod;

public enum PermissionAction {

    VIEW("查看", RequestMethod.GET),
    EDIT("编辑", RequestMethod.PUT),
    ADD("新增", RequestMethod.POST),
    REMOVE("删除", RequestMethod.DELETE),
    FILTER("过滤", RequestMethod.GET),
    IMPORT("导入", RequestMethod.POST),
    EXPORT("导出", RequestMethod.GET)
    ;

    PermissionAction(String desc, RequestMethod requestMethod) {
        this.desc = desc;
        this.requestMethod = requestMethod;
    }

    private String desc;
    private RequestMethod requestMethod;

    public String getDesc() {
        return desc;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }
}
