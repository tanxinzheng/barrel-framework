package com.github.tanxinzheng.jwt.access;

import com.github.tanxinzheng.jwt.handler.SecurityMetadataHandler;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by tanxinzheng on 2018/10/21.
 */
@Component
public class JwtFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    SecurityMetadataHandler securityMetadataHandler;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
     *
     * @param object the object being secured
     * @return the attributes that apply to the passed in secured object. Should return an
     * empty collection if there are no applicable attributes.
     * @throws IllegalArgumentException if the passed object is not of a type supported by
     *                                  the <code>SecurityMetadataSource</code> implementation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String requestUrl = fi.getRequestUrl();
        String requestMethod = fi.getRequest().getMethod();
        RequestMethod method = RequestMethod.valueOf(requestMethod);
        List<PermissionGrantedAuthority> authorityList = securityMetadataHandler.loadAllPermission();
        if(CollectionUtils.isNotEmpty(authorityList)){
            for (PermissionGrantedAuthority permissionGrantedAuthority : authorityList) {
                if(antPathMatcher.match(permissionGrantedAuthority.getUrl(), requestUrl)
                        && permissionGrantedAuthority.getRequestMethod().equals(method)){
                    return SecurityConfig.createList(permissionGrantedAuthority.getRoles()
                            .toArray(new String[permissionGrantedAuthority.getRoles().size()]));
                }
            }
        }
        //没有匹配到授权资源的角色，默认是要登录才能访问
        return SecurityConfig.createList("ROLE_USER");
    }

    /**
     * If available, returns all of the {@code ConfigAttribute}s defined by the
     * implementing class.
     * <p>
     * This is used by the {@link AbstractSecurityInterceptor} to perform startup time
     * validation of each {@code ConfigAttribute} configured against it.
     *
     * @return the {@code ConfigAttribute}s or {@code null} if unsupported
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Lists.newArrayList();
    }

    /**
     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
     * provide {@code ConfigAttribute}s for the indicated secure object type.
     *
     * @param clazz the class that is being queried
     * @return true if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
