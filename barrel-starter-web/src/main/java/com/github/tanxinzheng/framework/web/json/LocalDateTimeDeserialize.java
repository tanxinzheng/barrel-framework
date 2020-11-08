package com.github.tanxinzheng.framework.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.tanxinzheng.framework.constant.FormatConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.validator.routines.DateValidator;

import javax.print.attribute.standard.NumberUp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by tanxinzheng on 16/12/11.
 */
@Slf4j
public class LocalDateTimeDeserialize extends JsonDeserializer<LocalDateTime> {

    public static final String ISO_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String ISO_DATETIME_MINUTE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String source = jsonParser.getText();
        if(DateValidator.getInstance().isValid(source, FormatConstants.ISO_YYYY_MM_DD)){
            // parse yyyy-MM-dd
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE);
        }else if(DateValidator.getInstance().isValid(source, FormatConstants.ISO_YYYY_MM_DD_HH_MM_SS)){
            // parse yyyy-MM-dd HH:mm:ss
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(FormatConstants.ISO_YYYY_MM_DD_HH_MM_SS));
        }else if(DateValidator.getInstance().isValid(source, FormatConstants.ISO_YYYY_MM_DD_HH_MM)){
            // parse yyyy-MM-dd HH:mm
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(FormatConstants.ISO_YYYY_MM_DD_HH_MM));
        }else if(DateValidator.getInstance().isValid(source, DateFormatUtils.ISO_DATETIME_FORMAT.getPattern())){
            // parse yyyy-MM-dd'T'HH:mm:ss
            return LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        return null;
    }
}
