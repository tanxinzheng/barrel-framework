package com.github.tanxinzheng.framework.web.dictionary.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
public class DictInfoVO implements Serializable {

    private String code;
    private String desc;
    private Integer sort;
    private Map<String, Object> properties;
}
