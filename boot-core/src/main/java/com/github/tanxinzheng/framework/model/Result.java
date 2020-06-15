package com.github.tanxinzheng.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.tanxinzheng.framework.exception.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tanxinzheng on 2018/9/27.
 */
@Data
@ApiModel("返回对象")
public class Result<T> implements Serializable {

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

    public Result() {
        this.timestamp = LocalDateTime.now();
    }

    public Result(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


    public static Result success(Object data, String message) {
        Result result = new Result(data);
        result.setMessage(message);
        result.setCode(BaseResultCode.OK.getCode());
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static Result success(Object data) {
        return success(data, "SUCCESS");
    }

    public static Result failed(ResultCode resultCode) {
        return failed(resultCode, resultCode.getCode());
    }

    public static Result failed(ResultCode resultCode, Exception ex) {
        return failed(resultCode, ex.getMessage());
    }

    public static Result failed(ResultCode resultCode, String message) {
        return failed(resultCode.getCode(), message);
    }

    public static Result failed(String code, String message) {
        Result result = new Result();
        result.setMessage(message);
        result.setCode(code);
        result.setSuccess(Boolean.FALSE);
        return result;
    }

    public void toJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        toJSON(request, response);
    }



}
