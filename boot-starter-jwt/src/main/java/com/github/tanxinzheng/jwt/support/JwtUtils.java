package com.github.tanxinzheng.jwt.support;

import com.github.tanxinzheng.jwt.config.JwtConfigProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * Created by tanxinzheng on 2018/9/23.
 */
@Component
@Slf4j
public class JwtUtils {

    @Resource
    JwtConfigProperties jwtConfigProperties;

    /**
     * 生成token
     * @param username
     * @return
     */
    public String createToken(String username){
        return createTokenByTime(username, jwtConfigProperties.getExpiration());
    }

    /**
     * 生成token
     * @param username
     * @return
     */
    public String createRefreshToken(String username){
        return createTokenByTime(username, jwtConfigProperties.getRefreshTokenExpiration());
    }

    private String createTokenByTime(String username, Long expiration){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(username)
                .setNotBefore(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtConfigProperties.getSecret());
        if(StringUtils.isNotBlank(jwtConfigProperties.getIssuer())){
            jwtBuilder.setIssuedAt(new Date());
            jwtBuilder.setIssuer(jwtConfigProperties.getIssuer());
        }
        return jwtBuilder.compact();
    }

    /**
     * 解析出jwt信息
     * @param token
     * @param secret
     * @return
     */
    public Claims parseToken(String token, String secret){
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
        return claimsJws.getBody();
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        String  subject = Jwts.parser()
                    .setSigningKey(jwtConfigProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        return subject;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 判断token是否已经过期
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfigProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException
                | UnsupportedJwtException
                | MalformedJwtException
                | SignatureException
                | IllegalArgumentException ex) {
            log.info(ex.getMessage(), ex);
        } catch (Exception e) {
            log.info("token验证失败: {}",token);
        }
        return claims;
    }

    /**
     * 获取token
     * @param request
     * @param headerName
     * @param cookieName
     * @param parameterName
     * @return
     */
    public String getTokenValue(HttpServletRequest request, String headerName, String cookieName, String parameterName){
        if(request == null){
            return null;
        }
        // 从Header中获取token
        String token = request.getHeader(headerName);
        if(StringUtils.isNotBlank(token)){
            token = token.replaceAll(StringUtils.lowerCase(jwtConfigProperties.getTokenType()) + " ", "");
        }
        if(StringUtils.isBlank(token)){
            // 从请求参数中获取token
            token = request.getParameter(parameterName);
        }
        if(StringUtils.isBlank(token)
                && ArrayUtils.isNotEmpty(request.getCookies())){
            // 从Cookie中获取token
            String cookieTokenName = cookieName;
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName() != null
                        && cookieTokenName != null
                        && cookie.getName().equalsIgnoreCase(cookieTokenName)){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }




}
