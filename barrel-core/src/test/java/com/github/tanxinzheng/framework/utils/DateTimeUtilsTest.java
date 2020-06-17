package com.github.tanxinzheng.framework.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getDatetimeString() {
        long l = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(new Date(l));
    }
}