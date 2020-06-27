package com.github.tanxinzheng.framework.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by tanxinzheng on 17/6/7.
 */
@Data
public class BaseEntity implements Serializable {

    private String createdBy;
    private LocalDateTime createdTime;
    private String updatedBy;
    private LocalDateTime updatedTime;

    public <T> T toDTO(Class<T> clazz){
        return null;
    }

}
