package com.github.tanxinzheng.framework.web.controller.advice;

import com.github.tanxinzheng.framework.exception.ApiException;
import com.github.tanxinzheng.framework.exception.AuthException;
import com.github.tanxinzheng.framework.exception.BusinessException;
import com.github.tanxinzheng.framework.model.BaseResultCode;
import com.github.tanxinzheng.framework.model.Result;
import com.github.tanxinzheng.framework.utils.DateTimeUtils;
import com.github.tanxinzheng.framework.web.model.ErrorResult;
import com.github.tanxinzheng.framework.web.rest.FieldError;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Jeng on 15/11/29.
 */
@Slf4j
@Order(1)
@ControllerAdvice
public class GlobalExceptionControllerAdvice extends ResponseEntityExceptionHandler {

//    @Value(value = "${spring.servlet.multipart.max-file-size}")
    private Long maxUploadSize = 102400l;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            BusinessException.class,
            ApiException.class,
            IllegalArgumentException.class
    })
    @ResponseBody
    ResponseEntity<Object> handleBusinessException(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   Exception e){
        logger.debug(e.getMessage(), e);
        return ResponseEntity.badRequest().body(Result.failed(BaseResultCode.BAD_PARAMETERS, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    ResponseEntity<Object> handleMaxUploadSizeExceededException(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   BusinessException exception){
        logger.error(exception.getMessage(), exception);
        Result result = Result.failed(BaseResultCode.BAD_PARAMETERS, MessageFormat.format("文件上传限制最大不能超过{0}M" , (maxUploadSize/1024)/1024));
        return ResponseEntity.badRequest().body(result);
    }

    @Override
    public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResult errorResult = handleBindException(ex.getBindingResult(), ex);
        return ResponseEntity.badRequest().body(errorResult);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug(ex.getMessage(), ex);
        ErrorResult errorResult = handleBindException(ex.getBindingResult(), ex);
        return ResponseEntity.badRequest().body(errorResult);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.debug(ex.getMessage(), ex);
        Result errorResult = Result.failed(BaseResultCode.BAD_PARAMETERS, ex.getMessage());
        return ResponseEntity.badRequest().body(errorResult);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    ResponseEntity<Object> handleAuthException(HttpServletRequest request,
                                               HttpServletResponse response,
                                               AuthException exception){
        logger.debug(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Result.failed(BaseResultCode.UNAUTHORIZED, exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    ResponseEntity<Object> handleAccessDeniedException(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       AccessDeniedException exception){
        logger.debug(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Result.failed(BaseResultCode.BAD_PARAMETERS, exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    ResponseEntity<Object>  exceptionHandler(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Exception exception){
        String eventNo = DateTimeUtils.getDatetimeString(new Date()) + RandomStringUtils.randomNumeric(4);
        log.error("Event No: " + eventNo + " -> " + exception.getMessage(), exception);
        ErrorResult restError = new ErrorResult();
        restError.setCode(BaseResultCode.SYSTEM_ERROR.getCode());
        restError.setTimestamp(LocalDateTime.now());
        restError.setSuccess(Boolean.FALSE);
        restError.setMessage("服务器内部异常，请联系管理员，异常事件编号：" + eventNo);
        restError.setError(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(restError);
    }

    /**
     * 校验参数错误信息处理
     * @param bindingResult
     * @param ex
     * @return
     */
    protected ErrorResult handleBindException(BindingResult bindingResult, Exception ex) {
        ErrorResult restError = new ErrorResult();
        restError.setCode(BaseResultCode.BAD_PARAMETERS.getCode());
        restError.setSuccess(Boolean.FALSE);
        restError.setTimestamp(LocalDateTime.now());
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

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }
        if(body == null){
            body = Result.failed(String.valueOf(status.value()), ex.getMessage());
        }

        return new ResponseEntity(body, headers, status);
    }

}
