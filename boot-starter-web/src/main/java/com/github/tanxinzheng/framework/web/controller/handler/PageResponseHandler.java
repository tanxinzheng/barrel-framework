package com.github.tanxinzheng.framework.web.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tanxinzheng.framework.model.BaseResultCode;
import com.github.tanxinzheng.framework.model.RestResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PageResponseHandler implements ResponseBodyHandler<String> {

    @Resource
    ObjectMapper objectMapper;

    /**
     * 判断ResponseBody是否匹配类型
     *
     * @param body
     * @param url
     * @return
     */
    @Override
    public boolean match(Object body, String url) {
        if(body instanceof RestResponse){
            return true;
        }
        return false;
    }

    /**
     * 类型匹配后的处理
     *
     * @param body
     * @return
     */
    @Override
    public Object doHandle(String body) {
        RestResponse result = RestResponse.success(body);
        try {
            //因为是String类型，我们要返回Json字符串，否则SpringBoot框架会转换出错
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            return RestResponse.failed(BaseResultCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
