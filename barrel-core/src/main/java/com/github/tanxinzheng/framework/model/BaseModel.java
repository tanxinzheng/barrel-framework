package com.github.tanxinzheng.framework.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by jengt_000 on 2014/12/28.
 */
public class BaseModel implements Serializable {

    /** 创建人 */
    private String createdBy;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 更新人 */
    private String updatedBy;
    /** 更新时间 */
    private LocalDateTime updatedTime;
    /** 数据版本号 */
    private Integer dataVersion;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

}
