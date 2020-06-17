package com.github.tanxinzheng.framework.web.controller.handler;

import org.springframework.stereotype.Component;

@Component
public class SwaggerPathResponseHandler implements ResponseBodyHandler {

    @Override
    public boolean match(Object body, String url) {
        if(url.equalsIgnoreCase("/v2/api-docs")){
            return true;
        }
        return false;
    }

    @Override
    public Object doHandle(Object body) {
        return body;
    }
}
