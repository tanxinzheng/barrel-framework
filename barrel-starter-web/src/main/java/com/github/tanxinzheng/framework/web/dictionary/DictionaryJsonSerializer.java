package com.github.tanxinzheng.framework.web.dictionary;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.tanxinzheng.framework.web.annotation.DictionaryTransfer;
import com.github.tanxinzheng.framework.web.dictionary.domain.DictInfoVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 字典序列化
 * Created by tanxinzheng on 16/10/20.
 */
@Component
@Scope("prototype")
@Slf4j
public class DictionaryJsonSerializer extends JsonSerializer<Object>{
    /**
     * 字典翻译器
     */
    private DictionaryTransfer dictionaryTransfer;

    private ApplicationContext context;

    private List<DictionaryInterpreterService> dictionaryInterpreterServiceList;

    @Autowired
    public void setServiceList(List<DictionaryInterpreterService> items){
        dictionaryInterpreterServiceList = items;
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(value == null){
            return;
        }
        jsonGenerator.writeObject(value);
        try {
            Optional<DictionaryInterpreterService> transferService = getMatch();
            List<DictInfoVO> dictInfoVOList = Lists.newArrayList();
            if(transferService.isPresent()){
                dictInfoVOList = transferService.get().translate(dictionaryTransfer.type());
            }else{
                return;
            }
            String currentName = jsonGenerator.getOutputContext().getCurrentName();
            if(CollectionUtils.isEmpty(dictInfoVOList)){
                return;
            }
            for (DictInfoVO dictInfoVO : dictInfoVOList) {
                if(dictInfoVO.getCode().equalsIgnoreCase(value.toString())){
                    String fieldName = currentName + "Desc";
                    Object dictionaryLabel = null;
                    try {
                        if(StringUtils.trimToNull(dictionaryTransfer.fieldName()) != null){
                            fieldName = dictionaryTransfer.fieldName();
                        }
                        jsonGenerator.writeObjectField(fieldName, dictInfoVO.getDesc());
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    private Optional<DictionaryInterpreterService> getMatch(){
        Optional<DictionaryInterpreterService> optional = dictionaryInterpreterServiceList.stream().filter(item -> {
            return item.getServiceType() != null &&
                    item.getServiceType().equalsIgnoreCase(dictionaryTransfer.serviceType());
        }).findFirst();
        return Optional.ofNullable(optional).get();
    }

    public DictionaryJsonSerializer(DictionaryTransfer dictionaryTransfer) {
        this.dictionaryTransfer = dictionaryTransfer;
    }
}
