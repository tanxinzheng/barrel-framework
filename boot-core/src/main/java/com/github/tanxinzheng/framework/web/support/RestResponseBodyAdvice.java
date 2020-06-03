package com.github.tanxinzheng.framework.web.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import com.google.common.collect.Maps;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Jeng on 2016/1/21.
 */
@ControllerAdvice
public class RestResponseBodyAdvice implements ResponseBodyAdvice {

    private ThreadLocal<ObjectMapper>  mapperThreadLocal = ThreadLocal.withInitial(ObjectMapper::new);

    private static final Class[] annos = {
            RequestMapping.class,
            GetMapping.class,
            PostMapping.class,
            DeleteMapping.class,
            PutMapping.class
    };

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
        ObjectMapper mapper = mapperThreadLocal.get();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        if (body instanceof String){
            RestResponse result = RestResponse.success(body);
            try {
                //因为是String类型，我们要返回Json字符串，否则SpringBoot框架会转换出错
                return mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                return RestResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }else if(body instanceof Page){
            Page page = (Page) body;
            Map<String, Object> data = Maps.newHashMap();
            data.put("page", page.toPageInfo());
            data.put("data", page.getResult());
            return RestResponse.success(data);
        }else if(body instanceof RestResponse){
            RestResponse restResponse = (RestResponse) body;
            if(restResponse.isKeepOriginFormat()){
                return restResponse.getData();
            }
            return body;
        }
        return RestResponse.success(body);
    }
}
