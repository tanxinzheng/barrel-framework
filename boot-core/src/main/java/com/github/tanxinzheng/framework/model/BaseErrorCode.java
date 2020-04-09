package com.github.tanxinzheng.framework.model;

import com.github.tanxinzheng.framework.exception.ErrorCode;

public enum BaseErrorCode implements ErrorCode {
    BAD_PARAMETERS("E10002", "错误的请求参数"),
    SYSTEM_ERROR("E00001", "系统异常，请联系管理员")
    ;

    BaseErrorCode(String errorCode, String errorDesc) {
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
