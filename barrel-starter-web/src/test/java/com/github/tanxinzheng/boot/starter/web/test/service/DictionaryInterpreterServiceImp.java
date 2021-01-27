package com.github.tanxinzheng.boot.starter.web.test.service;

import com.github.tanxinzheng.framework.web.dictionary.DictionaryInterpreterService;
import com.github.tanxinzheng.framework.web.dictionary.domain.DictInfoVO;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DictionaryInterpreterServiceImp implements DictionaryInterpreterService {
    /**
     * 翻译
     *
     * @param type 字典类型
     * @return
     */
    @Override
    public List<DictInfoVO> translate(String type) {
        if(type.equalsIgnoreCase("SEX")){
            return Lists.newArrayList(DictInfoVO.builder()
                    .code("W")
                    .desc("女")
                    .sort(1).build(), DictInfoVO.builder()
                    .code("M")
                    .desc("男")
                    .sort(2).build());
        }
        if(type.equalsIgnoreCase("DISABLE")){
            return Lists.newArrayList(DictInfoVO.builder()
                    .code("true")
                    .desc("禁用")
                    .sort(1).build(), DictInfoVO.builder()
                    .code("false")
                    .desc("启用")
                    .sort(2).build());
        }
        return Lists.newArrayList();
    }

    /**
     * 服务类型
     *
     * @return
     */
    @Override
    public String getServiceType() {
        return "DICT_INFO";
    }
}
