package com.github.tanxinzheng.framework.exception;

/**
 * business logic exception
 * Created by tanxinzheng on 16/10/22.
 */
public class BusinessException extends BaseException {

    /**
     * 参数格式化错误信息
     *
     * @param message 信息
     * @param args    格式化参数
     */
    public BusinessException(String message, Object... args) {
        super(message, args);
    }

    /**
     * 参数格式化错误信息
     *
     * @param message 信息
     * @param cause   异常原因
     * @param args    格式化参数
     */
    public BusinessException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
