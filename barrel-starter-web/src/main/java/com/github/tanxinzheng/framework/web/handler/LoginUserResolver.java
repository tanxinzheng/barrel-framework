package com.github.tanxinzheng.framework.web.handler;

import com.github.tanxinzheng.framework.secure.config.SecureProperties;
import com.github.tanxinzheng.framework.utils.AssertValid;
import com.github.tanxinzheng.framework.web.annotation.LoginUser;
import com.github.tanxinzheng.framework.web.model.CurrentLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/**
 * 解析@LoginUser注解参数，当前登录用户信息
 */
@Slf4j
@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {

    @Resource
    SecureProperties secureProperties;

    @Resource
    RedisTemplate redisTemplate;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(CurrentLoginUser.class) && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public CurrentLoginUser resolveArgument(MethodParameter methodParameter,
                                            ModelAndViewContainer modelAndViewContainer,
                                            NativeWebRequest nativeWebRequest,
                                            WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader(secureProperties.getTokenHeaderName());
        String tokenKey = secureProperties.getTokenHeaderName() + ":" + token;
        log.info("the request token key:{} ", tokenKey);
        CurrentLoginUser currentLoginUser = (CurrentLoginUser) redisTemplate.opsForValue().get(tokenKey);
        AssertValid.notNull(currentLoginUser, "该接口访问受限，请登录后再访问。");
        return currentLoginUser;
    }
}
