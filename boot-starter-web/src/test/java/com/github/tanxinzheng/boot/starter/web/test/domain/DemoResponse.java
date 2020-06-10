package com.github.tanxinzheng.boot.starter.web.test.domain;

import com.github.tanxinzheng.framework.web.annotation.AccountField;
import com.github.tanxinzheng.framework.web.annotation.DictionaryTransfer;
import lombok.Data;

@Data
public class DemoResponse {

    private String id;
    @AccountField
    private String userId;
    @DictionaryTransfer(index = "SEX")
    private String sex;
    @DictionaryTransfer(index = "DISABLE", fieldName = "disableName")
    private String disable;
}
