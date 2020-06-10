package com.github.tanxinzheng.framework.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tanxinzheng.framework.utils.MybatisPlusUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tanxinzheng on 17/6/6.
 */
@Data
@ApiModel(value = "查询条件基类")
public class BaseQuery implements Serializable {

    private BaseQueryCondition query;

    @ApiModelProperty(value = "关键词")
    private String keyword;
    @ApiModelProperty(value = "每页条数")
    private Long pageSize;
    @ApiModelProperty(value = "当前页码")
    private Long pageNum;

    private List<QueryWrapperCondition> conditions;

    public QueryWrapper getQueryWrapper(){
        return MybatisPlusUtils.getQueryWrapper4Condition(this.conditions);
    }

    public BaseQuery() {
        this.pageNum = 1l;
        this.pageSize = 20l;
    }

    public void setDefaultPage(){
        if(pageNum == null){
            pageNum = 1l;
        }
        if(pageSize == null){
            pageSize = 20l;
        }
    }

}
