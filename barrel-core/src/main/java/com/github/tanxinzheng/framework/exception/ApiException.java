package com.github.tanxinzheng.framework.exception;

import com.github.tanxinzheng.framework.model.Result;

/*
 * @Description TODO
 * @Author tanxinzheng
 * @Date 2020/7/5
 */
public class ApiException extends BaseException {

    private Result result;

    public ApiException(Result result) {
        super(result.getMessage());
        this.result = result;
    }

    /**
     * 参数格式化错误信息
     *
     * @param message 信息
     * @param args    格式化参数
     */
    public ApiException(String message, Object... args) {
        super(message, args);
    }

    /**
     * 参数格式化错误信息
     *
     * @param message 信息
     * @param cause   异常原因
     * @param args    格式化参数
     */
    public ApiException(String message, Throwable cause, Object... args) {
        super(message, cause, args);
    }
}
