package com.github.tanxinzheng.framework.secure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "barrel.auth")
public class SecureProperties {

    private static final String DEFAULT_TOKEN_HEADER_NAME = "Authorization";
    private static final String DEFAULT_TOKEN_PARAMETER_NAME = "barrel-token";
    private static final String DEFAULT_TOKEN_COOKIE_NAME = "barrel-token";
    // 默认 2小时
    private static final long DEFAULT_TOKEN_EXPIRATION = 2l;
    /**
     * Token 认证请求头
     */
    private String tokenHeaderName;
    /**
     * Token 请求参数
     */
    private String tokenParameterName;
    /**
     * Token 请求参数
     */
    private String tokenCookieName;
    /**
     * Token 加密私钥
     */
    private String secret;
    /**
     * 有效时间，时间单位：分钟
     */
    private Long expiration;
    /**
     * 匿名可访问URL
     */
    private String[] ignoreUrls;
    /**
     * 是否启用验证码
     */
    private boolean enableCaptchaCheck;

    public SecureProperties() {
        this.expiration = DEFAULT_TOKEN_EXPIRATION;
        this.tokenHeaderName = DEFAULT_TOKEN_HEADER_NAME;
        this.tokenParameterName = DEFAULT_TOKEN_PARAMETER_NAME;
        this.tokenCookieName = DEFAULT_TOKEN_COOKIE_NAME;
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
    }

    public String getTokenParameterName() {
        return tokenParameterName;
    }

    public void setTokenParameterName(String tokenParameterName) {
        this.tokenParameterName = tokenParameterName;
    }

    public String getTokenCookieName() {
        return tokenCookieName;
    }

    public void setTokenCookieName(String tokenCookieName) {
        this.tokenCookieName = tokenCookieName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String[] getIgnoreUrls() {
        return ignoreUrls;
    }

    public void setIgnoreUrls(String[] ignoreUrls) {
        this.ignoreUrls = ignoreUrls;
    }

    public boolean isEnableCaptchaCheck() {
        return enableCaptchaCheck;
    }

    public void setEnableCaptchaCheck(boolean enableCaptchaCheck) {
        this.enableCaptchaCheck = enableCaptchaCheck;
    }
}
