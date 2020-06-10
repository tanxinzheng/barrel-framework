package com.github.tanxinzheng.framework.web.controller.handler;

import com.github.tanxinzheng.framework.model.RestResponse;
import org.springframework.stereotype.Component;

@Component
public class ResultResponseHandler implements ResponseBodyHandler<RestResponse> {
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
    public Object doHandle(RestResponse body) {
        if(body != null && body.isKeepOriginFormat()){
            return body.getData();
        }
        return body;
    }
}
