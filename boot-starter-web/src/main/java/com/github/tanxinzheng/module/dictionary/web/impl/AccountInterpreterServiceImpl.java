package com.github.tanxinzheng.module.dictionary.web.impl;

import com.github.tanxinzheng.module.dictionary.web.AccountInterpreterService;
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
