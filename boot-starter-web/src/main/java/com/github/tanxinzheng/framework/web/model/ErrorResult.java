package com.github.tanxinzheng.framework.web.model;

import com.github.tanxinzheng.framework.model.Result;
import lombok.Data;

/**
 * Created by tanxinzheng on 2018/9/27.
 */
@Data
public class ErrorResult<T> extends Result {

    private Object error;

}
