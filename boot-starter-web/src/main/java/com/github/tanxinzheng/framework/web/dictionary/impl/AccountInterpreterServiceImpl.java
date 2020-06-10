package com.github.tanxinzheng.framework.web.dictionary.impl;

import com.github.tanxinzheng.framework.web.dictionary.AccountInterpreterService;
import org.springframework.stereotype.Service;

@Service
public class AccountInterpreterServiceImpl implements AccountInterpreterService {

    @Override
    public Object translateAccount(String userId) {
        if("SYSTEM".equals(userId)){
            return "系统操作员";
        }
        return "unknown";
    }
}
