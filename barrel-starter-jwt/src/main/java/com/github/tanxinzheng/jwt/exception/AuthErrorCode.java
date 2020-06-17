package com.github.tanxinzheng.jwt.exception;

import com.github.tanxinzheng.framework.exception.ResultCode;

public enum AuthErrorCode implements ResultCode {

    NOT_FOUND_USERNAME("E20001", "该用户未注册"),
    UNAUTHORIZED("E20003", "未认证授权"),
    FORBIDDEN("E20004", "没有权限访问该资源"),
    ;

    AuthErrorCode(String errorCode, String errorDesc) {
        this.code = errorCode;
        this.desc = errorDesc;
    }

    private String code;
    private String desc;

    @Override
    public void setCode(String Code) {
        this.code = Code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setDesc(String Desc) {
        this.desc = Desc;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
