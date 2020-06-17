package com.github.tanxinzheng.framework.core.service;

import com.github.tanxinzheng.framework.core.model.SelectIndex;
import com.github.tanxinzheng.framework.core.model.SelectOptionModel;
import com.github.tanxinzheng.framework.core.model.SelectOptionQuery;

import java.util.List;

/**
 * Created by tanxinzheng on 17/7/16.
 */
public interface SelectService {

    /**
     * 查询option数据
     * @param selectOptionQuery
     * @return
     */
    public List<SelectOptionModel> selectOptionModels(SelectOptionQuery selectOptionQuery);

    /**
     * 获取select index
     * @return
     */
    public SelectIndex getSelectIndex();


}
