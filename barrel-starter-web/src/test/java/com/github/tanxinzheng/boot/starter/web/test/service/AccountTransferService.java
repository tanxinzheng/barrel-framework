package com.github.tanxinzheng.boot.starter.web.test.service;

import com.github.tanxinzheng.framework.web.dictionary.AccountInterpreterService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccountTransferService implements AccountInterpreterService {
    /**
     * 翻译
     *
     * @param userId
     * @return
     */
    @Override
    public Object translateAccount(String userId) {
        if(userId.equalsIgnoreCase("1")){
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", "测试人员");
            return map;
        }
        if(userId.equalsIgnoreCase("2")){
            Map<String, Object> map = Maps.newHashMap();
            map.put("name", "开发人员");
            return map;
        }
        return Maps.newHashMap();
    }
}
