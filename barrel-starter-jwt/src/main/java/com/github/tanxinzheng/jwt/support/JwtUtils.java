package com.github.tanxinzheng.jwt.support;

import com.github.tanxinzheng.framework.constant.JwtConfigProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
        if(!validateToken(token)){
            return null;
        }
        return Jwts.parser()
                    .setSigningKey(jwtConfigProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody().getSubject();
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
        } catch (UnsupportedJwtException ex) {
            log.error("token验证失败: {}", token);
            log.error(ex.getMessage(), ex);
            throw new JwtException("the access token is illegal, signature parsing fail.");
        } catch (SignatureException | MalformedJwtException se){
            log.error(se.getMessage(), se);
            throw new JwtException("the access token is illegal, signature parsing fail.");
        } catch (ExpiredJwtException e){
            log.error(e.getMessage(), e);
            throw new JwtException("the access token is expired.");
        }
        return claims;
    }

}
