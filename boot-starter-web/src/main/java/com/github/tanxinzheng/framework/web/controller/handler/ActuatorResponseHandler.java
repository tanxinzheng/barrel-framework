package com.github.tanxinzheng.framework.web.controller.handler;

import org.springframework.stereotype.Component;

@Component
public class ActuatorResponseHandler implements ResponseBodyHandler {
    /**
     * 判断ResponseBody是否匹配类型
     *
     * @param body
     * @param url
     * @return
     */
    @Override
    public boolean match(Object body, String url) {
//        if(body instanceof Result){
//            return true;
//        }
        return false;
    }

    /**
     * 类型匹配后的处理
     *
     * @param body
     * @return
     */
    @Override
    public Object doHandle(Object body) {
        return body;
    }
}
