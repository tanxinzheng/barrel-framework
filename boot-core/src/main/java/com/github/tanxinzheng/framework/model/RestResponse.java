package com.github.tanxinzheng.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tanxinzheng.framework.exception.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tanxinzheng on 2018/9/27.
 */
@Data
@ApiModel("返回对象")
public class RestResponse<T> implements Serializable {

    @ApiModelProperty(value = "代码", example = "200")
    private String code;
    @ApiModelProperty(value = "时间戳", example = "2020-01-02 12:00:00")
    private LocalDateTime timestamp;
    @ApiModelProperty(value = "提示信息", example = "SUCCESS")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "是否成功")
    private boolean success;

    /**
     * 是否保持源对象输出
     */
    @JsonIgnore
    private boolean keepOriginFormat;

    public RestResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public RestResponse(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


    public static RestResponse success(Object data, String message) {
        RestResponse restResponse = new RestResponse(data);
        restResponse.setMessage(message);
        restResponse.setCode(String.valueOf(HttpStatus.OK.value()));
        restResponse.setSuccess(Boolean.TRUE);
        return restResponse;
    }

    public static RestResponse success(Object data) {
        return success(data, "SUCCESS");
    }

    public static RestResponse failed(ResultCode resultCode) {
        return failed(resultCode, resultCode.getCode());
    }

    public static RestResponse failed(ResultCode resultCode, Exception ex) {
        return failed(resultCode, ex.getMessage());
    }

    public static RestResponse failed(ResultCode resultCode, String message) {
        RestResponse restResponse = new RestResponse();
        restResponse.setMessage(message);
        restResponse.setCode(resultCode.getCode());
        restResponse.setSuccess(Boolean.FALSE);
        return restResponse;
    }

    public void toJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        toJSON(request, response);
    }

    public void toJSON(HttpServletRequest request, HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(httpStatus.value());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false);
        mapper.writeValue(out, this);
        out.flush();
    }

}
