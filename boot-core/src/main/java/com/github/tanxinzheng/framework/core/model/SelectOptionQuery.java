package com.github.tanxinzheng.framework.core.model;

import com.github.tanxinzheng.framework.model.BaseQuery;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Created by tanxinzheng on 17/6/28.
 */
@Data
public class SelectOptionQuery extends BaseQuery {
    @NotBlank(message = "typeCode为必填项")
    private String typeCode;
    private String parentId;
    private String keyword;
}
