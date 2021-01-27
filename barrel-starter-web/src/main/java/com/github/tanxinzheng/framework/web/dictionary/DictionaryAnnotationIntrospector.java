package com.github.tanxinzheng.framework.web.dictionary;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.github.tanxinzheng.framework.web.annotation.AccountField;
import com.github.tanxinzheng.framework.web.annotation.DictionaryTransfer;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * 字典注解
 */
public class DictionaryAnnotationIntrospector extends JacksonAnnotationIntrospector implements ApplicationContextAware {

    private Map<Class, Class> annotationJsonSerializerMap = Maps.newHashMap();

    private ApplicationContext applicationContext;

    public DictionaryAnnotationIntrospector() {
        annotationJsonSerializerMap.put(DictionaryTransfer.class, DictionaryJsonSerializer.class);
        annotationJsonSerializerMap.put(AccountField.class, AccountJsonSerializer.class);
    }

    @Override
    public Object findSerializer(Annotated a) {
        for (Class key : annotationJsonSerializerMap.keySet()) {
            if(a.hasAnnotation(key)){
                Object dictionaryTransfer = a.getAnnotation(key);
                return applicationContext.getBean(annotationJsonSerializerMap.get(key), dictionaryTransfer);
            }
        }
        return super.findSerializer(a);
    }

    /**
     * 设置注解类及对应的序列化类
     * @param annotated
     * @param jsonSerializer
     */
    public void appendAnnotationJsonSerializer(Class annotated, Class jsonSerializer){
        annotationJsonSerializerMap.put(annotated, jsonSerializer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



}
