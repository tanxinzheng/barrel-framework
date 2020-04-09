package com.github.tanxinzheng.jwt.config;

import com.github.tanxinzheng.framework.core.service.CurrentUserService;
import com.github.tanxinzheng.framework.mybatis.handler.DefaultFillObjectHandler;
import com.github.tanxinzheng.jwt.support.LoginUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginUserResolverConfig {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @PostConstruct
    public void injectSelfMethodArgumentResolver() {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.add(new LoginUserResolver());
        argumentResolvers.addAll(adapter.getArgumentResolvers());
        adapter.setArgumentResolvers(argumentResolvers);
    }

    @Resource
    CurrentUserService currentUserService;

    @Bean
    public DefaultFillObjectHandler defaultFillObjectHandler(){
        return new DefaultFillObjectHandler(currentUserService);
    }
}
