package com.github.tanxinzheng.framework.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tanxinzheng.framework.utils.MybatisPlusUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tanxinzheng on 17/6/6.
 */
@Data
public class BaseQuery implements Serializable {

    private BaseQueryCondition query;
    private String keyword;
    private Long pageSize;
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
