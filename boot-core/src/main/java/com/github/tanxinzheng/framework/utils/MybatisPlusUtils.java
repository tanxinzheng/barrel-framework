package com.github.tanxinzheng.framework.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tanxinzheng.framework.model.QueryWrapperCondition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MybatisPlusUtils {

    /**
     * QueryWrapperConditionJson对象转QueryWrapper
     * @param conditionJson
     * @return
     */
    public static QueryWrapper getQueryWrapper4ConditionJson(String conditionJson){
        if(StringUtils.isNotBlank(conditionJson)){
            List<QueryWrapperCondition> conditionList = JSON.parseArray(conditionJson, QueryWrapperCondition.class);
            return getQueryWrapper4Condition(conditionList);
        }
        return new QueryWrapper();
    }

    /**
     * QueryWrapperCondition对象转QueryWrapper
     * @param conditions
     * @return
     */
    public static QueryWrapper getQueryWrapper4Condition(List<QueryWrapperCondition> conditions){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(CollectionUtils.isNotEmpty(conditions)) {
            for (QueryWrapperCondition conditionVo : conditions) {
                switch (conditionVo.getType()) {
                    case "eq":
                        queryWrapper.eq(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "ne":
                        queryWrapper.ne(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "like":
                        queryWrapper.like(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "leftlike":
                        queryWrapper.likeLeft(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "rightlike":
                        queryWrapper.likeRight(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "notlike":
                        queryWrapper.notLike(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "gt":
                        queryWrapper.gt(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "lt":
                        queryWrapper.lt(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "ge":
                        queryWrapper.ge(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                    case "le":
                        queryWrapper.le(conditionVo.getColumn(), conditionVo.getValue());
                        break;
                }
            }
        }
        return queryWrapper;
    }
}
