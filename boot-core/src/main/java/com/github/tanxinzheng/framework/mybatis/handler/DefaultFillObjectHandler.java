package com.github.tanxinzheng.framework.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.tanxinzheng.framework.core.service.CurrentUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

@Slf4j
public class DefaultFillObjectHandler  implements MetaObjectHandler {

    public DefaultFillObjectHandler(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    CurrentUserService currentUserService;

    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = currentUserService.getCurrentUserId();
        if(StringUtils.isBlank(userId)){
            userId = "SYSTEM";
        }
        if (StringUtils.isNotBlank(userId)) {
            log.info("开始填充创建者CreateUser");
            this.setInsertFieldValByName("createdUserId", userId, metaObject);
            log.info("开始填充更新者UpdateUser");
            this.setUpdateFieldValByName("updatedUserId", new Date(), metaObject);
        }
        log.info("开始填充插入时间InsertTime");
        this.setInsertFieldValByName("createdTime", new Date(), metaObject);
        log.info("开始填充更新时间UpdateTime");
        this.setUpdateFieldValByName("updatedTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = currentUserService.getCurrentUserId();
        if (StringUtils.isNotBlank(userId)) {
            log.info("开始填充更新者UpdateUser");
            this.setUpdateFieldValByName("updatedUserId", new Date(), metaObject);
        }
        log.info("开始填充更新时间UpdateTime");
        this.setUpdateFieldValByName("updatedTime", new Date(), metaObject);
    }
}
