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
            userId = "SYSTEM";
        }
        if (StringUtils.isNotBlank(userId)) {
            log.info("开始填充创建者CreateUser");
            this.setInsertFieldValByName("createdBy", userId, metaObject);
            log.info("开始填充更新者UpdateUser");
            this.setUpdateFieldValByName("updatedBy", userId, metaObject);
        }
        log.info("开始填充插入时间InsertTime");
        this.setInsertFieldValByName("createdTime", LocalDateTime.now(), metaObject);
        log.info("开始填充更新时间UpdateTime");
        this.setUpdateFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = currentUserService.getCurrentUserId();
        if (StringUtils.isNotBlank(userId)) {
            log.info("开始填充更新者UpdateUser");
            this.setUpdateFieldValByName("updatedUserId", LocalDateTime.now(), metaObject);
        }
        log.info("开始填充更新时间UpdateTime");
        this.setUpdateFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        this.currentUserService = configurableApplicationContext.getBean(CurrentUserService.class);
    }
}
