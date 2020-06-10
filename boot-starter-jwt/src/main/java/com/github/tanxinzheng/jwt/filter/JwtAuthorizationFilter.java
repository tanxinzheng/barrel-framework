package com.github.tanxinzheng.jwt.filter;

import com.github.tanxinzheng.framework.constant.JwtConfigProperties;
import com.github.tanxinzheng.framework.constant.TokenType;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import com.github.tanxinzheng.jwt.exception.AuthErrorCode;
import com.github.tanxinzheng.jwt.support.JwtAuthenticationProvider;
import com.github.tanxinzheng.jwt.support.JwtAuthenticationToken;
import com.github.tanxinzheng.jwt.support.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tanxinzheng on 17/8/19.
 */
@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Resource
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    JwtConfigProperties jwtConfigProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.debug(request.getRequestURI());
        String authHeader = request.getHeader(jwtConfigProperties.getTokenHeaderName());
        if (authHeader == null || !authHeader.startsWith(TokenType.BEARER.getCode())) {
            chain.doFilter(request, response);
            return;
        }
        String authToken = authHeader.substring(TokenType.BEARER.getCode().length() + 1);// The part after "Bearer "
        if (!jwtUtils.validateToken(authToken)) {
            throw new BadCredentialsException("the access token is invalid.");
        }
        try{
            String username = jwtUtils.getUsernameByToken(authToken);
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authToken);
            jwtAuthenticationProvider.authenticate(authenticationToken);
            log.info("authenticated user: {}, roles: {}", username, authenticationToken.getAuthorities());
            chain.doFilter(request, response);
        }catch (JwtException e){
            RestResponse.failed(AuthErrorCode.UNAUTHORIZED, e).toJSON(request, response, HttpStatus.UNAUTHORIZED);
            return;
        }
    }
}
