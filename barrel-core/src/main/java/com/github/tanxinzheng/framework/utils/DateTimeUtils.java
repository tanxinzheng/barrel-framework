package com.github.tanxinzheng.framework.utils;

import com.github.tanxinzheng.framework.constant.FormatConstants;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class DateTimeUtils {


    /**
     * 获取当前日期字符，字符格式：yyyyyMMddHHmmss
     * @return
     */
    public static String getDatetimeString(Date date){
        return FastDateFormat.getInstance(FormatConstants.ISO_YYYYMMDDHHMMSS).format(date);
    }

}
