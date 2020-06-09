package com.github.tanxinzheng.framework.web.rest;

import com.github.tanxinzheng.framework.exception.BusinessException;
import com.github.tanxinzheng.framework.model.BaseErrorCode;
import com.github.tanxinzheng.framework.utils.DateTimeUtils;
import com.github.tanxinzheng.framework.web.model.ErrorRestResponse;
import com.github.tanxinzheng.framework.web.model.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeng on 15/11/29.
 */
@Slf4j
@Order(1)
@ControllerAdvice
public class RestExceptionHandler {

//    @Value(value = "${spring.servlet.multipart.max-file-size}")
    private Long maxUploadSize = 102400l;


    @ExceptionHandler(value = {
            RuntimeException.class,
            BusinessException.class,
            AccessDeniedException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseBody
    public RestResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        String eventNo = DateTimeUtils.getDatetimeString(new Date()) + RandomStringUtils.randomNumeric(4);
        log.error("Event No: " + eventNo + " -> " + ex.getMessage(), ex);
        RestResponse restError = RestResponse.failed(BaseErrorCode.SYSTEM_ERROR, ex);
        restError.setMessage("系统异常，请联系管理员，异常事件编号：" + eventNo);
        if(ex instanceof BindException){
            BindException bindException = (BindException) ex;
            restError = handleBindException(bindException.getBindingResult(), bindException);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            restError = handleBindException(methodArgumentNotValidException.getBindingResult(), methodArgumentNotValidException);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof IllegalArgumentException ||
                ex instanceof BusinessException){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            restError.setStatus(HttpStatus.BAD_REQUEST.value());
        }else if(ex instanceof MaxUploadSizeExceededException){
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            restError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            restError.setMessage(MessageFormat.format("文件上传限制最大不能超过{0}M" , (maxUploadSize/1024)/1024));
        }else if(ex instanceof AccessDeniedException){
            response.setStatus(HttpStatus.FORBIDDEN.value());
            restError.setStatus(HttpStatus.FORBIDDEN.value());
            restError.setMessage(ex.getMessage());
        }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            restError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return restError;
    }

    protected ErrorRestResponse handleBindException(BindingResult bindingResult, Exception ex) {
        ErrorRestResponse restError = (ErrorRestResponse) ErrorRestResponse.failed(HttpStatus.BAD_REQUEST, "非法请求参数，校验请求参数不合法");
        BindingResult result = bindingResult;
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        List<FieldError> fieldErrorList = new ArrayList<FieldError>();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            FieldError error = new FieldError();
            error.setMessage(fieldError.getDefaultMessage());
            error.setField(fieldError.getField());
            error.setRejectedValue(fieldError.getRejectedValue());
            error.setObjectName(fieldError.getObjectName());
            fieldErrorList.add(error);
        }
        if(!CollectionUtils.isEmpty(fieldErrorList)){
            restError.setError(fieldErrorList);
            FieldError fieldError = fieldErrorList.get(0);
            if(StringUtils.trimToNull(fieldError.getField()) != null && StringUtils.trimToNull(fieldError.getMessage()) != null ){
                restError.setMessage(fieldError.getMessage());
            }
        }
        return restError;
    }

}
