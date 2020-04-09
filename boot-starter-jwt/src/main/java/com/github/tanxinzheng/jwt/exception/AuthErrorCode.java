package com.github.tanxinzheng.jwt.exception;

import com.github.tanxinzheng.framework.exception.ErrorCode;

public enum AuthErrorCode implements ErrorCode {

    NOT_FOUND_USERNAME("E20001", "该用户未注册"),
    UNAUTHORIZED("E20003", "未认证授权"),
    FORBIDDEN("E20004", "没有权限访问该资源"),
    ;

    AuthErrorCode(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    private String errorCode;
    private String errorDesc;

    @Override
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Override
    public String getErrorDesc() {
        return this.errorDesc;
    }
}
