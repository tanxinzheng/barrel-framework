package com.github.tanxinzheng.framework.web.dictionary;

import com.github.tanxinzheng.framework.web.dictionary.domain.DictInfoVO;

import java.util.List;
import java.util.Map;

/**
 * Created by tanxinzheng on 16/10/20.
 */
public interface DictionaryInterpreterService {

    /**
     * 翻译
     * @param type    字典类型
     * @return
     */
    List<DictInfoVO> translate(String type);

    /**
     * 服务类型
     * @return
     */
    String getServiceType();
}
