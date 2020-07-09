package com.github.tanxinzheng.framework.web.handler;

import com.github.tanxinzheng.framework.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanxinzheng on 18/2/9.
 */
@Slf4j
public class LogbackMDCInterceptor extends HandlerInterceptorAdapter {

    /**
     * 会话ID
     */
    private final static String USERNAME = "username";
    private final static String REQUEST_ID = "requestId";

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // 删除
        MDC.remove(USERNAME);
        MDC.remove(REQUEST_ID);
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String username = (String) request.getAttribute(USERNAME);
        String requestId = UUIDGenerator.getInstance().getUUID();
        if(StringUtils.isNotBlank(username)){
            MDC.put(USERNAME, username);
        }else{
            MDC.put(USERNAME, "-");
        }
        if(StringUtils.isNotBlank(requestId)){
            MDC.put(REQUEST_ID, requestId);
        }else{
            MDC.put(REQUEST_ID, "-");
        }
        return true;
    }

}
