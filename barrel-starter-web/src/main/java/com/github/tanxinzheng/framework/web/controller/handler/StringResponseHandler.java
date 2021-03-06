package com.github.tanxinzheng.framework.web.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tanxinzheng.framework.model.BaseResultCode;
import com.github.tanxinzheng.framework.model.Result;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class StringResponseHandler implements ResponseBodyHandler<String> {

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
        if(body instanceof Result){
            Result result = (Result) body;
            if(null != result.getData() && result.getData() instanceof String){
                return true;
            }
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
        Result result = Result.success(body);
        try {
            //因为是String类型，我们要返回Json字符串，否则SpringBoot框架会转换出错
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            return Result.failed(BaseResultCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
