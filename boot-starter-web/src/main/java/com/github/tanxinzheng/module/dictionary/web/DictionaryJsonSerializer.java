package com.github.tanxinzheng.module.dictionary.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典序列化
 * Created by tanxinzheng on 16/10/20.
 */
@Component
@Scope("prototype")
@Slf4j
public class DictionaryJsonSerializer extends JsonSerializer<Object>{

    private Map<String, DictionaryTransferService> dictionaryInterpreterServiceMap = new ConcurrentHashMap<>();

    @Autowired
    private DictionaryInterpreterService dictionaryInterpreterService;

    @Autowired
    public void register(List<DictionaryTransferService> serviceList){
        for (DictionaryTransferService dictionaryTransferService : serviceList) {
            dictionaryInterpreterServiceMap.put(dictionaryTransferService.getDictionaryIndex(), dictionaryTransferService);
        }
    }

    /**
     * 字典翻译器
     */
    private DictionaryTransfer dictionaryTransfer;

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(value == null){
            return;
        }
        jsonGenerator.writeObject(value);
        try {
            DictionaryTransferService transferService = dictionaryInterpreterServiceMap.get(dictionaryTransfer.index());
            Map<String, Object> dictionaryMapLabel = Maps.newHashMap();
            if(transferService != null){
                dictionaryMapLabel = transferService.translate(dictionaryTransfer.index(), (String) value);
            }else if(dictionaryInterpreterService != null){
                dictionaryMapLabel = dictionaryInterpreterService.translateDictionary(dictionaryTransfer.index(), (String) value);
            }else{
                return;
            }
            String currentName = jsonGenerator.getOutputContext().getCurrentName();
            Object dictionaryLabel = dictionaryTransfer.outputFormat().newInstance();
            if(MapUtils.isNotEmpty(dictionaryMapLabel)){
                dictionaryLabel = dictionaryMapLabel.get(value);
            }
            String fieldName = currentName + "Desc";
            if(StringUtils.trimToNull(dictionaryTransfer.fieldName()) != null){
                fieldName = dictionaryTransfer.fieldName();
            }
            if(dictionaryLabel instanceof String){
                jsonGenerator.writeStringField(fieldName, (String) dictionaryLabel);
            }else{
                jsonGenerator.writeObjectField(fieldName, dictionaryLabel);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }

    public DictionaryJsonSerializer(DictionaryTransfer dictionaryTransfer) {
        this.dictionaryTransfer = dictionaryTransfer;
    }

}
