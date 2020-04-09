package com.github.tanxinzheng.jwt.config;

import com.github.tanxinzheng.jwt.support.TokenType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Created by tanxinzheng on 2018/9/20.
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigProperties {

    private static final String DEFAULT_JWT_TOKEN_HEADER_NAME = "Authorization";

    private static final String DEFAULT_JWT_TOKEN_PARAMETER_NAME = "token";

    private static final String DEFAULT_JWT_TOKEN_COOKIE_NAME = "jwt-token";

    private static final String DEFAULT_JWT_REFRESH_TOKEN_HEADER_NAME = "refresh-token";

    private static final String DEFAULT_JWT_REFRESH_TOKEN_PARAMETER_NAME = "refresh-token";

    private static final String DEFAULT_JWT_REFRESH_TOKEN_COOKIE_NAME = "jwt-refresh-token";

    private static final String DEFAULT_JWT_SESSION_NAME = "jwt-session";

    private static final String DEFAULT_JWT_TOKEN_TYPE = TokenType.BEARER.getCode();

    // 默认 2小时
    private static final long DEFAULT_JWT_EXPIRATION = 2L * 1000 * 60 * 60;

    // 默认 2周
    private static final long DEFAULT_JWT_REFRESH_EXPIRATION = 2L * 7 * 24 * 1000 * 60 * 60;

    /**
     * JWT 认证请求头
     */
    private String tokenHeaderName;

    /**
     * Token Type
     */
    private String tokenType;

    /**
     * JWT 请求参数
     */
    private String tokenParameterName;

    /**
     * JWT 请求参数
     */
    private String tokenCookieName;

    /**
     * JWT 刷新token请求头
     */
    private String refreshTokenHeaderName;

    /**
     * JWT 刷新token请求参数
     */
    private String refreshTokenParameterName;

    /**
     * JWT 刷新tokenCookie参数
     */
    private String refreshTokenCookieName;

    /**
     * JWT tokenId名称，用于获取缓存的角色，权限及其他敏感信息
     */
    private String tokenIdName;

    /**
     * 签发该证书的负责人
     */
    private String issuer;

    /**
     * JWT私钥
     */
    private String secret;

    /**
     * 有效时间
     */
    private Long expiration;

    /**
     * refresh token 有效时间
     */
    private Long refreshTokenExpiration;

    /**
     * 匿名可访问URL
     */
    private String[] permitUrls;

    public JwtConfigProperties() {
        this.tokenType = TokenType.BEARER.getCode();
        this.tokenHeaderName = DEFAULT_JWT_TOKEN_HEADER_NAME;
        this.tokenParameterName = DEFAULT_JWT_TOKEN_PARAMETER_NAME;
        this.tokenCookieName = DEFAULT_JWT_TOKEN_COOKIE_NAME;
        this.refreshTokenExpiration = DEFAULT_JWT_REFRESH_EXPIRATION;
        this.refreshTokenHeaderName = DEFAULT_JWT_REFRESH_TOKEN_HEADER_NAME;
        this.refreshTokenParameterName = DEFAULT_JWT_REFRESH_TOKEN_PARAMETER_NAME;
        this.refreshTokenCookieName = DEFAULT_JWT_REFRESH_TOKEN_COOKIE_NAME;
        this.tokenIdName = DEFAULT_JWT_SESSION_NAME;
        this.expiration = DEFAULT_JWT_EXPIRATION;
        this.permitUrls = new String[]{
                "/**.css",
                "/**.js",
                "/**/*.css",
                "/**/*.js",
                "/favicon.ico"
        };
    }

    public String getTokenHeaderName() {
        return tokenHeaderName;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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

    public String getRefreshTokenHeaderName() {
        return refreshTokenHeaderName;
    }

    public void setRefreshTokenHeaderName(String refreshTokenHeaderName) {
        this.refreshTokenHeaderName = refreshTokenHeaderName;
    }

    public String getRefreshTokenParameterName() {
        return refreshTokenParameterName;
    }

    public void setRefreshTokenParameterName(String refreshTokenParameterName) {
        this.refreshTokenParameterName = refreshTokenParameterName;
    }

    public String getRefreshTokenCookieName() {
        return refreshTokenCookieName;
    }

    public void setRefreshTokenCookieName(String refreshTokenCookieName) {
        this.refreshTokenCookieName = refreshTokenCookieName;
    }

    public String getTokenIdName() {
        return tokenIdName;
    }

    public void setTokenIdName(String tokenIdName) {
        this.tokenIdName = tokenIdName;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
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

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(Long refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public String[] getPermitUrls() {
        return permitUrls;
    }

    public void setPermitUrls(String[] permitUrls) {
        this.permitUrls = permitUrls;
    }
}
