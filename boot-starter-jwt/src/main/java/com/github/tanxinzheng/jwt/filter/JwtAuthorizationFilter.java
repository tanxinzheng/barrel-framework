package com.github.tanxinzheng.jwt.filter;

import com.github.tanxinzheng.jwt.config.JwtConfigProperties;
import com.github.tanxinzheng.jwt.support.JwtAuthenticationProvider;
import com.github.tanxinzheng.jwt.support.JwtAuthenticationToken;
import com.github.tanxinzheng.jwt.support.JwtUtils;
import com.github.tanxinzheng.jwt.support.TokenType;
import lombok.extern.slf4j.Slf4j;
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
        if (authHeader != null && authHeader.startsWith(TokenType.BEARER.getCode())) {
            String authToken = authHeader.substring(TokenType.BEARER.getCode().length() + 1);// The part after "Bearer "
            if (!jwtUtils.validateToken(authToken)) {
                throw new BadCredentialsException("the access token is invalid.");
            } else {
                String username = jwtUtils.getUsernameByToken(authToken);
                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authToken);
                jwtAuthenticationProvider.authenticate(authenticationToken);
                request.setAttribute("username", username);
                log.info("authenticated user: {}, roles: {}", username, authenticationToken.getAuthorities());
            }
        }
        chain.doFilter(request, response);
    }
}
