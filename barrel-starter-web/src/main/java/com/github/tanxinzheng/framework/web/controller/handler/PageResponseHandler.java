package com.github.tanxinzheng.framework.web.controller.handler;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tanxinzheng.framework.model.BaseResultCode;
import com.github.tanxinzheng.framework.model.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class PageResponseHandler implements ResponseBodyHandler<IPage> {

    @Qualifier(value = "objectMapper")
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
        if(body instanceof IPage){
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
    public Object doHandle(IPage body) {
        Result result = Result.success(body);
        return result;
    }
}
