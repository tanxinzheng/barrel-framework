package com.github.tanxinzheng.framework.mybatis.domian;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryWrapperCondition {

    @ApiModelProperty(value = "连接类型，如like,equals,gt,ge,lt,le, 具体参考ConditionType")
    private String type;
    @ApiModelProperty(value = "字段名")
    private String column;
    @ApiModelProperty(value = "字段值")
    private String value;
}
