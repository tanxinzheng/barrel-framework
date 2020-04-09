package com.github.tanxinzheng.framework.model;

import lombok.Data;

import java.util.List;

@Data
public class BaseQueryCondition {

    private List<BaseQuerySort> sorts;
    private List<BaseQueryFilter> filters;
}
