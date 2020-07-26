package com.github.tanxinzheng.starter.cloud.feign.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/*
 * @Description TODO
 * @Author tanxinzheng
 * @Date 2020/7/25
 */
@Configuration
public class MultiPartSupportConfiguration {

    @Resource
    private HttpMessageConverters messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        ObjectFactory<HttpMessageConverters> messageConvertersFactory = new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return messageConverters;
            }
        };
        return new SpringFormEncoder(new SpringEncoder(messageConvertersFactory));
    }
}
