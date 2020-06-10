package com.github.tanxinzheng.framework.web.controller.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tanxinzheng.framework.web.controller.handler.ResponseBodyHandler;
import com.github.tanxinzheng.framework.model.RestResponse;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jeng on 2016/1/21.
 */
@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    @Resource
    ObjectMapper objectMapper;

    private static final Class[] annos = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

    private final List<ResponseBodyHandler> responseBodyHandlers = Lists.newArrayList();


    @Autowired(required = false)
    public void setHandler(List<ResponseBodyHandler> responseBodyHandlerList){
        if(CollectionUtils.isEmpty(responseBodyHandlerList)){
            return;
        }
        this.responseBodyHandlers.addAll(responseBodyHandlerList);
    }

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked, {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        AnnotatedElement element = returnType.getAnnotatedElement();
        return Arrays.stream(annos).anyMatch(anno -> anno.isAnnotation() && element.isAnnotationPresent(anno));
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified, possibly new instance
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        Optional<ResponseBodyHandler> handler =
                this.responseBodyHandlers.stream().filter(responseBodyHandler -> responseBodyHandler
                        .match(body, ((ServletServerHttpRequest) request).getServletRequest().getServletPath()))
                        .findFirst();
        if(handler.isPresent()){
           return handler.get().doHandle(body);
        }
        return RestResponse.success(body);
    }
}
