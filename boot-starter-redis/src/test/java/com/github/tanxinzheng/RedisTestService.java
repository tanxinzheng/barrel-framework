package com.github.tanxinzheng;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface RedisTestService {

    @Cacheable(key="'user_'+#id",value="'user'+#id")
    DemoDTO getUser(String id);

    @CacheEvict(key="'user_'+#id", value="users", condition="#id!=1")
    void deleteUser(String id);
}
