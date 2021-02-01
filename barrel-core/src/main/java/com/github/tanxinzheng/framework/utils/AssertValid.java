package com.github.tanxinzheng.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/*
 * @Author tanxinzheng
 * @Description TODO
 * @Date 2020/6/17
 */
public class AssertValid extends Assert {

    public static void state(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void isTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void isFalse(boolean expression, String message, Object... args) {
        if (expression) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void isNull(@Nullable Object object, String message, Object... args) {
        if (object != null) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notNull(@Nullable Object object, String message, Object... args) {
        if (object == null) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void isBlank(@Nullable String text, String message, Object... args) {
        if (StringUtils.isNotBlank(text)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notBlank(@Nullable String text, String message, Object... args) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }


    public static void notEmpty(@Nullable Object[] array, String message, Object... args) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message, Object... args) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }
}
