package com.github.tanxinzheng;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

public interface RedisTestService {

    @Cacheable(cacheNames = {"user"}, key="'user_'+#id")
    DemoDTO getUser(String id);

    @CacheEvict(cacheNames = {"user"}, key="'user_'+#id")
    void deleteUser(String id);
}
