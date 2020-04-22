package com.github.tanxinzheng;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class},
        scanBasePackages = "com.github.tanxinzheng.**")
public class AppTest {

    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }

}
