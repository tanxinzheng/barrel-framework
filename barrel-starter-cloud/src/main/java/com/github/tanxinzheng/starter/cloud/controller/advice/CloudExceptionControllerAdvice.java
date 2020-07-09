package com.github.tanxinzheng.starter.cloud.controller.advice;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @Description TODO
 * @Author tanxinzheng
 * @Date 2020/6/27
 */
@Slf4j
@Order(1)
@ControllerAdvice
public class CloudExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            FeignException.class
    })
    @ResponseBody
    ResponseEntity<Object> handleBusinessException(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   FeignException e){
        logger.debug(e.getMessage(), e);
        return ResponseEntity.status(e.status()).body(e.responseBody());
    }
}
