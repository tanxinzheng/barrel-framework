package com.github.tanxinzheng.jwt.config;

import com.github.tanxinzheng.jwt.access.JwtAccessDecisionManager;
import com.github.tanxinzheng.jwt.access.JwtFilterInvocationSecurityMetadataSource;
import com.github.tanxinzheng.jwt.filter.JwtAuthorizationFilter;
import com.github.tanxinzheng.jwt.handler.TokenAccessDeniedHandler;
import com.github.tanxinzheng.jwt.handler.TokenSecurityMetadataHandler;
import com.github.tanxinzheng.jwt.support.JwtAuthenticationProvider;
import com.github.tanxinzheng.jwt.support.RestAuthenticationEntryPoint;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanxinzheng on 2018/9/20.
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(value = JwtConfigProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtConfigProperties jwtConfigProperties;

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(getStaticResourcesUrl());
    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }

    @Bean
    public JwtAuthorizationFilter getJwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter();
    }

    private String[] getStaticResourcesUrl(){
        String[] permitUrls = jwtConfigProperties.getPermitUrls();
        List<String> list = Lists.newArrayList(permitUrls);
        String[] data = list.toArray(new String[list.size()]);
        return data;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .headers()
                // 允许frame（支持iframe下载功能）
                .frameOptions().sameOrigin()
                .and()
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .cors()
                .and()
//                .anonymous().disable()
                // 基于token，所以不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
                // 对于获取token的rest api要允许匿名访问
                .antMatchers(getStaticResourcesUrl())
                .permitAll()
                .anyRequest().authenticated()
                // 用于动态URL权限控制
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
                        fsi.setSecurityMetadataSource(jwtFilterInvocationSecurityMetadataSource);
                        fsi.setAccessDecisionManager(jwtAccessDecisionManager);
                        return fsi;
                    }
                })
                // 除上面外的所有请求全部需要鉴权认证
                .and()
                .addFilterBefore(getJwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                    .accessDeniedHandler(new TokenAccessDeniedHandler()) // 访问未授权资源异常处理
                .and()
                // 禁用缓存
                .logout()
                .and()
                .headers().cacheControl();
    }

    @Resource
    JwtAccessDecisionManager jwtAccessDecisionManager;

    @Resource
    JwtFilterInvocationSecurityMetadataSource jwtFilterInvocationSecurityMetadataSource;

    @Resource
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

}
