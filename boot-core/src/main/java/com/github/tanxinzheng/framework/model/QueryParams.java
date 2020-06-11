package com.github.tanxinzheng.framework.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.tanxinzheng.framework.utils.MybatisPlusUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryParams extends BaseQuery implements Serializable {

    @ApiModelProperty(value = "查询参数")
    private List<QueryWrapperCondition> conditions;
    @ApiModelProperty(value = "排序参数", hidden = true)
    @JsonIgnore
    private List<QueryWrapperSort> sorts;

    @ApiModelProperty(hidden=true)
    @JsonIgnore
    public QueryWrapper getQueryWrapper(){
        return MybatisPlusUtils.getQueryWrapper4Condition(this.conditions, this.sorts);
    }

}
