package com.github.tanxinzheng.framework.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tanxinzheng.framework.model.QueryWrapperCondition;
import com.github.tanxinzheng.framework.model.QueryWrapperSort;
import com.github.tanxinzheng.framework.mybatis.constants.ConditionType;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 万能查询参数转换工具类
 */
public class MybatisPlusUtils {

    /**
     * QueryWrapperCondition对象转QueryWrapper
     * @param conditions
     * @return
     */
    public static QueryWrapper getQueryWrapper4Condition(List<QueryWrapperCondition> conditions){
        return getQueryWrapper4Condition(conditions, null);
    }

    /**
     * QueryWrapperCondition对象转QueryWrapper
     * @param conditions
     * @return
     */
    public static QueryWrapper getQueryWrapper4Condition(List<QueryWrapperCondition> conditions,
                                                         List<QueryWrapperSort> sorts){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(CollectionUtils.isNotEmpty(conditions)) {
            for (QueryWrapperCondition conditionVo : conditions) {
                if(StringUtils.isNotBlank(conditionVo.getColumn())
                        && StringUtils.isNotBlank(conditionVo.getType())
                        && StringUtils.isNotBlank(conditionVo.getValue())
                        && !"null".equalsIgnoreCase(conditionVo.getColumn())
                        && !"null".equalsIgnoreCase(conditionVo.getType())
                        && !"null".equalsIgnoreCase(conditionVo.getValue())) {
                    switch (ConditionType.valueOf(conditionVo.getType().toUpperCase())) {
                        case NOT_IN:
                            List<String> notInList = Lists.newArrayList();
                            if (conditionVo.getValue().startsWith("[")) {
                                notInList = JSON.parseArray(conditionVo.getValue(), String.class);
                            } else {
                                notInList.add(conditionVo.getValue());
                            }
                            queryWrapper.notIn(conditionVo.getColumn(), notInList);
                            break;
                        case IN:
                            List<String> inList = Lists.newArrayList();
                            if (conditionVo.getValue().startsWith("[")) {
                                inList = JSON.parseArray(conditionVo.getValue(), String.class);
                            } else {
                                inList.add(conditionVo.getValue());
                            }
                            queryWrapper.in(conditionVo.getColumn(), inList);
                            break;
                        case EQ:
                            queryWrapper.eq(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case NE:
                            queryWrapper.ne(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case LIKE:
                            queryWrapper.like(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case LIKE_LEFT:
                            queryWrapper.likeLeft(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case LIKE_RIGHT:
                            queryWrapper.likeRight(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case NOT_LIKE:
                            queryWrapper.notLike(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case GT:
                            queryWrapper.gt(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case LT:
                            queryWrapper.lt(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case GE:
                            queryWrapper.ge(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        case LE:
                            queryWrapper.le(conditionVo.getColumn(), conditionVo.getValue());
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + conditionVo.getType());
                    }
                }
            }
        }
        if(CollectionUtils.isEmpty(sorts)){
            return queryWrapper;
        }
        for (QueryWrapperSort sortVo : sorts) {
            if(StringUtils.isBlank(sortVo.getColumn())
                    || StringUtils.isBlank(sortVo.getType())
                    || "null".equalsIgnoreCase(sortVo.getType())
                    || "null".equalsIgnoreCase(sortVo.getColumn())){
                continue;
            }
            switch (ConditionType.valueOf(sortVo.getType().toUpperCase())) {
                case ASC:
                    queryWrapper.orderByAsc(sortVo.getValue());
                    break;
                case DESC:
                    queryWrapper.orderByDesc(sortVo.getValue());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sortVo.getType());
            }
        }
        return queryWrapper;
    }
}
