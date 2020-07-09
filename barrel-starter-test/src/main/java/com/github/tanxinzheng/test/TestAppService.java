package com.github.tanxinzheng.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tanxinzheng on 2018/11/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestAppStart.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAppService {
}
