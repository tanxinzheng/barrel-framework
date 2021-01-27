package com.github.tanxinzheng.framework.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tanxinzheng on 16/10/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DictionaryTransfer {

    public static final String DICT_SERVICE_TYPE = "DICT_INFO";

    /**
     * 字典类型
     * @return
     */
    String type();

    /**
     * 字段名称
     * @return
     */
    String fieldName() default "";

    /**
     * 类型
     * @return
     */
    String serviceType() default DictionaryTransfer.DICT_SERVICE_TYPE;

    /**
     * 输出格式
     * @return
     */
    Class outputFormat() default String.class;

}
