package com.github.tanxinzheng.framework.exception;

public class AuthException extends RuntimeException {

    /**
     * Constructs a new invalid parameter exception with the specified detail message.
     * @param message exception message
     */
    public AuthException(final String message) {
        super(message);
    }
}
