package com.github.tanxinzheng.framework.exception;

public class AuthException extends BaseException {

    public AuthException(String message, Object... args) {
        super(message, args);
    }

    public AuthException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
