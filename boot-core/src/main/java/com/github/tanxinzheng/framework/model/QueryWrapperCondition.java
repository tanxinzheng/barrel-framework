package com.github.tanxinzheng.framework.model;

import lombok.Data;

@Data
public class QueryWrapperCondition {

    /**
     * 连接类型，如llike,equals,gt,ge,lt,le
     */
    private String type;
    private String column;
    private String value;
}
