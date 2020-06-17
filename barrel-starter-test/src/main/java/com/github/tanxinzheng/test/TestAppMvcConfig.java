package com.github.tanxinzheng.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by tanxinzheng on 2018/11/15.
 */
@ComponentScan("com.github.tanxinzheng.*")
@MapperScan(value = "com.github.tanxinzheng.**.mapper")
@Configuration
public class TestAppMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
                .parameterName("format")
                .favorParameter(true)
                .ignoreUnknownPathExtensions(false)
                .ignoreAcceptHeader(false)
                .useJaf(true);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(false, new MappingJackson2JsonView());
        registry.enableContentNegotiation(
                new MappingJackson2JsonView()
        );
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(
                SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
                DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );
        return builder.build();
    }

}
