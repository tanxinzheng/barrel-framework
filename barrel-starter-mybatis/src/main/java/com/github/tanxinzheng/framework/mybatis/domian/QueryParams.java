package com.github.tanxinzheng.framework.mybatis.domian;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.tanxinzheng.framework.model.BaseQuery;
import com.github.tanxinzheng.framework.mybatis.utils.MybatisPlusUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryParams<T> extends BaseQuery implements Serializable {


    @ApiModelProperty(value = "查询参数")
    private List<QueryWrapperCondition> conditions;
    @ApiModelProperty(value = "排序参数", hidden = true)
    @JsonIgnore
    private List<QueryWrapperSort> sorts;

    @ApiModelProperty(hidden=true)
    @JsonIgnore
    public QueryWrapper<T> getQueryWrapper(){
        return MybatisPlusUtils.getQueryWrapper4Condition(this.conditions, this.sorts);
    }

    public IPage<T> getPage(){
        return new Page<T>(this.getPageNum(), this.getPageSize());
    }

}
