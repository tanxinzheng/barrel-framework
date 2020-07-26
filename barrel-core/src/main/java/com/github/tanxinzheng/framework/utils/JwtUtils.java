package com.github.tanxinzheng.framework.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tanxinzheng on 2018/9/23.
 */
@Slf4j
public class JwtUtils {

    /**
     * 生成token
     * @param username
     * @param secret
     * @param expiration
     * @param issuer
     * @param claims
     * @return
     */
    private static String createToken(String username, String secret, Long expiration, String issuer, Claims claims){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(username)
                .setNotBefore(new Date());
        if(claims != null){
            jwtBuilder.setClaims(claims);
        }
        if(StringUtils.isNotBlank(issuer)){
            jwtBuilder.setIssuedAt(new Date());
            jwtBuilder.setIssuer(issuer);
        }
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secret);
        return jwtBuilder.compact();
    }

    /**
     * 生成token
     * @param username
     * @param secret
     * @param tokenExpiration
     * @param issuer
     * @return
     */
    public static String createToken(String username, String secret, Long tokenExpiration, String issuer){
        return createToken(username, secret, tokenExpiration, issuer, null);
    }

    /**
     * 解析出jwt信息
     * @param token
     * @param secret
     * @return
     */
    public static Jws<Claims> parseToken(String token, String secret) throws SignatureException {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     */
    public static boolean validateToken(String token, String secret) throws ExpiredJwtException, SignatureException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (UnsupportedJwtException | MalformedJwtException | ClaimJwtException se){
            log.error("token验证失败: {}", token);
            log.error(se.getMessage(), se);
            throw new SignatureException("the access token is illegal, signature parsing fail.");
        }
    }

}
