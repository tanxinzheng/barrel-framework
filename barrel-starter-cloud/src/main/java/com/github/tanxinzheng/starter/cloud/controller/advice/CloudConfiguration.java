package com.github.tanxinzheng.starter.cloud.controller.advice;

import com.alibaba.fastjson.JSONObject;
import com.github.tanxinzheng.framework.exception.ApiException;
import com.github.tanxinzheng.framework.model.Result;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.Charset;

/*
 * @Description TODO
 * @Author tanxinzheng
 * @Date 2020/7/5
 */
@Slf4j
@Configuration
public class CloudConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }
    /**
     * 重新实现feign的异常处理，捕捉restful接口返回的json格式的异常信息
     *
     */
    public class UserErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                String json = Util.toString(response.body().asReader(Charset.defaultCharset()));
                exception = new RuntimeException(json);
                if (StringUtils.isEmpty(json)) {
                    return null;
                }
                Result result = JSONObject.parseObject(json, Result.class);
                // 业务异常包装成自定义异常类MyException
                if (!result.isSuccess()) {
                    exception = new ApiException(result);
                }
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
