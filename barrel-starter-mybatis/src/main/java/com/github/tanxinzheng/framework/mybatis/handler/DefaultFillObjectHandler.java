package com.github.tanxinzheng.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.tanxinzheng.framework.core.service.CurrentUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class DefaultFillObjectHandler  implements MetaObjectHandler, ApplicationContextInitializer {

    CurrentUserService currentUserService;

    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = null;
        if(currentUserService != null){
            userId = currentUserService.getCurrentUserId();
        }else{
            userId = "ROBOT";
        }
        if (StringUtils.isNotBlank(userId)) {
            log.debug("fill createdBy field:{}, updatedBy field: {}", userId, userId);
            this.setInsertFieldValByName("createdBy", userId, metaObject);
            this.setUpdateFieldValByName("updatedBy", userId, metaObject);
        }
        this.setInsertFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        this.setUpdateFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = currentUserService.getCurrentUserId();
        if (StringUtils.isNotBlank(userId)) {
            this.setUpdateFieldValByName("updatedBy", LocalDateTime.now(), metaObject);
        }
        this.setUpdateFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        this.currentUserService = configurableApplicationContext.getBean(CurrentUserService.class);
    }
}
