package com.github.tanxinzheng.framework.validator;

/**
 * Created by tanxinzheng on 2018/12/13.
 */
public interface AbstractValidator<T> {

    /**
     * 获取正则表达式
     * @return
     */
    String getPattern();

    /**
     * 校验是否正确
     * @param value
     * @return
     */
    boolean isValid(T value);
}
