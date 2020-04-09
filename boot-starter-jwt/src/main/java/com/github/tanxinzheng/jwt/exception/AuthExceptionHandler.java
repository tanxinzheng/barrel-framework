package com.github.tanxinzheng.jwt.exception;

import com.github.tanxinzheng.framework.web.model.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

/**
 * Created by Jeng on 15/11/29.
 */
@Slf4j
@Order(1)
@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            AccessDeniedException.class
    })
    @ResponseBody
    public RestResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        RestResponse restError = RestResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        if(ex instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            restError.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof BadCredentialsException){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            restError.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof AccessDeniedException){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            restError.setStatus(HttpStatus.FORBIDDEN.value());
        }
        restError.setError(ex.getMessage());
        restError.setCode(String.valueOf(restError.getStatus()));
        log.debug(ex.getMessage(), ex);
        return restError;
    }

}
