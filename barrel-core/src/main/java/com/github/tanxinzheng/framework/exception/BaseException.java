package com.github.tanxinzheng.framework.exception;

import org.slf4j.helpers.MessageFormatter;

/*
 * @Description 异常基类
 * @Author tanxinzheng
 * @Date 2020/6/27
 */
public class BaseException extends RuntimeException {

    /**
     * 参数格式化错误信息
     * @param message   信息
     * @param args      格式化参数
     */
    public BaseException(String message, Object... args){
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }

    /**
     * 参数格式化错误信息
     * @param message   信息
     * @param cause     异常原因
     * @param args      格式化参数
     */
    public BaseException(String message, final Throwable cause, Object... args){
        super(MessageFormatter.arrayFormat(message, args).getMessage(), cause);
    }
}
