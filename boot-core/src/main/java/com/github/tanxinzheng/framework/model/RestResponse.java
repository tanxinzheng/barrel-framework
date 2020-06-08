package com.github.tanxinzheng.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tanxinzheng.framework.exception.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tanxinzheng on 2018/9/27.
 */
@Data
public class RestResponse<T> implements Serializable {

    private String code;
    private String timestamp;
    private Integer status;
    private String message;
    private T data;
    /**
     * 是否保持源对象输出
     */
    @JsonIgnore
    private boolean keepOriginFormat;

    public RestResponse() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public RestResponse(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static RestResponse success(Object data) {
        RestResponse restResponse = new RestResponse(data);
        restResponse.setMessage("SUCCESS");
        restResponse.setStatus(HttpStatus.OK.value());
        restResponse.setCode(String.valueOf(HttpStatus.OK.value()));
        return restResponse;
    }

    public static RestResponse failed(ErrorCode errorCode) {
        return failed(errorCode, null);
    }

    public static RestResponse failed(ErrorCode errorCode, Exception ex) {
        RestResponse restResponse = new RestResponse();
        restResponse.setMessage(errorCode.getErrorDesc());
        restResponse.setStatus(HttpStatus.OK.value());
        restResponse.setCode(errorCode.getErrorCode());
        return restResponse;
    }

    public static RestResponse failed(HttpStatus code, String message) {
        RestResponse restResponse = new RestResponse();
        restResponse.setMessage(message);
        restResponse.setStatus(code.value());
        restResponse.setCode(String.valueOf(code.value()));
        return restResponse;
    }

    public static RestResponse failed(HttpStatus code, Exception ex) {
        RestResponse restResponse = RestResponse.failed(code, ex.getMessage());
        return restResponse;
    }


    public static RestResponse validateFailed(String message) {
        RestResponse restResponse = new RestResponse();
        restResponse.setMessage(message);
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return restResponse;
    }

    public static RestResponse validateFailed() {
        return RestResponse.validateFailed("请求参数校验不通过，请检查参数是否正常");
    }

    public void toJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        toJSON(request, response, HttpStatus.valueOf(this.status));
    }

    public void toJSON(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());
        this.status = httpStatus.value();
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        mapper.writeValue(out, this);
        out.flush();
    }

}