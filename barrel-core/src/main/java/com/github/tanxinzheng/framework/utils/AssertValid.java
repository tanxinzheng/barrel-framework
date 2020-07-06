package com.github.tanxinzheng.framework.utils;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/*
 * @Author tanxinzheng
 * @Description TODO
 * @Date 2020/6/17
 */
public class AssertValid extends Assert {

    public static void state(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void isTrue(boolean expression, String message, Object... args) {
        if (!expression) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void isNull(@Nullable Object object, String message, Object... args) {
        if (object != null) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notNull(@Nullable Object object, String message, Object... args) {
        if (object == null) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notBlank(@Nullable String text, String message, Object... args) {
        if (org.apache.commons.lang3.StringUtils.isBlank(text)) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void hasLength(@Nullable String text, String message, Object... args) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void hasText(@Nullable String text, String message, Object... args) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message, Object... args) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message, Object... args) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalStateException(MessageFormatter.arrayFormat(message, args).getMessage());
        }
    }
}
