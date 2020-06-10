package com.github.tanxinzheng.framework.model;

import com.github.tanxinzheng.framework.exception.ResultCode;

public enum BaseResultCode implements ResultCode {

    OK("200", "请求成功"),
    FORBIDDEN("403", "没有权限访问该资源"),
    BAD_PARAMETERS("400", "错误的请求参数"),
    SYSTEM_ERROR("500", "服务器内部异常，请联系管理员")
    ;

    BaseResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
