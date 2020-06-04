package com.github.tanxinzheng.boot.starter.web.test.domain;

import com.github.tanxinzheng.module.dictionary.web.AccountField;
import com.github.tanxinzheng.module.dictionary.web.DictionaryTransfer;
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
