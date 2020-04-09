package com.github.tanxinzheng.framework.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tanxinzheng on 17/8/5.
 */
public class PhoneValidator implements AbstractValidator<String> {

    public static final String PATTERN = "^1(3|4|5|7|8)\\d{9}$";

    public static final Pattern PHONE_PATTERN = Pattern.compile(PATTERN);

    public static final PhoneValidator PHONE_VALIDATOR = new PhoneValidator();

    public static PhoneValidator getInstance(){
        return PHONE_VALIDATOR;
    }

    @Override
    public String getPattern() {
        return PHONE_PATTERN.pattern();
    }

    /**
     * 验证手机号码格式是否正确
     * @param phone
     * @return
     */
    public boolean isValid(String phone) {
        if(phone == null) {
            return false;
        } else {
            Matcher phoneMatcher = PHONE_PATTERN.matcher(phone);
            return phoneMatcher.matches();
        }
    }
}
