package com.github.tanxinzheng.framework.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tanxinzheng on 17/6/6.
 */
@Data
public class BaseQuery implements Serializable {

    private BaseQueryCondition query;
    private Integer pageSize;
    private Integer pageNum;

    public BaseQuery() {
        this.pageNum = 1;
        this.pageSize = 20;
    }

    public void setDefaultPage(){
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
    }

}
