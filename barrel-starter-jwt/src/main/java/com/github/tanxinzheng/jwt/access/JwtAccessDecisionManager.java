package com.github.tanxinzheng.jwt.access;

import com.alibaba.fastjson.JSONObject;
import com.github.tanxinzheng.jwt.config.JwtConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;

/**
 * 动态URL权限控制，
 * 与注解型相比有如下优势
 * 1.可动态配置资源，灵活控制
 * 2.无需启动
 * Created by tanxinzheng on 2018/10/21.
 */
@Slf4j
@Component
public class JwtAccessDecisionManager implements AccessDecisionManager {

    @Resource
    JwtConfigProperties jwtConfigProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * Resolves an access control decision for the passed parameters.
     *
     * @param authentication   the caller invoking the method (not null)
     * @param object           the secured object being called
     * @param configAttributes the configuration attributes associated with the secured
     *                         object being invoked
     * @throws AccessDeniedException               if access is denied as the authentication does not
     *                                             hold a required authority or ACL privilege
     * @throws InsufficientAuthenticationException if access is denied as the
     *                                             authentication does not provide a sufficient level of trust
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 跳过匿名可访问资源
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequestUrl();
        for (String permitUrl : jwtConfigProperties.getPermitUrls()) {
            if(antPathMatcher.match(permitUrl, requestUrl)){
                return;
            }
        }
        if (CollectionUtils.isEmpty(configAttributes)) {
            throw new AccessDeniedException(StringUtils.lowerCase(HttpStatus.FORBIDDEN.name()));
        }
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String needRole = ((org.springframework.security.access.SecurityConfig) ca).getAttribute();
            log.debug("access this resource need role : {}, the current user roles : {} ", needRole, JSONObject.toJSONString(authentication.getAuthorities()));
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if(grantedAuthority.getAuthority().equals(needRole)){
                    //匹配到有对应角色,则允许通过
                    return;
                }
            }
        }
        //该url有配置权限,但是当然登录用户没有匹配到对应权限,则禁止访问
        throw new AccessDeniedException(StringUtils.lowerCase(HttpStatus.FORBIDDEN.name()));
    }

    /**
     * Indicates whether this <code>AccessDecisionManager</code> is able to process
     * authorization requests presented with the passed <code>ConfigAttribute</code>.
     * <p>
     * This allows the <code>AbstractSecurityInterceptor</code> to check every
     * configuration attribute can be consumed by the configured
     * <code>AccessDecisionManager</code> and/or <code>RunAsManager</code> and/or
     * <code>AfterInvocationManager</code>.
     * </p>
     *
     * @param attribute a configuration attribute that has been configured against the
     *                  <code>AbstractSecurityInterceptor</code>
     * @return true if this <code>AccessDecisionManager</code> can support the passed
     * configuration attribute
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * Indicates whether the <code>AccessDecisionManager</code> implementation is able to
     * provide access control decisions for the indicated secured object type.
     *
     * @param clazz the class that is being queried
     * @return <code>true</code> if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
