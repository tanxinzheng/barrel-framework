package com.github.tanxinzheng;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppTest.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class RedisTest {


    @Autowired
    RedisTestService redisTestService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void get() {
        redisTemplate.opsForValue().set("test", "我是地瓜");
        Assert.assertEquals("我是地瓜", redisTemplate.opsForValue().get("test"));
    }

    @Test
    public void testServiceCache(){
        for (int i = 0; i < 10; i++) {
            redisTestService.deleteUser(String.valueOf(i));
        }
        for (int i = 0; i < 10; i++) {
            DemoDTO demoDTO = redisTestService.getUser(String.valueOf(i));
            System.out.println(demoDTO);
        }
        for (int i = 0; i < 10; i++) {
            DemoDTO demoDTO = redisTestService.getUser(String.valueOf(i));
            System.out.println(demoDTO);
        }
    }
}
