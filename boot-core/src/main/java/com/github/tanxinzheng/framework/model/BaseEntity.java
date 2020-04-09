package com.github.tanxinzheng.framework.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tanxinzheng on 17/6/7.
 */
@Data
public class BaseEntity implements Serializable {

    private String createdUserId;
    private Date createdTime;
    private String updatedUserId;
    private Date updatedTime;
    private Integer dataVersion;

}
