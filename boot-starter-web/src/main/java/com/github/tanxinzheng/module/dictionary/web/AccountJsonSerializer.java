package com.github.tanxinzheng.module.dictionary.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 字典序列化
 * Created by tanxinzheng on 16/10/20.
 */
@Component
@Scope("prototype")
@Slf4j
public class AccountJsonSerializer extends JsonSerializer<Object> {

    @Autowired(required = false)
    AccountInterpreterService accountInterpreterService;

    AccountField accountField;

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(value == null){
            return;
        }
        jsonGenerator.writeObject(value);
        try {
            if(accountInterpreterService == null){
                return;
            }
            String currentName = jsonGenerator.getOutputContext().getCurrentName();
            // TODO 此处需要根据业务场景进行定制，默认1场景q
            // 1. 传统后台型，用户数量较少时，可直接查询所有用户的信息缓存至redis数据库中，可提高性能。
            // 2. 电商平台型，用户数量较多时，可通过单次查找通过userId进行缓存。
            Object dictionaryLabel = accountInterpreterService.translateAccount((String) value);
            String fieldName = currentName + "Detail";
            if(StringUtils.trimToNull(accountField.fieldName()) != null){
                fieldName = accountField.fieldName();
            }
            if(dictionaryLabel instanceof String){
                jsonGenerator.writeStringField(fieldName, (String) dictionaryLabel);
            }else if(dictionaryLabel instanceof Map){
                Object val = ((Map) dictionaryLabel).get(value);
                jsonGenerator.writeObjectField(fieldName, val);
            }else{
                jsonGenerator.writeObjectField(fieldName, dictionaryLabel);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public AccountJsonSerializer(AccountField accountField) {
        this.accountField = accountField;
    }
}
