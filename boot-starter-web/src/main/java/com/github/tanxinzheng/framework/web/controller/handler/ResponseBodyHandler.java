package com.github.tanxinzheng.framework.web.controller.handler;

public interface ResponseBodyHandler<T> {

    /**
     * 判断ResponseBody是否匹配类型
     * @param body
     * @param url
     * @return
     */
    boolean match(Object body, String url);

    /**
     * 类型匹配后的处理
     * @param body
     * @return
     */
    Object doHandle(T body);
}
