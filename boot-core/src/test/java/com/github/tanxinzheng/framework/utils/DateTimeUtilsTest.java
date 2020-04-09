package com.github.tanxinzheng.framework.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        String eventNo = DateTimeUtils.getDatetimeString(new Date()) + RandomStringUtils.randomNumeric(4);
        Assert.assertTrue(eventNo.length() == 18);
    }
}