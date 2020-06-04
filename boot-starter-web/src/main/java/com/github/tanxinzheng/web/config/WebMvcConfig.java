package com.github.tanxinzheng.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tanxinzheng.framework.web.handler.LogbackMDCInterceptor;
import com.github.tanxinzheng.framework.web.json.CustomDateDeserialize;
import com.github.tanxinzheng.framework.web.json.LocalDateTimeDeserialize;
import com.github.tanxinzheng.framework.web.json.LocalDateTimeSerializer;
import com.github.tanxinzheng.framework.web.support.DateConverter;
import com.github.tanxinzheng.module.dictionary.web.DictionaryAnnotationIntrospector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by tanxinzheng on 17/8/23.
 */
@Configuration
@ComponentScan(basePackages = {"com.github.tanxinzheng.**"})
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        System.out.println();
    }

    /**
     * 使用swagger-ui.html必须添加的配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.timeZone("GMT+8");
        builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.deserializerByType(Date.class, new CustomDateDeserialize());
        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserialize());
        builder.featuresToDisable(
                SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
                DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );
        builder.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        builder.annotationIntrospector(getDictionaryIntrospector());
        return builder.build();
    }

    @Bean
    public DictionaryAnnotationIntrospector getDictionaryIntrospector(){
        DictionaryAnnotationIntrospector dictionaryAnnotationIntrospector = new DictionaryAnnotationIntrospector();
        dictionaryAnnotationIntrospector.setApplicationContext(applicationContext);
        return dictionaryAnnotationIntrospector;
    }

    @Bean
    public BeanNameViewResolver getBeanNameViewResolver(){
        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .parameterName("format")
                .favorParameter(true)
                .ignoreUnknownPathExtensions(false)
                .ignoreAcceptHeader(false);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(false, new MappingJackson2JsonView());
        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }

    @Bean
    public ErrorProperties errorProperties(){
        return new ErrorProperties();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogbackMDCInterceptor()).addPathPatterns("/**");
    }

}
