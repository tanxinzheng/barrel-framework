package com.github.tanxinzheng.framework.web.model;

import lombok.Data;

/**
 * Created by tanxinzheng on 2018/9/27.
 */
@Data
public class ErrorRestResponse<T> extends RestResponse {

    private Object error;

}
