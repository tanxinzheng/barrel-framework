package com.github.tanxinzheng.framework.mybatis.domian;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryWrapperSort {

    @ApiModelProperty(value = "排序类型：ASC | DESC")
    private String type;
    @ApiModelProperty(value = "字段名")
    private String column;
    @ApiModelProperty(value = "字段值")
    private String value;
}
