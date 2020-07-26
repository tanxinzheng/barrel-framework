package com.github.tanxinzheng.framework.web.dictionary.impl;

import com.github.tanxinzheng.framework.web.dictionary.DictionaryTransferService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
public class DictionaryTransferServiceImpl implements DictionaryTransferService {
    /**
     * 翻译
     *
     * @param type 字典类型
     * @param code 字典代码
     * @return
     */
    @Override
    public Map<String, Object> translate(String type, String code) {
        return null;
    }

    /**
     * 字典索引
     *
     * @return
     */
    @Override
    public String getDictionaryIndex() {
        return null;
    }
}
