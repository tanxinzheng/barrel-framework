package com.github.tanxinzheng.module.dictionary.web;

import java.util.Map;

/**
 * Created by tanxinzheng on 16/10/20.
 */
public interface DictionaryTransferService {

    /**
     * 翻译
     * @param type    字典类型
     * @param code    字典代码
     * @return
     */
    Map<String, Object> translate(String type, String code);

    /**
     * 字典索引
     * @return
     */
    String getDictionaryIndex();
}
