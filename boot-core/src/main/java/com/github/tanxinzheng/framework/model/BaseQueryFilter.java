package com.github.tanxinzheng.framework.model;

import lombok.Data;

@Data
public class BaseQueryFilter {

    private String field;
    private String act;
    private Object value;
}
